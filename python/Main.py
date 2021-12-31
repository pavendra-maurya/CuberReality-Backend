import json
import requests
import pymongo
import re
import platform
from flask import Flask

from subprocess import Popen, PIPE
import os

CONFIG_REPO_NAME = "cuberreality-config"
PROPERTIES_SCHEMA = "PropertiesSchema"
LEADS_SCHEMA = "LeadsSchema"
USER_PROFILE_SCHEMA = "UserProfilesSchema"
OCCUPATIONS_SCHEMA = "OccupationsSchema"
SEARCH_SCHEMA = "SearchSchema"

config_data = {}


def load_config_details():
    with open("config.json") as file:
        global config_data
        config_data = json.load(file)
    return config_data


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
    if (output.get("access_token") is not None):
        config_data["token"] = output.get("access_token")


def get_mongo_client(collection_name='Properties'):
    client = pymongo.MongoClient("mongodb://" + config_data.get('host') + ":" + config_data.get('port'))
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
            print(ex)
            exit(1)

    if method.lower() == "post":
        return requests.request("POST", url, data=payload, headers=headers).json()


def sync_properties_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_property_path"), "GET")
    collection = get_mongo_client(PROPERTIES_SCHEMA)
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
        print(update.raw_result)


def sync_leads_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_leads_path"), "GET")
    collection = get_mongo_client(LEADS_SCHEMA)
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
        print(update.raw_result)


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
    properties = execute_api(config_data.get("crm_user_profile_path"), "GET")
    collection = get_mongo_client(USER_PROFILE_SCHEMA)
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
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
            json_path = os.path.join(os.path.abspath("./"), "cuberreality-config", key.strip(),
                                     key.strip() + "_ProjectSpecs", key.strip() + ".json")
            text_path = os.path.join(os.path.abspath("./"), "cuberreality-config", key.strip(),
                                     key.strip() + "_ProjectSpecs", key.strip() + ".txt")
            parse_file(key.strip(), json_path, text_path)


def image_processing(json_data):
    base_url = config_data.get('image_base_url')
    image_data = json_data.get('imageUrl')
    if image_data:
        focusedImg_url = image_data.get('focusedImg_url')
        json_data["imageUrl"]["focusedImg_url"] = add_base_image_url(base_url, focusedImg_url)
        broucher_url = image_data.get('broucher_url')
        json_data["imageUrl"]["broucher_url"] = add_base_image_url(base_url, broucher_url)
        BuilderLogo_url = image_data.get('BuilderLogo_url')
        json_data["imageUrl"]["BuilderLogo_url"] = add_base_image_url(base_url, BuilderLogo_url)
        MasterPlan_url = image_data.get('MasterPlan_url')
        json_data["imageUrl"]["MasterPlan_url"] = add_base_image_url(base_url, MasterPlan_url)
        PaymentPlan_url = image_data.get('PaymentPlan_url')
        json_data["imageUrl"]["PaymentPlan_url"] = add_base_image_url(base_url, PaymentPlan_url)
        ProjectImages_url = image_data.get('ProjectImages_url')
        json_data["imageUrl"]["ProjectImages_url"] = add_base_image_url(base_url, ProjectImages_url)
        ProjectLogo_url = image_data.get('ProjectLogo_url')
        json_data["imageUrl"]["ProjectLogo_url"] = add_base_image_url(base_url, ProjectLogo_url)
        UnitPhotosUrl = image_data.get('UnitPhotosUrl')
        json_data["imageUrl"]["UnitPhotosUrl"] = add_base_image_url(base_url, UnitPhotosUrl)
        Videos_url = image_data.get('Videos_url')
        json_data["imageUrl"]["Videos_url"] = add_base_image_url(base_url, Videos_url)
        FloorPlans_url = image_data.get('FloorPlans_url')
        if FloorPlans_url:
            FloorPlans_url_2bhk = FloorPlans_url.get('2BHK')
            if FloorPlans_url_2bhk:
                FloorPlans_url_2bhk_type1 = FloorPlans_url_2bhk.get('Type1')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type1"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type1)
                FloorPlans_url_2bhk_type2 = FloorPlans_url_2bhk.get('Type2')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type2"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type2)
                FloorPlans_url_2bhk_type3 = FloorPlans_url_2bhk.get('Type3')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type3"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type3)
                FloorPlans_url_2bhk_type4 = FloorPlans_url_2bhk.get('Type4')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type4"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type4)
                FloorPlans_url_2bhk_type5 = FloorPlans_url_2bhk.get('Type5')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type5"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type5)
                FloorPlans_url_2bhk_type6 = FloorPlans_url_2bhk.get('Type6')
                json_data["imageUrl"]["FloorPlans_url"]["2BHK"]["Type6"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_2bhk_type6)

            FloorPlans_url_3bhk = FloorPlans_url.get('3BHK')
            if FloorPlans_url_3bhk:
                FloorPlans_url_3bhk_type1 = FloorPlans_url_3bhk.get('Type1')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type1"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type1)
                FloorPlans_url_3bhk_type2 = FloorPlans_url_3bhk.get('Type2')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type2"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type2)
                FloorPlans_url_3bhk_type3 = FloorPlans_url_3bhk.get('Type3')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type3"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type3)
                FloorPlans_url_3bhk_type4 = FloorPlans_url_3bhk.get('Type4')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type4"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type4)
                FloorPlans_url_3bhk_type5 = FloorPlans_url_3bhk.get('Type5')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type5"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type5)
                FloorPlans_url_3bhk_type6 = FloorPlans_url_3bhk.get('Type6')
                json_data["imageUrl"]["FloorPlans_url"]["3BHK"]["Type6"] = add_base_image_url(base_url,
                                                                                              FloorPlans_url_3bhk_type6)
    return json_data

