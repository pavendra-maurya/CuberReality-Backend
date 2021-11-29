package com.cuberreality.resource;


import com.cuberreality.response.BaseResponse;
import com.cuberreality.response.VersionResponse;
import com.cuberreality.service.CommonService;
import com.cuberreality.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/v1")
@Validated
public class ReferralResource {

    @Autowired
    private ReferralService referralService;

    @RequestMapping(method = RequestMethod.GET, value = "/referral/{reseller_id}")
    public ResponseEntity<?> getReferralDetails(@PathVariable String reseller_id) {
        return new ResponseEntity<>(new BaseResponse<>(referralService.getReferralDetails(reseller_id), ""), HttpStatus.OK);
    }

}