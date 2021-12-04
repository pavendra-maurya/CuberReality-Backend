package com.cuberreality.response.leads;

import lombok.Data;

@Data
public class ReviewProcess{
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}