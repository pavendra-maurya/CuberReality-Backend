package com.cuberreality.entity.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.cuberreality.constant.Schema.PROPERTIES_SCHEMA;


@Data
@Document(PROPERTIES_SCHEMA)
public class PropertiesSchema {

    @Field("_id")
    private String mongoId;

    @Field("Product_Category")
    private String productCategory;

    @Field("Description")
    private Object description;

    @Field("__currency_symbol")
    private String currencySymbol;

    @Field("BHK")
    private List<String> bHK;

    @Field("Address")
    private String address;

    @Field("__review_process")
    private ReviewProcess reviewProcess;

    @Field("Sales_Start_Date")
    private String salesStartDate;

    @Field("Door_Site_Number")
    private Object doorSiteNumber;

    @Field("Product_Active")
    private boolean productActive;

    @Field("Record_Image")
    private Object recordImage;

    @Field("__review")
    private Object review;

    @Field("Product_Code")
    private Object productCode;

    @Field("__state")
    private String state;

    @Field("__process_flow")
    private boolean processFlow;

    @Field("id")
    private String id;

    @Field("Property_Listing_Tags")
    private List<Object> propertyListingTags;

    @Field("__approved")
    private boolean approved;

    @Field("Status")
    private List<String> status;

    @Field("__approval")
    private Approval approval;

    @Field("Modified_Time")
    private LocalDateTime modifiedTime;

    @Field("Sub_Area")
    private String subArea;

    @Field("Created_Time")
    private LocalDateTime createdTime;

    @Field("__followed")
    private boolean followed;

    @Field("Product_Name")
    private String productName;

    @Field("Bank_Approved")
    private List<String> bankApproved;

    @Field("__taxable")
    private boolean taxable;

    @Field("__editable")
    private boolean editable;

    @Field("Property_Type")
    private List<String> propertyType;

    @Field("City")
    private String city;

    @Field("Builder_Name")
    private String builderName;

    @Field("Owner_Details")
    private Object ownerDetails;

    @Field("__orchestration")
    private Object orchestration;

    @Field("Property_ID")
    private String propertyID;

    @Field("Price_Range")
    private String priceRange;

    @Field("Area")
    private String area;

    @Field("__in_merge")
    private boolean inMerge;

    @Field("State")
    private Object countryState;

    @Field("Property_Referred_By")
    private Object propertyReferredBy;

    @Field("Country")
    private Object country;

    @Field("Tag")
    private List<Object> tag;

    @Field("__approval_state")
    private String approvalState;

    @Field("Sales_End_Date")
    private String salesEndDate;

    @Field("Unit_Price")
    private int unitPrice;

    @Field("Taxable")
    private boolean propertyTaxable;

    @Field("PID")
    private String pID;

   // private FloorPlan floorPlan;
   Map<String, Map<String,Type>> floorPlan;
    private ImageUrl imageUrl;
    private PlotSize plotSize;
    private String projectName;
    private ProjectSpecification projectSpecification;
    private ReraApproved reraApproved;

    @Field("UnitSpecification")
    public UnitSpecification unitSpecification;


}
