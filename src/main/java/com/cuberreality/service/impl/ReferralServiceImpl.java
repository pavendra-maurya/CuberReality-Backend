package com.cuberreality.service.impl;

import com.cuberreality.entity.referral.Referral;
import com.cuberreality.entity.user.UserProfilesSchema;
import com.cuberreality.error.RecordNotFoundException;
import com.cuberreality.repository.ReferralRepository;
import com.cuberreality.repository.UserProfileRepository;
import com.cuberreality.response.ReferralResponse;
import com.cuberreality.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ReferralServiceImpl implements ReferralService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ReferralRepository referralRepository;

    @Override
    public ReferralResponse getReferralDetails(String reseller_id) {

        ReferralResponse referralResponse = new ReferralResponse();
        Optional<UserProfilesSchema> userProfilesSchema = userProfileRepository.findByCrmUserId(reseller_id);
        String uuid = UUID.randomUUID().toString();

        if(userProfilesSchema.isPresent()){
            Referral referral = new Referral();
            referral.setReferralCode(userProfilesSchema.get().getClientID());
            referral.setReferralUuid(uuid);
            referralRepository.save(referral);


            referralResponse.setReferralCode(userProfilesSchema.get().getClientID());
            referralResponse.setReferralId(uuid);
            return referralResponse;
        }
        throw  new RecordNotFoundException("Reseller does not exist");
    }
}
