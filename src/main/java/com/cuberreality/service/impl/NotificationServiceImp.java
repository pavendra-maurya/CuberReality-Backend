package com.cuberreality.service.impl;

import com.cuberreality.config.AppConfig;
import com.cuberreality.service.NotificationService;
import com.cuberreality.service.UserLoginService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NotificationServiceImp implements NotificationService {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private AppConfig appConfig;


    @Override
    public void sendNotificationToUser(String userId, final String msgType) {

        new Thread(() -> createNotificationToUser(userId, msgType)).start();

    }

    private void createNotificationToUser(String userId, final String msgType) {

        String deviceToken = getUserDeviceId(userId);

        if ("New".equals(msgType)) {
            sendNotificationToUserApiCall(newPropertyAdded(deviceToken));
        } else {
            System.out.println("there is no need to trigger  notification for " + msgType);
        }

    }

    private String getUserDeviceId(String userID) {
        return userLoginService.getDeviceToken(userID);

    }

    private String newPropertyAdded(String deviceToken) {
        JSONObject payload = new JSONObject();
        JSONObject dataPayload = new JSONObject();
        try {
            dataPayload.put("type", "New property");
            dataPayload.put("title", "New Hot deals");
            dataPayload.put("msg", "New property has been added please share with customer");
            dataPayload.put("status", "New ");
            dataPayload.put("priority", "");
            payload.put("to", deviceToken);
            payload.put("data", dataPayload);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload.toString();
    }

    private void sendNotificationToUserApiCall(String payload) {
        System.out.print(payload);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, payload);
        Request request = new Request.Builder().url("https://fcm.googleapis.com/fcm/send").post(body)
                .addHeader("authorization", appConfig.getReseller_notification_server_key())
                .addHeader("content-type", "application/json").build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

    public void sendWhatsAppMessage(String PhoneNumber, String msg) {

        String PhoneNum = PhoneNumber;

        if (!PhoneNumber.startsWith("+91")) {
            PhoneNum = "+91" + PhoneNum;
        }

        Twilio.init(appConfig.getAccount_sid(), appConfig.getAuth_token());
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:"+PhoneNum),
                        new com.twilio.type.PhoneNumber("whatsapp:"+appConfig.getRegister_phone_number()),
                        msg )
                .create();
        System.out.println(message.getSid());
    }


}