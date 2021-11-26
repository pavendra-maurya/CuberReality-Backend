package com.cuberreality.resource;


import com.cuberreality.response.VersionResponse;
import com.cuberreality.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/v1")
@Validated
public class CommonResource {

    @Autowired
    private CommonService commonService;

    @RequestMapping(method = RequestMethod.GET, value = "/reseller_app/version")
    public VersionResponse getShopAppCurrentVersion() {
        return commonService.getResellerVersion();
    }

}