def add_base_image_url(base_url, data):
    data_list = []
    if data:
        for path in data:
            data_list.append(base_url + "/" + path)
    return data_list


def parse_file(property_id, json_path, text_path):
    complete_json = {}
    isAttachmentSuccess1 = False
    isAttachmentSuccess2 = False

    try:
        with open(json_path, "r") as file:
            complete_json = json.load(file)
            # Implement parser to add url in image
            complete_json = image_processing(complete_json)
            isAttachmentSuccess1 = True

    except FileNotFoundError as ex:
        print("Error has occurred while downloading attachment " + json_path + ". error " + str(ex))

    except Exception as ex:
        print((ex), json_path)

    try:
        with open(text_path, "r", encoding='utf-8') as file:
            response = file.read()
            complete_json["projectSpecification"].update({"specifications": str(response)})
            isAttachmentSuccess2 = True

    except FileNotFoundError as ex:
        print("Error has occurred while downloading & processing attachment " + text_path + ". error " + str(ex))

    except Exception as ex:
        print(str(ex), text_path)

    if isAttachmentSuccess1 and isAttachmentSuccess2:
        collection = get_mongo_client(PROPERTIES_SCHEMA)
        res = collection.update_one({"Property_ID": property_id}, {"$set": complete_json}, ).raw_result
    else:
        print("Error has occurred while it was reading file from config")


def create_search_space():
    search_data = {"Country": []}
    collection = get_mongo_client("Properties")
    x = collection.find({}, {'Country': 1, 'State': 1, 'Area': 1, 'City': 1, 'Sub_Area': 1, 'Address': 1, 'id': 1})
    for ids in x:
        Country, State, Area, City, Sub_Area, Address, Id = format_search_data(ids["Country"]), format_search_data(
            ids["State"]), format_search_data(ids["Area"]), format_search_data(ids["City"]), format_search_data(
            ids["Sub_Area"]), format_search_data(ids["Address"]), format_search_data(ids["id"])

        if Country is None or State is None:
            continue
        else:
            property_details = {"Id": Id, "Address": Address}
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
        return "no_data"
    return "_".join(data.split(" ")).strip()


def config_repo_clone():
    if platform.system().lower() is "linux":
        config_folder_delete = ['rm', '-rf', CONFIG_REPO_NAME]
        config_folder_delete_query = Popen(config_folder_delete, stdout=PIPE, stderr=PIPE)
        status, error = config_folder_delete_query.communicate()

    if platform.system().lower() is "windows":
        os.system('rmdir /S /Q "{}"'.format(CONFIG_REPO_NAME))

    git_command = ['git', 'clone', 'https://' + config_data.get(
        'github_token') + ':x-oauth-basic@github.com/cuberreality/cuberreality-config']
    git_query = Popen(git_command, stdout=PIPE, stderr=PIPE)
    git_status, error = git_query.communicate()
    print(git_status, error)


def main():
    load_config_details()
    refresh_token()
    sync_occupations_data()
    sync_properties_data_from_crm_to_db()
    sync_leads_data_from_crm_to_db()
    sync_user_profile_data_from_crm_to_db()
    create_search_space()
    config_repo_clone()
    process_github_config()


app = Flask(__name__)


@app.route("/sync/crm")
def sync_crm():
    main()
    return "Successfully synced"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)