package com.cuberreality.response.propertise;

import com.cuberreality.entity.propertise.Type;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@Document("Properties")
public class PropertiesSearchDetails {


    @JsonProperty("Product_Category")
    private String productCategory;

    @JsonProperty("Description")
    private Object description;


    @JsonProperty("BHK")
    private List<String> bHK;

    @JsonProperty("Address")
    private String address;


    @JsonProperty("Sales_Start_Date")
    private String salesStartDate;

    @JsonProperty("Door_Site_Number")
    private Object doorSiteNumber;

    @JsonProperty("Product_Active")
    private boolean productActive;

    @JsonProperty("Record_Image")
    private Object recordImage;

    @JsonProperty("Product_Code")
    private Object productCode;

    @JsonProperty("id")
    private String id;

    @JsonProperty("Property_Listing_Tags")
    private List<Object> propertyListingTags;


    @JsonProperty("Status")
    private List<String> status;


    @JsonProperty("Modified_Time")
    private LocalDateTime modifiedTime;

    @JsonProperty("Sub_Area")
    private String subArea;

    @JsonProperty("Created_Time")
    private LocalDateTime createdTime;


    @JsonProperty("Product_Name")
    private String productName;

    @JsonProperty("Bank_Approved")
    private List<String> bankApproved;


    @JsonProperty("Property_Type")
    private List<String> propertyType;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Builder_Name")
    private String builderName;

    @JsonProperty("Owner_Details")
    private Object ownerDetails;

    @JsonProperty("Property_ID")
    private String propertyID;

    @JsonProperty("Price_Range")
    private String priceRange;

    @JsonProperty("Area")
    private String area;

    @JsonProperty("State")
    private Object countryState;

    @JsonProperty("Property_Referred_By")
    private Object propertyReferredBy;

    @JsonProperty("Country")
    private Object country;

    @JsonProperty("Tag")
    private List<Object> tag;


    @JsonProperty("Unit_Price")
    private int unitPrice;

    @JsonProperty("PID")
    private String pID;

    Map<String, Map<String, Type>> floorPlan;

    private ImageUrl imageUrl;
    private PlotSize plotSize;
    private String projectName;
    private ProjectSpecification projectSpecification;
    private ReraApproved reraApproved;

    private String AreaRange;

    @JsonProperty("video_urls")
    private List<String> videoUrls;

    public UnitSpecification unitSpecification;

}
