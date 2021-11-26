package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class ImageUrl {
    @JsonProperty("focused_img_url")
    private List<String> focusedImgUrl;

    @JsonProperty("broucher_url")
    private List<String> broucherUrl;

    @JsonProperty("BuilderLogo_url")
    private List<String> builderLogoUrl;

    @JsonProperty("MasterPlan_url")
    private List<String> masterPlanUrl;

    @JsonProperty("PaymentPlan_url")
    private List<String> paymentPlanUrl;

    @JsonProperty("ProjectImages_url")
    private List<String> projectImagesUrl;

    @JsonProperty("ProjectLogo_url")
    private List<String> projectLogoUrl;

    @JsonProperty("UnitPhotosUrl")
    private List<String> unitPhotosUrl;

    @JsonProperty("Videos_url")
    private List<String> videosUrl;

    @JsonProperty("FloorPlans_url")
    private FloorPlansUrl floorPlansUrl;
}
