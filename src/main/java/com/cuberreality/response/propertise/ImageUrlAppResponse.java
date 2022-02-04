package com.cuberreality.response.propertise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ImageUrlAppResponse {
    @JsonProperty("focused_img_url")
    private List<String> focusedImgUrl;

    @JsonProperty("Highlight_url")
    private List<String> highlightUrl;
}
