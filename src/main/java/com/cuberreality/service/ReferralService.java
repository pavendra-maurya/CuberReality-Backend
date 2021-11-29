package com.cuberreality.service;

import com.cuberreality.response.ReferralResponse;
import com.cuberreality.response.VersionResponse;

public interface ReferralService {
    ReferralResponse getReferralDetails(String reseller_id);
}
