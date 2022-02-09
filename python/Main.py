import json
import requests
import pymongo
import re
import platform
from subprocess import Popen, PIPE
import os
from datetime import datetime
import subprocess

CONFIG_REPO_NAME = "cuberreality-config"
PROPERTIES_SCHEMA = "PropertiesSchema"
LEADS_SCHEMA = "LeadsSchema"
USER_PROFILE_SCHEMA = "UserProfilesSchema"
OCCUPATIONS_SCHEMA = "OccupationsSchema"
REFER_LEADS_SCHEMA = "ReferLeadsSchema"
SEARCH_SCHEMA = "SearchSchema"
config_data = {}
sync_property = []

print(" ")
print(datetime.now())
print(" ")


def load_config_details():
    global config_data
    with open(os.path.join(os.path.dirname(os.path.realpath(__file__)), "config.json")) as file:
        config_data = json.load(file)

    last_generate_token_time = config_data["last_generate_token_time"]
    time_format = "%Y-%m-%d %H:%M:%S"
    prev = datetime.strptime(last_generate_token_time, time_format)
    current_time = datetime.now().strftime(time_format)
    current = datetime.strptime(str(current_time), time_format)
    diff = (current - prev).seconds // 60

    if diff > 55:
        token = refresh_token()
        if token:
            config_data["token"] = token
            config_data["last_generate_token_time"] = str(current_time)
            with open('config.json', 'w') as outfile:
                json.dump(config_data, outfile)
        else:
            exit(1)


def refresh_token():
    refresh_token = config_data.get('crm_refresh_token')
    client_id = config_data.get('crm_client_id')
    client_secret = config_data.get('crm_client_secret')
    payload = "------" \
              "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " \
              "name=\"refresh_token\"\r\n\r\n" + refresh_token + "\r\n------" \
                                                                 "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " \
                                                                 "name=\"client_id\"\r\n\r\n" + client_id + "\r\n------" \
                                                                                                            "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " \
                                                                                                            "name=\"client_secret\"\r\n\r\n" + client_secret + "\r\n------" \
                                                                                                                                                               "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " \
                                                                                                                                                               "name=\"grant_type\"\r\n\r\nrefresh_token\r\n------" \
                                                                                                                                                               "WebKitFormBoundary7MA4YWxkTrZu0gW--"
    headers = {
        'content-type': "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW"
    }
    output = execute_api(config_data.get("crm_account_url") + config_data.get("refresh_token_path"), "POST", payload,
                         headers)
    if output.get("access_token") is not None:
        return output.get("access_token")
    else:
        print("Access token is not generated")
        return None


def get_mongo_client(collection_name='Properties'):
    # user_name = "nearbyse"
    # password = "Kuchkito420"
    # client = pymongo.MongoClient("mongodb://nearbyse:Kuchkito420@localhost:27017/?authSource=CuberReality")
    user_name = "nearbysecuber"
    password = "Kuchkitho420"
    client = pymongo.MongoClient(
        "mongodb://" + user_name + ":" + password + "@" + config_data.get('host') + ":" + config_data.get(
            'port') + "/?authSource=admin")
    database = client["CuberReality"]
    collection = database[collection_name]
    return collection


def execute_api(url, method, payload=None, headers=None):
    base_url = config_data.get('crm_base_url')

    if method.lower() == "get":
        if headers is None:
            headers = {
                'authorization': "Bearer " + config_data.get('token')
            }
        else:
            headers = headers
        print(base_url + url)
        response = requests.get(base_url + url, headers=headers)
        try:
            data = response.text
            return json.loads(re.sub(r'"\$', '"__', data))["data"]
        except Exception as ex:
            print("Get Api does not returned data ")
            return []

    if method.lower() == "post":
        return requests.request("POST", url, data=payload, headers=headers).json()


