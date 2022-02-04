package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;


@Data
public class PropertiesSearchDetailsAppResponse {

    @JsonProperty("Product_Category")
    private String productCategory;

    @JsonProperty("BHK")
    private List<String> bHK;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("Product_Code")
    private Object productCode;

    @JsonProperty("id")
    private String id;

    @JsonProperty("Sub_Area")
    private String subArea;

    @JsonProperty("Product_Name")
    private String productName;

    @JsonProperty("Property_Type")
    private List<String> propertyType;

    @JsonProperty("Status")
    private List<String> status;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Builder_Name")
    private String builderName;

    @JsonProperty("Property_ID")
    private String propertyID;

    @JsonProperty("Price_Range")
    private String priceRange;

    @JsonProperty("Area")
    private String area;

    @JsonProperty("State")
    private Object countryState;

    @JsonProperty("Country")
    private Object country;


    @JsonProperty("Unit_Price")
    private int unitPrice;

    @JsonProperty("PID")
    private String pID;

    private ImageUrlAppResponse imageUrl;

    private PlotSize plotSize;

    private String projectName;

    private String AreaRange;

}
