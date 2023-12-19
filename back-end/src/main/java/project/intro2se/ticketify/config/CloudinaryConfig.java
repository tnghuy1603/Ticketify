package project.intro2se.ticketify.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;
    @Bean
    public Cloudinary getCloudinary(){
//        Map config = new HashMap();
//        config.put("cloud_name", cloudName);
//        config.put("api_key", apiKey);
//        config.put("api_secret", apiSecret);
//        config.put("secure", true);
//        return new Cloudinary(config);
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djx9dg94f",
                "api_key", "534952553431135",
                "api_secret", "485Z3ZTB9Wm8MXRU0BtwmM9snmA"));
    }
}