def append_extra_data(crm_data, mongo_data):
    if mongo_data is not None:
        extra_key = list(set(mongo_data.keys()) - set(crm_data.keys()))
        for key in extra_key:
            crm_data[key] = mongo_data[key]
    return crm_data


def get_mongo_data(collection):
    output = {}
    cursor = collection.find({})
    for document in cursor:
        output[document["id"]] = document
    return output


def sync_properties_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_property_path"), "GET")
    collection = get_mongo_client(PROPERTIES_SCHEMA)
    mongo_properties = get_mongo_data(collection)
    global sync_property
    for property_data in properties:
        if property_data.get("Sync"):
            id = property_data["id"]
            sync_property.append(id)
            property_data = append_extra_data(property_data, mongo_properties.get(id))
            update = collection.replace_one({"id": id}, property_data, upsert=True)
            print(update.raw_result)


def sync_leads_data_from_crm_to_db():
    lead_data = execute_api(config_data.get("crm_leads_path"), "GET")
    lead_collection = get_mongo_client(LEADS_SCHEMA)
    referral_collection = get_mongo_client(REFER_LEADS_SCHEMA)
    mongo_lead_data = get_mongo_data(lead_collection)
    mongo_referal_data = get_mongo_data(referral_collection)
    for lead in lead_data:
        id = lead["id"]
        if lead["Pipeline"] == "Property Purchase":
            lead = append_extra_data(lead, mongo_lead_data.get(id))
            update = lead_collection.replace_one({"id": id}, lead, upsert=True)
            print(update.raw_result)
        elif lead["Pipeline"] == "Property Listing":
            lead = append_extra_data(lead, mongo_referal_data.get(id))
            update = referral_collection.replace_one({"id": id}, lead, upsert=True)
            print(update.raw_result)
        else:
            print("does not find the Pipeline data schema of pipeline "+lead["Pipeline"] )



def sync_occupations_data():
    occupations_data = {
        "resellersOccupation": ["Loan Agent", "Insurance Agent", "Homemaker", "Home Loan Agent",
                                "Mutual Fund Agent", "Financial Advisors", "Chartered accountant", "Architect",
                                "Interior Designer", "Civil Contractor / Engineer"]
    }
    collection1 = get_mongo_client(OCCUPATIONS_SCHEMA)
    collection1.drop()
    collection1 = get_mongo_client(OCCUPATIONS_SCHEMA)
    collection1.insert_one(occupations_data)


def sync_user_profile_data_from_crm_to_db():
    user_profiles = execute_api(config_data.get("crm_user_profile_path"), "GET")
    collection = get_mongo_client(USER_PROFILE_SCHEMA)
    mongo_user_profile = get_mongo_data(collection)
    for user_profile in user_profiles:
        id = user_profile["id"]
        user_profile = append_extra_data(user_profile, mongo_user_profile.get(id))
        update = collection.replace_one({"id": id}, user_profile, upsert=True)
        print(update.raw_result)


def get_all_properties_id():
    id_list = []
    collection = get_mongo_client(PROPERTIES_SCHEMA)
    x = collection.find({}, {'Property_ID': 1, 'id': 1})
    for ids in x:
        id_list.append({ids["Property_ID"]: ids["id"]})
    return id_list


def process_github_config():
    pid_list = get_all_properties_id()
    for data in pid_list:
        for key, value in data.items():
            if value in sync_property:
                json_path = os.path.join(os.path.abspath("./"), "cuberreality-config", key.strip(),
                                         key.strip() + "_ProjectSpecs", key.strip() + ".json")
                text_path = os.path.join(os.path.abspath("./"), "cuberreality-config", key.strip(),
                                         key.strip() + "_ProjectSpecs", key.strip() + ".txt")
                # video_path = os.path.join(os.path.abspath("./"), "cuberreality-config", key.strip(),
                #                           key.strip() + "_Videos", "video.json")
                parse_file(key.strip(), json_path, text_path)


