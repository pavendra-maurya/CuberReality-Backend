package com.cuberreality.security;

import com.cuberreality.config.AppConfig;
import com.cuberreality.error.OtpException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class OtpGenerationServiceImp implements OtpGenerationService{
	
		 @Autowired
		 private AppConfig appConfig;

	 	 private LoadingCache<String, String> otpCache;
	 	 private static final int 	OTP_EXPIRED_SEC = 300;
	 
		 private OtpGenerationServiceImp(){
			 	otpCache =  CacheBuilder.newBuilder()
			 			   .expireAfterWrite(OTP_EXPIRED_SEC,TimeUnit.SECONDS )
			    		   .build(
			    				   new CacheLoader<String, String>() {
									@Override
									public String load(String key) throws Exception {
										return null;
									}
								
			    			});
		}
	 

		public void generateSendOTP(String username) throws OtpException {
			    String numbers = "0123456789"; 
			    int len= appConfig.getOtp_len();
		        Random rndm_method = new Random(); 
		  
		        char[] otp = new char[len]; 
		  
		        for (int i = 0; i < len; i++) 
		        { 
		            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length())); 
		        } 
		        
		       String res =  new String(otp);
		       
		        otpCache.put(username, res);

				log.info("Generated opt for "+username +" is "+res);

		        otpSender(username, res);

		}


	   public String getOtp(String key){ 
				try{
					return otpCache.get(key); 
				}catch (Exception e){
					return null; 
			}
	   }
			
		public void clearOTP(String key){ 
				otpCache.invalidate(key);
		 }
		
		private  void otpSender(String PhoneNumber, String otp) throws OtpException {
		 	try{
				String PhoneNum = PhoneNumber;

				if(!PhoneNumber.startsWith("+91")) {
					PhoneNum="+91"+PhoneNum;
				}
				Twilio.init(appConfig.getAccount_sid(), appConfig.getAuth_token());
				Message.creator(
								new com.twilio.type.PhoneNumber(PhoneNum),
								new com.twilio.type.PhoneNumber(appConfig.getRegister_phone_number()),
								"Dear Customer, Your OTP "+ otp+" for Nearbyse Login"
						)
						.create();
			}
		 	catch (Exception ex ){
				log.error(ex.toString());
				throw new OtpException("This mobile number is not valid " + PhoneNumber);
			}
		}
			
}
