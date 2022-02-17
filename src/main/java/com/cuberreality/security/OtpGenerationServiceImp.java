package com.cuberreality.security;

import com.cuberreality.config.AppConfig;
import com.cuberreality.error.OtpException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class OtpGenerationServiceImp implements OtpGenerationService {

    @Autowired
    private AppConfig appConfig;

    private LoadingCache<String, String> otpCache;
    private static final int OTP_EXPIRED_SEC = 300;

    private OtpGenerationServiceImp() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(OTP_EXPIRED_SEC, TimeUnit.SECONDS)
                .build(
                        new CacheLoader<String, String>() {
                            @Override
                            public String load(String key) throws Exception {
                                return null;
                            }

                        });
    }


    public void generateSendOTP(String phoneNumber) throws OtpException {
        String numbers = "0123456789";
        int len = appConfig.getOtp_len();
        Random rndm_method = new Random();

        char[] otp = new char[len];

        for (int i = 0; i < len; i++) {
            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }

        String res = new String(otp);

        otpCache.put(phoneNumber, res);

        log.info("Generated opt for " + phoneNumber + " is " + res);

        if ("twilio".equalsIgnoreCase(appConfig.getOpt_provider_primary()))
            otpSenderTwilio(phoneNumber, res);
        else {
            otpSenderExotel(phoneNumber, res);
        }

    }


    public String getOtp(String key) {
        try {
            return otpCache.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }

    private void otpSenderTwilio(String PhoneNumber, String otp) throws OtpException {
        try {
            String PhoneNum = PhoneNumber;

            if (!PhoneNumber.startsWith("+91")) {
                PhoneNum = "+91" + PhoneNum;
            }
            Twilio.init(appConfig.getTwilio_account_sid(), appConfig.getTwilio_auth_token());
            Message response = Message.creator(
                            new com.twilio.type.PhoneNumber(PhoneNum),
                            new com.twilio.type.PhoneNumber(appConfig.getTwilio_register_phone_number()),
                            "Hi, Your login OTP for CuberReality app is  " + otp + " . Happy Reselling.."
                    )
                    .create();
            log.info(response.getBody());
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new OtpException("This mobile number is not valid " + PhoneNumber);
        }
    }


    private void otpSenderExotel(String PhoneNumber, String otp) throws OtpException {

        String userName = appConfig.getExotel_account_user();
        String userToken = appConfig.getExotel_auth_token();
        String accountSid = appConfig.getExotel_account_sid();
        String from = appConfig.getExotel_register_phone_number();
        String msg = "Hi, Your login OTP for CuberReality app is "+otp+" . Happy Reselling..";

        try {
            HttpClient client = getClient();

            HttpPost post = new HttpPost("https://" + userName + ":" + userToken + "@api.exotel.com/v1/Accounts/"
                    + accountSid + "/Sms/send");

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

            nameValuePairs.add(new BasicNameValuePair("From", from));
            nameValuePairs.add(new BasicNameValuePair("To", PhoneNumber));
            nameValuePairs.add(new BasicNameValuePair("Body", msg));
            nameValuePairs.add(new BasicNameValuePair("DltEntityId", "1201164369761561456"));
            nameValuePairs.add(new BasicNameValuePair("DltTemplateId", "1207164485513787177"));
            nameValuePairs.add(new BasicNameValuePair("Priority", "high"));

            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(post, responseHandler);
            log.info(response);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            log.error(ex.toString());
            throw new OtpException("This mobile number is not valid " + PhoneNumber);
        }

    }

    private HttpClient getClient() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("SSL");

        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                System.out.println("getAcceptedIssuers =============");
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                System.out.println("checkClientTrusted =============");
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                System.out.println("checkServerTrusted =============");
            }
        }}, new SecureRandom());

        return HttpClients.custom().setSSLContext(sslContext).build();
    }

}