def image_processing(json_data):
    base_url = config_data.get('image_base_url')

    for key, val in json_data["imageUrl"].items():
        json_data["imageUrl"][key] = add_base_image_url(base_url, json_data["imageUrl"][key])

    for key, val in json_data["floorPlan"].items():
        for key1, val1 in json_data["floorPlan"][key].items():
            json_data["floorPlan"][key][key1]["imageUrls"] = add_base_image_url(base_url,
                                                                                json_data["floorPlan"][key][key1][
                                                                                    "imageUrls"])

    displayImageUrls = []
    FocusedImg_url = json_data["imageUrl"]["focusedImg_url"]
    ProjectImages_url = json_data["imageUrl"]["ProjectImages_url"]
    UnitPhotosUrl = json_data["imageUrl"]["UnitPhotosUrl"]
    MasterPlan_url = json_data["imageUrl"]["MasterPlan_url"]
    displayImageUrls.extend(FocusedImg_url)
    displayImageUrls.extend(MasterPlan_url)
    displayImageUrls.extend(ProjectImages_url)
    displayImageUrls.extend(UnitPhotosUrl)
    json_data["imageUrl"]["displayImageUrls"] = displayImageUrls

    return json_data


def add_base_image_url(base_url, data):
    data_list = []
    if data:
        for path in data:
            data_list.append(base_url + "/" + path)
    return data_list


def parse_file(property_id, json_path, text_path):
    complete_json = {}

    try:
        with open(json_path, "r") as file:
            complete_json = json.load(file)
            complete_json = image_processing(complete_json)

    except FileNotFoundError as ex:
        print("Error has occurred while downloading attachment " + json_path + ". error " + str(ex))

    except Exception as ex:
        print(ex, json_path)

    try:
        with open(text_path, "r", encoding='utf-8') as file:
            response = file.read()
            complete_json["projectSpecification"].update({"specifications": str(response)})

    except FileNotFoundError as ex:
        print("Error has occurred while downloading & processing attachment " + text_path + ". error " + str(ex))

    except Exception as ex:
        print(str(ex), text_path)

    collection = get_mongo_client(PROPERTIES_SCHEMA)
    res = collection.update_one({"Property_ID": property_id}, {"$set": complete_json}, ).raw_result
    print(res)


