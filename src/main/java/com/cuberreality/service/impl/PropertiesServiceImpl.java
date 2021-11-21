package com.cuberreality.service.impl;

import com.cuberreality.entity.*;
import com.cuberreality.repository.PropertiesRepository;
import com.cuberreality.repository.SearchRepository;
import com.cuberreality.request.CreateLeadRequest;
import com.cuberreality.request.PropertiesSearchRequest;
import com.cuberreality.request.UpdateLeadRequest;
import com.cuberreality.response.CreateLeadResponse;
import com.cuberreality.response.LeadResponse;
import com.cuberreality.response.PropertiesResponse;
import com.cuberreality.response.PropertyDetailedResponse;
import com.cuberreality.service.PropertiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;


    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<PropertiesSchema> getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest) {

        List<SearchSchema> searchSchemas = searchRepository.findAll();

        List<String> propertyIdList = getPropertyIds(searchSchemas, propertiesSearchRequest);

        List<PropertiesSchema> propertiesSchemaList = new ArrayList<>();
        for (String id : propertyIdList) {
            propertiesSchemaList.add(propertiesRepository.findById(id));
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
    public CreateLeadResponse createLead(CreateLeadRequest createLeadRequest) {
        return null;
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

    @Override
    public PropertyDetailedResponse getProperty(String id, String referred_by_id) {
        return null;
    }


}
