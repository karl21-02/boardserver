package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.config.AWSConfig;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
public class SnsService {
    AWSConfig awsConfig;

    public SnsService(AWSConfig awsConfig) {
        this.awsConfig = awsConfig;
    }

    /**
     * AWS로부터 값을 받아서 사용할 수 있는 함수
     */
    public AwsCredentialsProvider getAWSCredential(String accessKeyId, String secretAccessKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return () -> awsBasicCredentials;
    }

    /**
     * 실제로 sns와 통신하는 클라이언트 함수
     * @return
     */
    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(
                        getAWSCredential(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey())
                ).region(Region.of(awsConfig.getAwsRegion()))
                .build();
    }
}
