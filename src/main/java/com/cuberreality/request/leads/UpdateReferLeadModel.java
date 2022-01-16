package com.cuberreality.request.leads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateReferLeadModel {


    @JsonProperty("Deal_Name")
    private String Deal_Name;

    @JsonProperty("Owner_Mobile")
    private String Owner_Mobile;

    @JsonProperty("Owner_Name")
    private String Owner_Name;

    @JsonProperty("Description")
    private String Description;

    @JsonProperty("Reseller_Comments")
    private String Reseller_Comments;

}
