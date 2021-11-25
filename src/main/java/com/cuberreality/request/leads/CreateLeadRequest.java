package com.cuberreality.request.leads;

import com.cuberreality.request.leads.CreateLeadModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class CreateLeadRequest {

    @JsonProperty("data")
    List<CreateLeadModel> data;













//    @JsonProperty("Reseller_Loan_Agent")
//    private String Reseller_Loan_Agent;
//    @JsonProperty("Reseller_ID")
//    private String Reseller_ID;
//    @JsonProperty("Modified_Time")
//    private String Modified_Time;
//    @JsonProperty("Buyer_Mobile_Number")
//    private String Buyer_Mobile_Number;
//    @JsonProperty("Created_Time")
//    private String Created_Time;
//    @JsonProperty("__state")
//    private String __state;
//    @JsonProperty("Buyer_ID")
//    private String Buyer_ID;
//    @JsonProperty("__sharing_permission")
//    private String __sharing_permission;
//    @JsonProperty("__orchestration")
//    private String __orchestration;
//    @JsonProperty("Property_Catagory")
//    private List<String> Property_Catagory;
//    @JsonProperty("Description")
//    private String Description;
//    @JsonProperty("__followers")
//    private String __followers;
//    @JsonProperty("__approval_state")
//    private String __approval_state;
//    @JsonProperty("__currency_symbol")
//    private String __currency_symbol;
//    @JsonProperty("Created_By")
//    private CreatedBy Created_By;
//
//
//    @JsonProperty("__approved")
//    private String __approved;
//
//    @JsonProperty("__approval")
//    private Approval __approval;
//
//
//    @JsonProperty("__review_process")
//    private ReviewProcess __review_process;
//
//    @JsonProperty("Reseller_Name")
//    private String Reseller_Name;
//    @JsonProperty("Pipeline")
//    private String Pipeline;
//    @JsonProperty("Stage")
//    private String Stage;
//    @JsonProperty("__process_flow")
//    private String __process_flow;
//    @JsonProperty("Buyer_Name")
//    private String Buyer_Name;
//    @JsonProperty("__editable")
//    private String __editable;
//    @JsonProperty("__in_merge")
//    private String __in_merge;
//    @JsonProperty("Last_Activity_Time")
//    private String Last_Activity_Time;
//    @JsonProperty("Property_Type")
//    private List<String> Property_Type;
//    @JsonProperty("__review")
//    private String __review;
//    @JsonProperty("Modified_By")
//    private CreatedBy modifiedBy;
//    @JsonProperty("__followed")
//    private String __followed;
//    @JsonProperty("Tag")
//    private List<String> Tag;
//    @JsonProperty("Owner")
//    private CreatedBy Owner;
//    @JsonProperty("Deal_Name")
//    private String Deal_Name;
//
//    @JsonProperty("Property_ID")
//    private String Property_ID;
//
//
}
//
//class CreatedBy{
//    @JsonProperty("email")
//    private String email;
//    @JsonProperty("name")
//    private String name;
//    @JsonProperty("id")
//    private String id;
//
//}
//
//class Approval{
//    @JsonProperty("resubmit")
//    private String resubmit;
//    @JsonProperty("approve")
//    private String approve;
//    @JsonProperty("delegate")
//    private String delegate;
//    @JsonProperty("reject")
//    private String reject;
//
//}
//
//class ReviewProcess{
//    @JsonProperty("resubmit")
//    private String resubmit;
//    @JsonProperty("approve")
//    private String approve;
//    @JsonProperty("reject")
//    private String reject;
//
//}
