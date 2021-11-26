package com.cuberreality.response.propertise;

import lombok.Data;

@Data
public class ReviewProcess {
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}
