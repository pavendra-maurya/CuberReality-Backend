package com.cuberreality.response.leads;

import lombok.Data;

import java.util.List;
//@Data
//class ModifiedBy{
//    public String name;
//    public String id;
//}
//@Data
// class CreatedBy{
//    public String name;
//    public String id;
//}
//@Data
// class Details{
//    @JsonProperty("Modified_Time")
//    public Date modified_Time;
//    @JsonProperty("Modified_By")
//    public ModifiedBy modified_By;
//    @JsonProperty("Created_Time")
//    public Date created_Time;
//    public String id;
//    @JsonProperty("Created_By")
//    public CreatedBy created_By;
//}
//@Data
// class CreateLeadResponseModel{
//    public String code;
//    public Details details;
//    public String message;
//    public String status;
//}
@Data
public class CreateLeadResponse{
    public List<CreateLeadResponseModel> data;
}