package com.cuberreality.response.propertise;

import lombok.Data;

import java.util.List;

@Data
public class PropertyAreaDetails {

    String state;
    String country;
    List<String> subAreList;
}
