package com.cuberreality.service;

public interface NotificationService {

    void sendNotificationToUser(String userId, final String msgType);

    void sendWhatsAppMessage(String PhoneNumber, String msg);
}
