package com.cuberreality.response.propertise;

import lombok.Data;

@Data
public class Approval {
    private boolean delegate;
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}
