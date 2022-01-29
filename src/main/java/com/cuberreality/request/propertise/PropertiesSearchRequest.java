package com.cuberreality.request.propertise;

import lombok.Data;

import java.util.List;

@Data
public class PropertiesSearchRequest {

    private String country;

    private String state;

    private String city;

    private boolean isFocusedPropertyBasedOnCity;

    private String listPropertyBasedOn;

    private List<String> subAreaList;

    private List<String> areaList;

}
