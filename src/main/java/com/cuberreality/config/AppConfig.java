package com.cuberreality.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("constants")
@Data
public class AppConfig {
    private Long access_token_validity_seconds;
    private String signing_key;
    private String token_prefix;
    private String header_string;
    private String authorities_key;
    private Integer otp_len;
    private Integer otp_expired_sec;
    private String twilio_account_sid;
    private String opt_provider_primary;
    private String twilio_auth_token;
    private String twilio_register_phone_number;
	private String exotel_account_sid;
	private String exotel_account_user;
	private String exotel_auth_token;
	private String exotel_register_phone_number;
    private String reseller_notification_server_key;
    private Integer soft_app_update_version;
    private Integer hard_app_update_version;
    private String crm_refresh_token;
    private String crm_client_id;
    private String crm_client_secret;
    private String crm_base_url;
    private String crm_auth_base_url;
    private String bearer_token;

}
