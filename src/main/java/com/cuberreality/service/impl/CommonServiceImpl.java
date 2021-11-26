package com.cuberreality.service.impl;

import com.cuberreality.config.AppConfig;
import com.cuberreality.response.VersionResponse;
import com.cuberreality.service.CommonService;
import com.cuberreality.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private AppConfig appConfig;

//    @Autowired
//    private NotificationService notificationService;
//

    @Override
    public VersionResponse getResellerVersion() {

        return new VersionResponse(appConfig.getReseller_app_version());
    }
}
