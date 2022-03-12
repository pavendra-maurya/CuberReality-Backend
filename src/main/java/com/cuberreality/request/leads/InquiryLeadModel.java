package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InquiryLeadModel {

    @JsonProperty("Description")
    private String Description;

    @JsonProperty("Deal_Name")
    private String Deal_Name;
    @JsonProperty("Stage")
    private String Stage;
    @JsonProperty("Reseller_Name")
    private String Reseller_Name;

    @JsonProperty("Reseller_Loan_Agent")
    private boolean Reseller_Loan_Agent;

    @JsonProperty("Reseller_ID")
    private String Reseller_ID;

    @JsonProperty("$editable")
    private boolean $editable;

    @JsonProperty("Owner_Mobile")
    private String Owner_Mobile;

    @JsonProperty("Pipeline")
    private String Pipeline;

//    @JsonProperty("Property_ID")
//    private String Property_ID;
    @JsonProperty("Owner_Name")
    private String Owner_Name;
    @JsonProperty("$approval_state")
    private String $approval_state;
    @JsonProperty("Property_Name")
    private String Property_Name;

//    @JsonProperty("userPropertyId")
//    private String userPropertyId; // not required

    @JsonProperty("Reseller_Comments")
    private String Reseller_Comments;

}
