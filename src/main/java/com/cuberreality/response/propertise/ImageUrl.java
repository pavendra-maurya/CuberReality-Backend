package com.cuberreality.response.propertise;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class ImageUrl {
    @Field("focused_img_url")
    private List<String> focusedImgUrl;

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
    private FloorPlansUrl floorPlansUrl;
}
