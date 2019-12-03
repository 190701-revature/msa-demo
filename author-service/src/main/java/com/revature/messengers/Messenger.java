package com.revature.messengers;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Author;

@Component
public class Messenger implements InitializingBean {
	
	// Injecting environment variable data into strings
	@Value("${MESSAGING_ACCESS_KEY}")
	private String accessKey;
	
	@Value("${MESSAGING_SECRET_ACCESS_KEY}")
	private String secretKey;
	
	@Value("${MESSAGING_REGION}")
	private String region;
	
	@Value("${MESSAGING_TOPIC_ARN}")
	private String topicArn;
	
	// ObjectMapper can map between Java/JSON
	@Autowired
	ObjectMapper objectMapper;
	
	Logger logger = Logger.getLogger(Messenger.class);
	
	// Define common object we will use - can't initialize them here, because we don't
	// know if the environment strings will be injected yet
	private BasicAWSCredentials credentials;
	private AmazonSNS snsClient;
	
	// afterPropertiesSet is the appropriate place to initialize them
	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretKey);
		snsClient = AmazonSNSClient.builder()
						.withCredentials(new AWSStaticCredentialsProvider(credentials))
						.withRegion(region)
						.build();
	}
	
	public void sendDeleteBooksMessage(Author author) {
		// Creating JSON data
		String json = null;
		try {
			json = objectMapper.writeValueAsString(author);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// Sending the message
		PublishRequest request = new PublishRequest(topicArn, json);
		PublishResult result = snsClient.publish(request);
		logger.info("Sending message: " + result.getMessageId());
	}
	
}
