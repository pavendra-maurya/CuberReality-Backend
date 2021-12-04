package com.cuberreality.response.leads;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetLeadResponseModel {


    @JsonProperty("Property_Catagory")
    private List<String> property_Catagory;
    @JsonProperty("Owner")
    private CreatedBy owner;
    @JsonProperty("Buyer_ID")
    private String buyer_ID;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("__currency_symbol")
    private String currencySymbol;
    @JsonProperty("__review_process")
    private ReviewProcess reviewProcess;
    @JsonProperty("__followers")
    private String followers;
    @JsonProperty("__sharing_permission")
    private String sharingPermission;
    @JsonProperty("Last_Activity_Time")
    private String last_Activity_Time;
    @JsonProperty("Modified_By")
    private CreatedBy modified_By;
    @JsonProperty("__review")
    private String review;
    @JsonProperty("__state")
    private String state;
    @JsonProperty("__process_flow")
    private boolean processFlow;
    @JsonProperty("Deal_Name")
    private String deal_Name;
    @JsonProperty("Associated_Products")
    private List<String> associated_Products;
    @JsonProperty("Stage")
    private String stage;
    private String id;
    @JsonProperty("__approved")
    private boolean approved;
    @JsonProperty("__approval")
    private Approval approval;
    @JsonProperty("Modified_Time")
    private Date modified_Time;
    @JsonProperty("Created_Time")
    private Date created_Time;
    @JsonProperty("Reseller_Name")
    private String reseller_Name;
    @JsonProperty("Reseller_Loan_Agent")
    private boolean reseller_Loan_Agent;
    @JsonProperty("__followed")
    private boolean followed;
    @JsonProperty("Reseller_ID")
    private String reseller_ID;
    @JsonProperty("__editable")
    private boolean editable;
    @JsonProperty("Property_Type")
    private List<String> property_Type;
    @JsonProperty("Buyer_Mobile_Number")
    private String buyer_Mobile_Number;
    @JsonProperty("__orchestration")
    private String orchestration;
    @JsonProperty("Pipeline")
    private String pipeline;
    @JsonProperty("Property_ID")
    private String property_ID;
    @JsonProperty("__in_merge")
    private boolean inMerge;
    @JsonProperty("Buyer_Name")
    private String buyer_Name;
    @JsonProperty("Tag")
    private List<String> tag;
    @JsonProperty("Created_By")
    private CreatedBy created_By;
    @JsonProperty("__approval_state")
    private String approvalState;
    @JsonProperty("Property_Name")
    private String property_Name;
}
