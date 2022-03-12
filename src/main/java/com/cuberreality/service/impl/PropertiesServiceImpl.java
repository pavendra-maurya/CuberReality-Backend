package com.cuberreality.service.impl;


import com.cuberreality.entity.propertise.PropertiesSchema;
import com.cuberreality.entity.propertisesearch.*;
import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.entity.user.VisitedProperties;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.mapper.PropertiesMapper;
import com.cuberreality.repository.PropertiesRepository;
import com.cuberreality.repository.SearchRepository;
import com.cuberreality.repository.UserProfileRepository;
import com.cuberreality.repository.VisitedPropertiesRepository;
import com.cuberreality.request.propertise.PropertiesSearchRequest;
import com.cuberreality.response.propertise.*;
import com.cuberreality.response.user.UserJwtTokenValidationResponse;
import com.cuberreality.service.PropertiesService;
import com.cuberreality.service.UserLoginService;
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
    private UserProfileRepository userProfileRepository;

    @Autowired
    private PropertiesMapper propertiesMapper;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    MongoTemplate mongoTemplate;

    public PropertiesServiceImpl() {
    }

    @Override
    public PropertiesSearchResponse getPropertiesInSpace(PropertiesSearchRequest propertiesSearchRequest) {

        UserJwtTokenValidationResponse jwtTokenValidationResponse = userLoginService.userJwtTokenValidation();
        String userUuid = jwtTokenValidationResponse.getUuid();
        System.out.println(userUuid);
        UserProfilesSchema userProfilesSchema = userProfileRepository.findByUserUuid(userUuid).get();

        List<SearchSchema> searchSchemas = searchRepository.findAll();

        PropertiesSearchResponse response = new PropertiesSearchResponse();

        if (getCityMapping().get(propertiesSearchRequest.getCity()) != null)
            propertiesSearchRequest.setCity(getCityMapping().get(propertiesSearchRequest.getCity()));

        List<List<String>> propertyIdList = getPropertyIds(searchSchemas, propertiesSearchRequest);

        List<String> regularPropertiesIds = propertyIdList.get(1);
        List<String> focusedPropertiesIds = propertyIdList.get(0);

        List<PropertiesSearchDetailsAppResponse> regularPropertiesSearch = new ArrayList<>();
        List<PropertiesSearchDetailsAppResponse> focusedPropertiesSearch = new ArrayList<>();

        for (String id : regularPropertiesIds) {
            Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findByIdAndProductActive(id, true);
            if (propertiesSchema.isPresent()) {
                PropertiesSchema schema = propertiesSchema.get();
                if (userProfilesSchema.isEmployee()) {
                    regularPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                    if (!propertiesSearchRequest.isFocusedPropertyBasedOnCity()) {
                        if (schema.isPropertyTaxable())
                            focusedPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                    }
                } else {
                    if (schema.getPreferredProperty() < 1) {
                        regularPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                        if (!propertiesSearchRequest.isFocusedPropertyBasedOnCity()) {
                            if (schema.isPropertyTaxable())
                                focusedPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                        }
                    }
                }

            }
        }
        for (String id : focusedPropertiesIds) {
            Optional<PropertiesSchema> propertiesSchema = propertiesRepository.findByIdAndProductActive(id, true);
            if (propertiesSchema.isPresent()) {
                PropertiesSchema schema = propertiesSchema.get();
                if (userProfilesSchema.isEmployee()) {
                    focusedPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                } else {
                    if (schema.getPreferredProperty() < 1) {
                        focusedPropertiesSearch.add(propertiesMapper.toPropertiesAppResponse(schema));
                    }
                }

            }
        }
        response.setFocusedProperties(focusedPropertiesSearch);
        response.setRegularProperties(regularPropertiesSearch);
        return response;
    }

    private List<List<String>> getPropertyIds(List<SearchSchema> searchSchema,
                                              PropertiesSearchRequest propertiesSearchRequest) {

        List<City> cityList = searchSchema.get(0).getCountry().stream()
                .filter(obj -> obj.getCountryName().equalsIgnoreCase(propertiesSearchRequest.getCountry()))
                .flatMap(list -> list.getState().stream())
                .filter(obj -> obj.getStateName().equalsIgnoreCase(propertiesSearchRequest.getState()))
                .flatMap(list -> list.getCity().stream())
                .filter(city -> city.getCityName().equalsIgnoreCase(propertiesSearchRequest.getCity()))
                .collect(Collectors.toList());

        List<String> normalProperty = new ArrayList<>();

        List<String> focusProperty = new ArrayList<>();

        if (propertiesSearchRequest.isFocusedPropertyBasedOnCity())
            focusProperty = cityList.stream()
                    .flatMap(obj -> obj.getSubArea().stream())
                    .flatMap(obj -> obj.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .filter(PropertyData::isFocused)
                    .map(PropertyData::getId)
                    .collect(Collectors.toList());

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("city"))
            normalProperty = cityList.stream()
                    .flatMap(obj -> obj.getSubArea().stream())
                    .flatMap(obj -> obj.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId)
                    .collect(Collectors.toList());

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("area")) {
            normalProperty = cityList.stream()
                    .flatMap(list -> list.getSubArea().stream())
                    .flatMap(list -> list.getArea().stream())
                    .filter(obj -> propertiesSearchRequest.getAreaList().contains(obj.getAreaName()))
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId).collect(Collectors.toList());
        }

        if (propertiesSearchRequest.getListPropertyBasedOn().equalsIgnoreCase("subArea")) {
            normalProperty = cityList.stream()
                    .flatMap(list -> list.getSubArea().stream())
                    .filter(obj -> propertiesSearchRequest.getSubAreaList().contains(obj.getSubAreaName()))
                    .flatMap(list -> list.getArea().stream())
                    .flatMap(obj -> obj.getPropertyData().stream())
                    .filter(PropertyData::isActive)
                    .map(PropertyData::getId).collect(Collectors.toList());
        }

        List<List<String>> data = new ArrayList<>();
        data.add(focusProperty);
        data.add(normalProperty);
        return data;
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

    public void update(String id, int count) {
        Query query = new Query();
        query.addCriteria(Criteria.where("property_id").is(id));
        Update update = new Update();
        update.set("visit_count", count + 1);
        mongoTemplate.findAndModify(query, update, VisitedProperties.class);
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
        Map<String, PropertyAreaDetails> searchDetailsMap;

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
                    String cityName = city.getCityName();

                    if (!getLiveCity().contains(cityName.toLowerCase()))
                        continue;

                    if (!Objects.isNull(getCityMapping().get(cityName)))
                        cityName = getCityMapping().get(cityName);

                    PropertyAreaDetails propertyAreaDetails = new PropertyAreaDetails();
                    propertyAreaDetails.setCountry(country.getCountryName());
                    propertyAreaDetails.setState(state.getStateName());

                    Set<String> subAreaList = city.getSubArea().stream()
                            .map(SubArea::getSubAreaName)
                            .collect(Collectors.toSet());

                    Set<String> areaList = city.getSubArea().stream()
                            .flatMap(subArea -> subArea.getArea().stream())
                            .map(Area::getAreaName)
                            .collect(Collectors.toSet());

                    if (searchDetailsMap.get(cityName) != null) {
                        Set<String> availableSubArea = new HashSet<>(searchDetailsMap.get(cityName).getSubAreList());
                        Set<String> availableArea = new HashSet<>(searchDetailsMap.get(cityName).getAreList());
                        availableArea.addAll(areaList);
                        availableSubArea.addAll(subAreaList);
                        propertyAreaDetails.setSubAreList(new ArrayList<>(availableArea));
                        propertyAreaDetails.setAreList(new ArrayList<>(availableSubArea));

                    } else {
                        propertyAreaDetails.setSubAreList(new ArrayList<>(subAreaList));
                        propertyAreaDetails.setAreList(new ArrayList<>(areaList));
                    }
                    searchDetailsMap.put(cityName, propertyAreaDetails);
                }
            }
        }
        return addMultipleCity(searchDetailsMap);
    }


    private Map<String, PropertyAreaDetails> addMultipleCity(Map<String, PropertyAreaDetails> searchDetailsMap) {

        Map<String, PropertyAreaDetails> propertyAreaDetailsMap = new HashMap<>();

        Map<String, Set<String>> listMap = new HashMap<>();
        getCityMapping().forEach((key, value) -> {
            listMap.computeIfAbsent(value, k -> new HashSet<>());
            listMap.get(value).add(key);
        });
        listMap.forEach((key, value) -> value.forEach((city) -> propertyAreaDetailsMap.put(city, searchDetailsMap.get(key))));

        propertyAreaDetailsMap.putAll(searchDetailsMap);

        return propertyAreaDetailsMap;
    }

    private Map<String, String> getCityMapping() {
        Map<String, String> cities = new HashMap<>();
        cities.put("Bengaluru", "Bangalore");
        return cities;
    }

    private HashSet<String> getLiveCity() {
        HashSet<String> cities = new HashSet<>();
        cities.add("bangalore");
        cities.add("bengaluru");
        return cities;
    }

}
