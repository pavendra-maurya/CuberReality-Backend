package com.cuberreality.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Document("Properties")
@Data
public class PropertiesSchema{

    @Field("_id")
    private String mongoId;

    @Field("Product_Category")
    private String productCategory;

    @Field("Owner")
    private Owner owner;

    @Field("Description")
    private Object description;

    private String __currency_symbol;

    @Field("BHK")
    private List<String> bHK;

    @Field("Address")
    private String address;

    @Field("__review_process")
    private ReviewProcess reviewProcess;

    @Field("Sales_Start_Date")
    private String salesStartDate;

    @Field("__followers")
    private Object followers;

    @Field("Door_Site_Number")
    private Object doorSiteNumber;

    @Field("Product_Active")
    private boolean productActive;

    @Field("Record_Image")
    private Object recordImage;

    @Field("Modified_By")
    private ModifiedBy modifiedBy;

    @Field("__review")
    private Object __review;

    @Field("Product_Code")
    private Object productCode;

    @Field("__state")
    private String __state;

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
    private Object state;


    @Field("Property_Referred_By")
    private Object propertyReferredBy;

    @Field("Country")
    private Object country;

    @Field("Tag")
    private List<Object> tag;

    @Field("Created_By")
    private CreatedBy createdBy;

    @Field("__approval_state")
    private String approvalState;

    @Field("Sales_End_Date")
    private String salesEndDate;

    @Field("Unit_Price")
    private int unitPrice;

    @Field("PID")
    private String pID;
    private ImageUrl imageUrl;
    private PlotSize plotSize;
    private String projectName;
    private ProjectSpecification projectSpecification;
    private ReraApproved reraApproved;
}

@Data
class Owner{
    private String name;
    @Field("id")
    private String id;
    private String email;
}

@Data
class ReviewProcess{
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}

@Data
class ModifiedBy{
    private String name;
    @Field("id")
    private String id;
    private String email;
}

@Data
class Approval{
    private boolean delegate;
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}

@Data
class CreatedBy{
    private String name;
    @Field("id")
    private String id;
    private String email;
}

@Data
class _2BHK{
    @Field("Type1")
    private List<String> type1;
    @Field("Type2")
    private List<String> type2;
    @Field("Type3")
    private List<String> type3;
    @Field("Type4")
    private List<String> type4;
    @Field("Type5")
    private List<String> type5;
    @Field("Type6")
    private List<String> type6;
}

@Data
class _3BHK{
    @Field("Type1")
    private List<String> type1;
    @Field("Type2")
    private List<String> type2;
    @Field("Type3")
    private List<String> type3;
    @Field("Type4")
    private List<String> type4;
    @Field("Type5")
    private List<String> type5;
    @Field("Type6")
    private List<String> type6;
}

@Data
class FloorPlansUrl{

    @Field("2BHK")
    private _2BHK twoBHK;

    @Field("3BHK")
    private _3BHK threeBHK;
}

@Data
class ImageUrl{

    @Field("broucher_url")
    private List<String> broucherUrl;

    @Field("BuilderLogo_url")
    private List<String> builderLogoUrl;

    @Field("MasterPlan_url")
    private List<String> masterPlanUrl;

    @Field("PaymentPlan_url")
    private List<String> paymentPlanUrl;

    @Field("ProjectImages_url")
    private List<String> projectImagesUrl;

    @Field("ProjectLogo_url")
    private List<String> projectLogoUrl;

    @Field("UnitPhotosUrl")
    private List<String> unitPhotosUrl;

    @Field("Videos_url")
    private List<String> videosUrl;

    @Field("FloorPlans_url")
    private FloorPlansUrl floorPlans_url;
}

@Data
class PlotSize{
    private String plotArea;
    private String plotDimension;
}

@Data
class ProjectSpecification{
    private String totalProjectArea;
    private String noOfUnits;
    private String noOfFloors;
    private String towersBlocks;
    private String approvedBy;
    private String geoLocation;
    private String commission;
    private String aminities;
    private String landmarks;
    private String projectDescription;
    private String specifications;
}

@Data
class ReraApproved{

    @Field("Phase One")
    private String phaseOne;

    @Field("Phase Two")
    private String phaseTwo;
}