package com.revature.messenger;

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

@Component
public class Messenger implements InitializingBean {

	@Value("${MESSAGING_ACCESS_KEY}")
	private String accessKey;
	
	@Value("${MESSAGING_SECRET_ACCESS_KEY}")
	private String secretAccessKey;
	
	@Value("${MESSAGING_REGION}")
	private String region;
	
	@Value("${MESSAGING_TOPIC_ARN}")
	private String topicArn;

	@Autowired
	ObjectMapper objectMapper;
	
	private BasicAWSCredentials credentials;
	
	private AmazonSNS snsClient;
	
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
		snsClient = AmazonSNSClient
					.builder()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(region)
					.build();
		System.out.println(topicArn);
	}
	
	public void sendDeleteBooksMessage(int authorId) {
		Message message = new Message(authorId);
		String json = null;
		try {
			json = objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		PublishRequest request = new PublishRequest(topicArn, json);
		PublishResult result = snsClient.publish(request);
	}
	
}

class Message {
	int authorId;

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public Message(int authorId) {
		this.authorId = authorId;
	}

	public Message() {
	}	
	
}
