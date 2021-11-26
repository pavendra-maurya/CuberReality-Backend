package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Document("Properties")
public class PropertiesSearchDetails {

    @JsonProperty("_id")
    private String mongoId;

    @JsonProperty("Product_Category")
    private String productCategory;

    @JsonProperty("Description")
    private Object description;

    @JsonProperty("__currency_symbol")
    private String currencySymbol;

    @JsonProperty("BHK")
    private List<String> bHK;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("__review_process")
    private ReviewProcess reviewProcess;

    @JsonProperty("Sales_Start_Date")
    private String salesStartDate;

    @JsonProperty("Door_Site_Number")
    private Object doorSiteNumber;

    @JsonProperty("Product_Active")
    private boolean productActive;

    @JsonProperty("Record_Image")
    private Object recordImage;

    @JsonProperty("__review")
    private Object review;

    @JsonProperty("Product_Code")
    private Object productCode;

    @JsonProperty("__state")
    private String state;

    @JsonProperty("__process_flow")
    private boolean processFlow;

    @JsonProperty("id")
    private String id;

    @JsonProperty("Property_Listing_Tags")
    private List<Object> propertyListingTags;

    @JsonProperty("__approved")
    private boolean approved;

    @JsonProperty("Status")
    private List<String> status;

    @JsonProperty("__approval")
    private Approval approval;

    @JsonProperty("Modified_Time")
    private LocalDateTime modifiedTime;

    @JsonProperty("Sub_Area")
    private String subArea;

    @JsonProperty("Created_Time")
    private LocalDateTime createdTime;

    @JsonProperty("__followed")
    private boolean followed;

    @JsonProperty("Product_Name")
    private String productName;

    @JsonProperty("Bank_Approved")
    private List<String> bankApproved;

    @JsonProperty("__taxable")
    private boolean taxable;

    @JsonProperty("__editable")
    private boolean editable;

    @JsonProperty("Property_Type")
    private List<String> propertyType;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Builder_Name")
    private String builderName;

    @JsonProperty("Owner_Details")
    private Object ownerDetails;

    @JsonProperty("__orchestration")
    private Object orchestration;

    @JsonProperty("Property_ID")
    private String propertyID;

    @JsonProperty("Price_Range")
    private String priceRange;

    @JsonProperty("Area")
    private String area;

    @JsonProperty("__in_merge")
    private boolean inMerge;

    @JsonProperty("State")
    private Object countryState;

    @JsonProperty("Property_Referred_By")
    private Object propertyReferredBy;

    @JsonProperty("Country")
    private Object country;

    @JsonProperty("Tag")
    private List<Object> tag;

    @JsonProperty("__approval_state")
    private String approvalState;

    @JsonProperty("Sales_End_Date")
    private String salesEndDate;

    @JsonProperty("Unit_Price")
    private int unitPrice;

    @JsonProperty("Taxable")
    private boolean propertyTaxable;

    @JsonProperty("PID")
    private String pID;

    private FloorPlan floorPlan;
    private ImageUrl imageUrl;
    private PlotSize plotSize;
    private String projectName;
    private ProjectSpecification projectSpecification;
    private ReraApproved reraApproved;
}
