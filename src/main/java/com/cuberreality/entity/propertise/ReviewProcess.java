package com.cuberreality.entity.propertise;

import lombok.Data;

@Data
public class ReviewProcess {
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}
