package com.cuberreality.entity.leads;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static com.cuberreality.constant.Schema.LEAD_SCHEMA;

@Document(LEAD_SCHEMA)
@Data
public class LeadsSchema {
    @Field("_id")
    private BigInteger mongoId;

    @Field("Property_Catagory")
    public List<String> property_Catagory;
    @Field("Owner")
    public com.cuberreality.response.leads.CreatedBy owner;
    @Field("Buyer_ID")
    public String buyer_ID;
    @Field("Description")
    public String description;
    @Field("__currency_symbol")
    public String currencySymbol;
    @Field("__review_process")
    public com.cuberreality.response.leads.ReviewProcess reviewProcess;
    @Field("__followers")
    public String followers;
    @Field("__sharing_permission")
    public String sharingPermission;
    @Field("Last_Activity_Time")
    public String last_Activity_Time;
    @Field("Modified_By")
    public com.cuberreality.response.leads.CreatedBy modified_By;
    @Field("__review")
    public String review;
    @Field("__state")
    public String state;
    @Field("__process_flow")
    public boolean processFlow;
    @Field("Deal_Name")
    public String deal_Name;
    @Field("Associated_Products")
    public List<String> associated_Products;
    @Field("Stage")
    public String stage;
    @Field("leadId")
    public String leadId;
    @Field("__approved")
    public boolean approved;
    @Field("__approval")
    public com.cuberreality.response.leads.Approval approval;
    @Field("Modified_Time")
    public Date modified_Time;
    @Field("Created_Time")
    public Date created_Time;
    @Field("Reseller_Name")
    public String reseller_Name;
    @Field("Reseller_Loan_Agent")
    public boolean reseller_Loan_Agent;
    @Field("__followed")
    public boolean followed;
    @Field("Reseller_ID")
    public String reseller_ID;
    @Field("__editable")
    public boolean editable;
    @Field("Property_Type")
    public List<String> property_Type;
    @Field("Buyer_Mobile_Number")
    public String buyer_Mobile_Number;
    @Field("__orchestration")
    public String orchestration;
    @Field("Pipeline")
    public String pipeline;
    @Field("Property_ID")
    public String property_ID;
    @Field("__in_merge")
    public boolean inMerge;
    @Field("Buyer_Name")
    public String buyer_Name;
    @Field("Tag")
    public List<String> tag;
    @Field("Created_By")
    public com.cuberreality.response.leads.CreatedBy created_By;
    @Field("__approval_state")
    public String approvalState;
    @Field("Property_Name")
    public String property_Name;



}