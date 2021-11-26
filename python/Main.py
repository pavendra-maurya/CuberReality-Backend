import json
import requests
import pymongo
import re

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

    execute_api(config_data.get("crm_account_url") + config_data.get("refresh_token_path"), "POST", payload, headers)


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
        print(requests.request("POST", url, data=payload, headers=headers).text)


def sync_properties_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_property_path"), "GET")
    collection = get_mongo_client("Properties")
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
        print(update.raw_result)


def sync_leads_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_leads_path"), "GET")
    collection = get_mongo_client("Leads")
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
        print(update.raw_result)


def sync_user_profile_data_from_crm_to_db():
    properties = execute_api(config_data.get("crm_user_profile_path"), "GET")
    collection = get_mongo_client("UserProfiles")
    for property in properties:
        value = property["id"]
        update = collection.replace_one({"id": value}, property, upsert=True)
        print(update.raw_result)


def get_all_properties_id():
    id_list = []
    collection = get_mongo_client("Properties")
    x = collection.find({}, {'Property_ID': 1, 'id': 1})
    for ids in x:
        id_list.append({ids["Property_ID"]: ids["id"]})
    return id_list


def download_attachment():
    pid_list = get_all_properties_id()

    for data in pid_list:
        for key, value in data.items():
            pid_json = "https://cuberreality.s3.ap-south-1.amazonaws.com/" + key.strip() + "/" + key.strip() + "_ProjectSpecs/" + key.strip() + ".json"
            pid_txt = "https://cuberreality.s3.ap-south-1.amazonaws.com/" + key.strip() + "/" + key.strip() + "_ProjectSpecs/" + key.strip() + ".txt"
            download_attachment_from_s3(key.strip(), pid_json, pid_txt)


def download_attachment_from_s3(property_id, pid_json, pid_txt):
    complete_json = {}
    isAttachmentSuccess1 = False
    isAttachmentSuccess2 = False
    try:
        response = requests.get(pid_json)
        if response.status_code == 200:
            complete_json = response.json()
            isAttachmentSuccess1 = True

    except Exception as ex:
        print("Error has occurred while downloading attachment " + pid_json + ". error " + str(ex))
    try:
        response = requests.get(pid_txt)
        if response.status_code == 200:
            complete_json["projectSpecification"].update({"specifications": str(response.text)})
            isAttachmentSuccess2 = True
    except Exception as ex:
        print("Error has occurred while downloading & processing attachment " + pid_txt + ". error " + str(ex))

    if isAttachmentSuccess1 and isAttachmentSuccess2:
        collection = get_mongo_client("Properties")
        res = collection.update_one({"Property_ID": property_id}, {"$set": complete_json}, ).raw_result
        print(res, property_id, complete_json)
    else:
        print("Error has occurred while it was reading attachment files, urls " + pid_json + "  ," + pid_txt)


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
    collection1 = get_mongo_client("Search")
    collection1.drop()
    collection1 = get_mongo_client("Search")
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


load_config_details()
sync_properties_data_from_crm_to_db()
sync_leads_data_from_crm_to_db()
sync_user_profile_data_from_crm_to_db()
create_search_space()
download_attachment()
