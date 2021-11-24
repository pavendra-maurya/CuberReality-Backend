package com.cuberreality.service.impl;

import com.cuberreality.entity.*;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.repository.PropertiesRepository;
import com.cuberreality.repository.SearchRepository;
import com.cuberreality.request.CreateLeadRequest;
import com.cuberreality.request.PropertiesSearchRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.CreateLeadResponse;
import com.cuberreality.response.LeadResponse;
import com.cuberreality.response.ResellersOccupation;
import com.cuberreality.service.PropertiesService;
import com.cuberreality.util.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<PropertiesSchema> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest) {

        List<SearchSchema> searchSchemas = searchRepository.findAll();

        List<String> propertyIdList = getPropertyIds(searchSchemas, propertiesSearchRequest);

        List<PropertiesSchema> propertiesSchemaList = new ArrayList<>();
        for (String id : propertyIdList) {

            Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findById(id);

            propertiesSchema.ifPresent(propertiesSchemaList::add);
        }

        return propertiesSchemaList;
    }


    private List<String> getPropertyIds(List<SearchSchema> searchSchema, PropertiesSearchRequest propertiesSearchRequest) {

        List<Country> countryList = searchSchema.get(0).getCountry();
        Optional<Country> country = countryList.stream()
                .filter(obj -> obj.getCountryName().equalsIgnoreCase(propertiesSearchRequest.getCountry()))
                .findFirst();

        List<State> stateList = new ArrayList<>();
        if (country.isPresent())
            stateList = country.get().getState();


        Optional<State> state = stateList.stream()
                .filter(obj -> obj.getStateName().equalsIgnoreCase(propertiesSearchRequest.getState()))
                .findFirst();

        List<City> cityList = new ArrayList<>();
        if (state.isPresent())
            cityList = state.get().getCity();

        Optional<City> city = cityList.stream()
                .filter(obj -> obj.getCityName().equalsIgnoreCase(propertiesSearchRequest.getCity()))
                .findFirst();

        List<SubArea> subAreasList = new ArrayList<>();
        if (city.isPresent())
            subAreasList = city.get().getSubArea();

        Optional<SubArea> subArea = subAreasList.stream()
                .filter(obj -> obj.getSubAreaName().toLowerCase(Locale.ROOT)
                        .contains(propertiesSearchRequest.getSubArea().toLowerCase(Locale.ROOT)))
                .findFirst();

        List<Area> areasList = new ArrayList<>();
        if (subArea.isPresent())
            areasList = subArea.get().getArea();

        List<PropertyData> propertyDataList = new ArrayList<>();
        for (Area area : areasList) {
            propertyDataList.addAll(area.getPropertyData());
        }

        List<String> propertyIdList = new ArrayList<>();

        for (PropertyData propertyData : propertyDataList)
            propertyIdList.add(propertyData.getId());

        return propertyIdList;

    }

    @Override
    public PropertiesSchema getProperty(String id, String referred_by_id) {


        Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findById(id);

        if (propertiesSchema.isPresent())
            return propertiesSchema.get();
        throw new RecordNotFoundException("Given Property id does not exist");
    }

    @Override
    public ResellersOccupation getResellersOccupation() throws Exception {

        List<String> arrayList = Arrays.asList("Loan Agent", "Insurance Agent", "Homemaker", "Home Loan Agent", "Mutual Fund Agent", "Financial Advisors", "Chartered accountant", "Architect", "Interior Designer", "Civil Contractor / Engineer");
        return new ResellersOccupation(arrayList);
    }

    @Override
    public CreateLeadResponse createLead(CreateLeadRequest createLeadRequest) throws Exception {


        CreateLeadResponse createLeadResponse = postWithHeaders(createLeadRequest);


        return createLeadResponse;
    }

    public CreateLeadResponse postWithHeaders(CreateLeadRequest createLeadRequest) throws Exception {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth("1000.c638ef0b29357324e00ed358013db1a7.ab7100ce48c870ef3722b72ccc79adc3");
//        for (String key : headers.keySet()) {
//            httpHeaders.add(key, headers.get(key));
        // }
        HttpEntity<CreateLeadRequest> entity = new HttpEntity<>(createLeadRequest, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateLeadResponse> response = restTemplate.postForEntity("https://www.zohoapis.in/bigin/v1/Deals", entity, CreateLeadResponse.class);
        System.out.println(response.getBody());

        return response.getBody();

    }

    private static void sendPOST() throws IOException {


//        URL obj = new URL("https://www.zohoapis.in/bigin/v1/Deals");
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
//
//        // For POST only - START
//        con.setDoOutput(true);
//        con.setRequestProperty("Authorization","Bearer Token "+"1000.ca01e84b2312a0c6e503f7ade6c3a2d3.7c9b711f095f2b1bd9024f4125ed70eb");
//        OutputStream os = con.getOutputStream();
//        //os.write(POST_PARAMS.getBytes());
//        os.flush();
//        os.close();
//        // For POST only - END
//
//
//        int responseCode = con.getResponseCode();
//        System.out.println("POST Response Code :: " + responseCode);
//
//        if (responseCode == HttpURLConnection.HTTP_OK) { //success
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    con.getInputStream()));
//            String inputLine;
//            StringBuffer response = new StringBuffer();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // print result
//            System.out.println(response.toString());
//        } else {
//            System.out.println("POST request not worked");
//        }
    }

    @Override
    public LeadResponse getLead(String id) {
        return null;
    }

    @Override
    public List<LeadResponse> getLeads() {
        return null;
    }

    @Override
    public List<LeadResponse> updateLead(UpdateLeadRequest updateLeadRequest) {
        return null;
    }


}
