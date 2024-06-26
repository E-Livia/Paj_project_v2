package com.example.calendar_backendv2.services.CrudTests;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;

import java.nio.charset.StandardCharsets;

public class LambdaTest2 {

    // AWS credentials and region
    //test
    private static final String accessKey = "";
    private static final String secretKey = "";
    private static final String regionName = "eu-north-1"; // e.g., "us-east-1"

    // Lambda function details
    private static final String functionName = "euN1LambdaF"; // e.g., "MyLambdaFunction"

    public static void main(String[] args) {
        // Create AWS credentials
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        // Set up Lambda client
        LambdaClient lambdaClient = LambdaClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(regionName))
                .build();

        // Prepare request payload (if any)
        //SdkBytes payload = SdkBytes.fromUtf8String("{\"key\": \"value\"}");
        String payload2 = "{\"number\": " + "0" + "}";
        SdkBytes payload = SdkBytes.fromUtf8String(payload2);

        // Prepare invoke request
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(functionName)
                .payload(payload)
                .build();

        try {
            // Invoke Lambda function
            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
            String responsePayload = invokeResponse.payload().asString(StandardCharsets.UTF_8);

            // Print the response
            System.out.println("Lambda function response:");
            System.out.println(responsePayload);
        } catch (LambdaException e) {
            System.err.println("Error invoking Lambda function: " + e.getMessage());
            e.printStackTrace();
        }
    }
}