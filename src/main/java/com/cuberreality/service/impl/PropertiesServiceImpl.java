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
import com.cuberreality.response.propertise.PropertyAreaDetails;
import com.cuberreality.response.propertise.PropertyAreaResponse;
import com.cuberreality.service.PropertiesService;
import com.cuberreality.util.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private Map<String, PropertyAreaDetails> searchDetailsStaticMap;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private VisitedPropertiesRepository visitedPropertiesRepository;

    @Autowired
    private PropertiesMapper propertiesMapper;

    @Autowired
    MongoTemplate mongoTemplate;

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
            if (propertiesSchema.isPresent()) {
                PropertiesSchema schema = propertiesSchema.get();
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

    private List<String> getPropertyIds(List<SearchSchema> searchSchema,
                                        PropertiesSearchRequest propertiesSearchRequest) {

        List<City> cityList = searchSchema.get(0).getCountry().stream()
                .filter(obj -> obj.getCountryName().equalsIgnoreCase(propertiesSearchRequest.getCountry()))
                .flatMap(list -> list.getState().stream())
                .filter(obj -> obj.getStateName().equalsIgnoreCase(propertiesSearchRequest.getState()))
                .flatMap(list -> list.getCity().stream())
                .filter(city -> city.getCityName().equalsIgnoreCase(propertiesSearchRequest.getCity()))
                .collect(Collectors.toList());


        Set<String> propertyIds = new HashSet<>();


        if (propertiesSearchRequest.isFocusedPropertyBasedOnCity())
            propertyIds.addAll(cityList.stream()
                    .flatMap(obj -> obj.getSubArea().stream())
                    .flatMap(obj -> obj.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .filter(PropertyData::isFocused)
                    .map(PropertyData::getId)
                    .collect(Collectors.toSet()));

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("city"))
            propertyIds.addAll(cityList.stream()
                    .flatMap(obj -> obj.getSubArea().stream())
                    .flatMap(obj -> obj.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId)
                    .collect(Collectors.toSet()));

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("area")) {
            propertyIds.addAll(cityList.stream()
                    .flatMap(list -> list.getSubArea().stream())
                    .flatMap(list -> list.getArea().stream())
                    .filter(obj -> propertiesSearchRequest.getAreaList().contains(obj.getAreaName()))
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId).collect(Collectors.toSet()));
        }

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("subArea")) {
            propertyIds.addAll(cityList.stream()
                    .flatMap(list -> list.getSubArea().stream())
                    .filter(obj -> propertiesSearchRequest.getSubAreaList().contains(obj.getSubAreaName()))
                    .flatMap(list -> list.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId).collect(Collectors.toSet()));
        }

        return new ArrayList<>(propertyIds);
    }

    @Override
    public PropertiesSearchDetails getPropertyByCustomer(String property_id, String referred_by_id) {

        Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findById(property_id);

        if (propertiesSchema.isPresent()) {
            Optional<VisitedProperties> visitedProperties = visitedPropertiesRepository.findByPropertyIdAndResellerId(property_id, referred_by_id);

            VisitedProperties newVisitedProperties;

            if (visitedProperties.isPresent()) {
                newVisitedProperties = visitedProperties.get();
                // newVisitedProperties.setVisitCount(newVisitedProperties.getVisitCount() + 1);
                update(newVisitedProperties.getPropertyId(), newVisitedProperties.getVisitCount());
            } else {
                newVisitedProperties = new VisitedProperties();
                newVisitedProperties.setPropertyId(property_id);
                newVisitedProperties.setVisitCount(1);
                newVisitedProperties.setResellerId(referred_by_id);
                visitedPropertiesRepository.save(newVisitedProperties);

            }

            return propertiesMapper.toPropertiesResponse(propertiesSchema.get());
        }

        throw new RecordNotFoundException("Given Property id does not exist");
    }

    public VisitedProperties update(String id, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("property_id").is(id));
        Update update = new Update();
        update.set("visit_count", count + 1);

        return mongoTemplate.findAndModify(query, update, VisitedProperties.class);
    }

    @Override
    public PropertiesSearchDetails getPropertyById(String property_id) {
        Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findByPropertyID(property_id);

        if (propertiesSchema.isPresent())
            return propertiesMapper.toPropertiesResponse(propertiesSchema.get());
        throw new RecordNotFoundException("Given Property id does not exist");
    }


    @Override
    public PropertyAreaResponse getSearchAreas() {
        PropertyAreaResponse propertyAreaResponse = new PropertyAreaResponse();
        Map<String, PropertyAreaDetails> searchDetailsMap = null;

        searchDetailsMap = getStringPropertyAreaDetailsMap();

        propertyAreaResponse.setPropertyAreaResponse(searchDetailsMap);
        return propertyAreaResponse;

    }

    private Map<String, PropertyAreaDetails> getStringPropertyAreaDetailsMap() {
        List<SearchSchema> searchSchemas = searchRepository.findAll();

        Map<String, PropertyAreaDetails> searchDetailsMap = new HashMap<>();

        for (Country country : searchSchemas.get(0).getCountry()) {
            for (State state : country.getState()) {
                for (City city : state.getCity()) {
                    PropertyAreaDetails propertyAreaDetails = new PropertyAreaDetails();
                    propertyAreaDetails.setCountry(country.getCountryName());
                    propertyAreaDetails.setState(state.getStateName());
                    String cityName = city.getCityName();

                    List<String> subAreaList = city.getSubArea().stream()
                            .map(SubArea::getSubAreaName)
                            .collect(Collectors.toList());

                    Set<String> areaList = city.getSubArea().stream()
                            .flatMap(subArea -> subArea.getArea().stream())
                            .map(Area::getAreaName)
                            .collect(Collectors.toSet());

                    propertyAreaDetails.setSubAreList(subAreaList);
                    propertyAreaDetails.setAreList(new ArrayList<>(areaList));
                    searchDetailsMap.put(cityName, propertyAreaDetails);


                }

            }


        }
        return searchDetailsMap;

    }


}
