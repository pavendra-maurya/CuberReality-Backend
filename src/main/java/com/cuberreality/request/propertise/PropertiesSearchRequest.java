package com.cuberreality.request.propertise;

import lombok.Data;

@Data
public class PropertiesSearchRequest {

    private String country;

    private String state;

    private String city;

    private String subArea;

    private long lat;

    private long log;
}
