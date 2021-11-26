package com.cuberreality.service;

import org.springframework.stereotype.Service;

@Service
public interface NotificationService {

    void sendNotificationToUser(String userId, final String msgType);

    void sendWhatsAppMessage(String PhoneNumber, String msg);
}
