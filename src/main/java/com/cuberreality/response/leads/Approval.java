package com.cuberreality.response.leads;

import lombok.Data;

@Data
public class Approval{
    private boolean delegate;
    private boolean approve;
    private boolean reject;
    private boolean resubmit;
}