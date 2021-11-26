package com.cuberreality.entity.propertise;

import lombok.Data;

@Data
public class Approval {
    private boolean delegate;
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}
