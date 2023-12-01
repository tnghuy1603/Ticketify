package project.intro2se.ticketify.config;


import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class PaypalConfig {
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.client-secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;


    @Bean
    public PayPalHttpClient payPalHttpClient(){
        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }

}
