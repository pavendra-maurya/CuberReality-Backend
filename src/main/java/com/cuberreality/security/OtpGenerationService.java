package com.cuberreality.security;

import com.cuberreality.error.OtpException;

public interface OtpGenerationService {
	
		public void generateSendOTP(String username) throws OtpException;

		public String getOtp(String key);
			
		public void clearOTP(String key);

			
}
