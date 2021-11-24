package com.cuberreality.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateLeadModel {

    @JsonProperty("Buyer_ID")
    private String Buyer_ID;

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

    @JsonProperty("Buyer_Mobile_Number")
    private String Buyer_Mobile_Number;

    @JsonProperty("Pipeline")
    private String Pipeline;

    @JsonProperty("Property_ID")
    private String Property_ID;
    @JsonProperty("Buyer_Name")
    private String Buyer_Name;
    @JsonProperty("$approval_state")
    private String $approval_state;
    @JsonProperty("Property_Name")
    private String Property_Name;


}
