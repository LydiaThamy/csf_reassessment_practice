package vttp.backend.configuration;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {
    @Value("${s3.access.key}")
    private String accessKey;

    @Value("${s3.secret.key}")
    private String secretKey;

    @Bean
    public S3Client createS3() {
        AwsBasicCredentials cred = AwsBasicCredentials.create(accessKey, secretKey);
        return S3Client.builder()
                .region(Region.of("sgp1"))
                .endpointOverride(URI.create("https://sgp1.digitaloceanspaces.com"))
                .credentialsProvider(
                    StaticCredentialsProvider.create(cred))
                .build();
    }

}