def create_search_space():
    search_data = {"Country": []}
    collection = get_mongo_client(PROPERTIES_SCHEMA)
    x = collection.find({}, {'Country': 1, 'State': 1, 'Area': 1, 'City': 1, 'Sub_Area': 1, 'Address': 1, 'id': 1, 'Taxable':1, 'Product_Active':1})
    for ids in x:
        Country, State, Area, City, Sub_Area, Address, Id, Focused, Product_Active = format_search_data(
            ids["Country"]).capitalize(), format_search_data(
            ids["State"]).capitalize(), format_search_data(ids["Area"]).capitalize(), format_search_data(
            ids["City"]).capitalize(), format_search_data(
            ids["Sub_Area"]).capitalize(), format_search_data(ids["Address"]).capitalize(), ids["id"], ids["Taxable"], ids["Product_Active"]

        if "None" in [Country, State, Area, City, Sub_Area, Address, Id]:
            print("Address details is None", Id, Country, State, Area, City, Sub_Area, Address)
            continue
        elif Product_Active is False:
            continue
        else:
            property_details = {"Id": Id, "Address": Address,"Focused": Focused, "Product_Active":Product_Active}
            country_index = isExist(search_data["Country"], "Country_Name", Country)
            if country_index is not -1:
                state_index = isExist(search_data["Country"][country_index]["State"], "State_Name", State)
                if state_index is not -1:
                    city_index = isExist(search_data["Country"][country_index]["State"][state_index]["City"],
                                         "City_Name", City)
                    if city_index is not -1:
                        sub_area_index = isExist(
                            search_data["Country"][country_index]["State"][state_index]["City"][city_index]["Sub_Area"],
                            "Sub_Area_Name", Sub_Area)
                        if sub_area_index is not -1:
                            area_index = isExist(
                                search_data["Country"][country_index]["State"][state_index]["City"][city_index][
                                    "Sub_Area"][sub_area_index]["Area"], "Area_Name", Area)
                            if area_index is not -1:
                                search_data["Country"][country_index]["State"][state_index]["City"][city_index][
                                    "Sub_Area"][sub_area_index]["Area"][area_index]["Property_Data"].append(
                                    property_details)
                            else:
                                search_data["Country"][country_index]["State"][state_index]["City"][city_index][
                                    "Sub_Area"][sub_area_index]["Area"].append(
                                    {"Property_Data": [property_details], "Area_Name": Area})
                        else:
                            search_data["Country"][country_index]["State"][state_index]["City"][city_index][
                                "Sub_Area"].append({"Area": [{"Property_Data": [property_details], "Area_Name": Area}],
                                                    "Sub_Area_Name": Sub_Area})
                    else:
                        search_data["Country"][country_index]["State"][state_index]["City"].append(
                            {"City_Name": City, "Sub_Area": [
                                {"Area": [{"Property_Data": [property_details], "Area_Name": Area}],
                                 "Sub_Area_Name": Sub_Area}]})
                else:
                    search_data["Country"][country_index]["State"].append({"City": [{"City_Name": City, "Sub_Area": [
                        {"Area": [{"Property_Data": [property_details], "Area_Name": Area}],
                         "Sub_Area_Name": Sub_Area}]}], "State_Name": State})
            else:
                search_data["Country"].append({"State": [{"City": [{"City_Name": City, "Sub_Area": [
                    {"Area": [{"Property_Data": [property_details], "Area_Name": Area}], "Sub_Area_Name": Sub_Area}]}],
                                                          "State_Name": State}], "Country_Name": Country})
    collection1 = get_mongo_client(SEARCH_SCHEMA)
    collection1.drop()
    collection1 = get_mongo_client(SEARCH_SCHEMA)
    collection1.insert_one(search_data)


def isExist(data, key, Value):
    for index in range(len(data)):
        if data[index].get(key) == Value:
            return index
    return -1


def format_search_data(data):
    if data is None:
        return "None"
    return "_".join(data.split(" ")).strip()


def config_repo_clone():
    folder_path = os.path.dirname(os.path.realpath(__file__))

    if platform.system().lower() == "linux":
        print("deleting " + os.path.join(folder_path, CONFIG_REPO_NAME) + " folder")
        config_folder_delete = ['rm', '-rf', os.path.join(folder_path, CONFIG_REPO_NAME)]
        config_folder_delete_query = Popen(config_folder_delete, stdout=PIPE, stderr=PIPE)
        status, error = config_folder_delete_query.communicate()

    if platform.system().lower() == "windows":
        os.system('rmdir /S /Q "{}"'.format(CONFIG_REPO_NAME))

    git_command = ['git', 'clone', 'https://' + config_data.get(
        'github_token') + ':x-oauth-basic@github.com/cuberreality/cuberreality-config']

    os.chdir(folder_path)
    git_query = Popen(git_command, stdout=PIPE, stderr=PIPE)
    git_status, error = git_query.communicate()
    print(git_status, error)
    if platform.system().lower() == "linux":
        print("changing folder permissions")
        subprocess.call(['chmod', '-R', '777', os.path.join(folder_path, CONFIG_REPO_NAME)])


def main():
    load_config_details()
    sync_occupations_data()
    sync_properties_data_from_crm_to_db()
    sync_leads_data_from_crm_to_db()
    sync_user_profile_data_from_crm_to_db()
    create_search_space()
    config_repo_clone()
    process_github_config()


main()

# app = Flask(__name__)

# @app.route("/sync/crm")
# def sync_crm():
#     main()
#     return "Successfully synced"
#
#
# if __name__ == "__main__":
#     app.run(host="0.0.0.0", port=5000, debug=True)