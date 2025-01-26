package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.config.AWSConfig;
import com.fastcampus.boardserver.service.SnsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@RestController
@Log4j2
public class SnsController {
    private final AWSConfig awsConfig;
    private final SnsService snsService;

    public SnsController(AWSConfig awsConfig, SnsService snsService) {
        this.awsConfig = awsConfig;
        this.snsService = snsService;
    }

    /**
     * 토픽을 만들기
     * @param topicName
     * @return
     */
    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        SnsClient snsClient = snsService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if(!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }
        log.info("topic name =" + createTopicResponse.topicArn());
        snsClient.close();
        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }

    /**
     * 구독하기
     * @param endpoint
     * @param topicArn
     * @return
     */
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam final String endpoint, @RequestParam final String topicArn) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();
        SnsClient snsClient = snsService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
        if(!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }
        log.info("topicARN to subscribe = " + subscribeResponse.subscriptionArn());
        snsClient.close();

        return new ResponseEntity<>("SUBSCRITING SUCCESS", HttpStatus.OK);
    }

    /**
     * 발행하기
     * @param topicArn
     * @param message
     * @return
     */
    @PostMapping("/publish")
    public String publish(@RequestParam final String topicArn, @RequestBody Map<String, String> message) {
        SnsClient snsClient = snsService.getSnsClient();
        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);
        log.info("message : " + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();
        return publishResponse.messageId();
    }

    private ResponseStatusException getResponseStatusException(SnsResponse snsResponse) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, snsResponse.sdkHttpResponse().statusText().get()
        );
    }
}
