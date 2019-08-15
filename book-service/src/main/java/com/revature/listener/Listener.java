package com.revature.listener;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.BookService;

@Component
public class Listener implements InitializingBean {
	@Value("${MESSAGING_ACCESS_KEY}")
	private String accessKey;

	@Value("${MESSAGING_SECRET_ACCESS_KEY}")
	private String secretAccessKey;

	@Value("${MESSAGING_REGION}")
	private String region;

	@Value("${MESSAGING_QUEUE_URL}")
	private String queueUrl;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BookService bookService;
	
	private BasicAWSCredentials credentials;

	private AmazonSQS sqsClient;

	@Override
	public void afterPropertiesSet() throws Exception {
		credentials = new BasicAWSCredentials(accessKey, secretAccessKey);
		sqsClient = AmazonSQSClient.builder().withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(region).build();
	}

	@Scheduled(fixedRate = 15000)
	public void scheduledPoll() {
		ReceiveMessageResult pollResult = poll();
		List<Message> messages = pollResult.getMessages();
		
		messages.forEach(message -> {
			String body = message.getBody();
			DeleteMessage deleteMessage = null;
		
			try {
				deleteMessage = objectMapper.readValue(body, DeleteMessage.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Received message to delete with id: " + deleteMessage.getAuthorId());
			
			bookService.deleteBooksByAuthorId(deleteMessage.getAuthorId());
			deleteMessage(message.getReceiptHandle());
		});
	}

	public ReceiveMessageResult poll() {
		ReceiveMessageRequest messageRequest = new ReceiveMessageRequest(queueUrl);
		messageRequest.setVisibilityTimeout(15);
		ReceiveMessageResult message = sqsClient.receiveMessage(messageRequest);
		return message;
	}

	public void deleteMessage(String receiptHandle) {
		DeleteMessageRequest deleteRequest = new DeleteMessageRequest(queueUrl, receiptHandle);
		sqsClient.deleteMessage(deleteRequest);
	}

}

class DeleteMessage {
	private int authorId;

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public DeleteMessage(int authorId) {
		super();
		this.authorId = authorId;
	}

	public DeleteMessage() {
		super();
	}

}