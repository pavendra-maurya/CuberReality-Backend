package com.cuberreality.service.impl;


import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.entity.propertisesearch.*;
import com.cuberreality.entity.user.VisitedProperties;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.mapper.PropertiesMapper;
import com.cuberreality.repository.PropertiesRepository;
import com.cuberreality.repository.SearchRepository;
import com.cuberreality.repository.VisitedPropertiesRepository;
import com.cuberreality.request.propertise.PropertiesSearchRequest;
import com.cuberreality.response.propertise.PropertiesSearchDetails;
import com.cuberreality.response.propertise.PropertiesSearchResponse;
import com.cuberreality.service.PropertiesService;
import com.cuberreality.util.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private VisitedPropertiesRepository visitedPropertiesRepository;

    @Autowired
    private PropertiesMapper propertiesMapper;

    public PropertiesServiceImpl() {
    }

    @Override
    public PropertiesSearchResponse getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest) {


        List<SearchSchema> searchSchemas = searchRepository.findAll();

        PropertiesSearchResponse response = new PropertiesSearchResponse();

        List<String> propertyIdList = getPropertyIds(searchSchemas, propertiesSearchRequest);
        List<PropertiesSearchDetails> regularPropertiesSearch = new ArrayList<>();
        List<PropertiesSearchDetails> focusedPropertiesSearch = new ArrayList<>();
        for (String id : propertyIdList) {

            Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findByIdAndProductActive(id, true);
            if(propertiesSchema.isPresent()){
                PropertiesSchema  schema = propertiesSchema.get();
                if (schema.isPropertyTaxable())
                    focusedPropertiesSearch.add(propertiesMapper.toPropertiesResponse(schema));
                else
                    regularPropertiesSearch.add(propertiesMapper.toPropertiesResponse(schema));
            }
        }
        response.setFocusedProperties(focusedPropertiesSearch);
        response.setRegularProperties(regularPropertiesSearch);
        return response;
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
    public PropertiesSearchDetails getPropertyByCustomer(String property_id, String referred_by_id) {

        Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findById(property_id);

        if (propertiesSchema.isPresent()) {
            Optional<VisitedProperties> visitedProperties = visitedPropertiesRepository.findByPropertyIdAndResellerId(property_id, referred_by_id);

            VisitedProperties newVisitedProperties;

            if (visitedProperties.isPresent()) {
                newVisitedProperties = visitedProperties.get();
                newVisitedProperties.setVisitCount(newVisitedProperties.getVisitCount() + 1);
            } else {
                newVisitedProperties = new VisitedProperties();
                newVisitedProperties.setPropertyId(property_id);
                newVisitedProperties.setVisitCount(1);
                newVisitedProperties.setResellerId(referred_by_id);
            }
            visitedPropertiesRepository.save(newVisitedProperties);

            return propertiesMapper.toPropertiesResponse(propertiesSchema.get());
        }

        throw new RecordNotFoundException("Given Property id does not exist");
    }

    @Override
    public PropertiesSearchDetails getPropertyById(String property_id) {
        Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findById(property_id);

        if (propertiesSchema.isPresent())
            return propertiesMapper.toPropertiesResponse(propertiesSchema.get());
        throw new RecordNotFoundException("Given Property id does not exist");
    }


}
