package com.example.calendar_backendv2.services;


import com.example.calendar_backendv2.models.ServiceResponse;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;



public class S3ServiceWithLambda {


    private static final String accessKey = "";
    private static final String secretKey = "";
    private static final String bucketName = "bucketprojpaj";


    public static S3Client initS3Client() {
        // Create AWS credentials
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        // Create an S3 client with correct region configuration
        S3Client s3 = S3Client.builder()
                .region(Region.EU_NORTH_1)  // Specify the correct region where your S3 bucket is located
                .credentialsProvider(credentialsProvider)
                .build();
        return s3;
    }

    public static ServiceResponse uploadS3(String filePath, String keyname) {
        int retVal = -1;
        S3Client s3 = initS3Client();
        retVal = uploadFile(s3, filePath, keyname);

        String lambdaResponse = invokeLambda("upload", retVal);
        //ServiceResponse srvRsp=new ServiceResponse(retVal,)

        return new ServiceResponse(retVal,lambdaResponse);
    }

    public static ServiceResponse deleteS3(String keyname) {
        int retVal = -1;
        S3Client s3 = initS3Client();
        retVal = deleteFile(s3, keyname);

        String lambdaResponse = invokeLambda("delete", retVal);

        return new ServiceResponse(retVal,lambdaResponse);
    }

    public static ServiceResponse retrieveS3(String keynames, String baseFilePath) {
        int retVal = -1;
        S3Client s3 = initS3Client();
        retVal = retrieveFiles(s3, keynames, baseFilePath);

        String lambdaResponse = invokeLambda("retrieve", retVal);

        return new ServiceResponse(retVal,lambdaResponse);
    }

    private static int uploadFile(S3Client s3, String filePath, String keyname) {
        // Specify the file to upload and the S3 key (object key)
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyname)
                .build();

        try {
            // Upload the file to the S3 bucket
            s3.putObject(putObjectRequest, Paths.get(filePath));
            System.out.println("File uploaded successfully to S3 bucket: " + bucketName);
            return 0;
        } catch (S3Exception e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    private static int deleteFile(S3Client s3, String keyname) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyname)
                .build();

        try {
            // Delete the file from the S3 bucket
            s3.deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully from S3 bucket: " + bucketName);
            return 0;
        } catch (S3Exception e) {
            System.err.println("Error deleting file from S3: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    private static int retrieveFiles(S3Client s3, String keyNames, String baseFilePath) {
        // Split the key names by commas
        String[] keys = keyNames.split(",");

        for (String keyName : keys) {
            // Trim whitespace
            keyName = keyName.trim();

            // Specify the object to retrieve
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            try {
                // Retrieve the object
                ResponseBytes<GetObjectResponse> objectResponse = s3.getObjectAsBytes(getObjectRequest);

                // Save the object content to a file
                byte[] objectBytes = objectResponse.asByteArray();
                String filePath = baseFilePath + "\\download_" + keyName;
                FileOutputStream outputStream = new FileOutputStream(filePath);
                outputStream.write(objectBytes);
                outputStream.close();
                System.out.println("Object downloaded successfully to: " + filePath);

            } catch (S3Exception | IOException e) {
                System.err.println("Error downloading object from S3: " + e.getMessage());
                e.printStackTrace();
                return 1;
            }
        }
        return 0;
    }

    private static String invokeLambda(String action, int key) {
        // Initialize Lambda client
        int retval=-1;
        String responsereturn="";
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        String regionName="eu-north-1";
        LambdaClient lambdaClient = LambdaClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(regionName))
                .build();

        String payload = "{\"number\": " + key + ", \"action\": \"" + action + "\"}";
        SdkBytes sdkBytes = SdkBytes.fromUtf8String(payload);

        // Invoke the Lambda function
        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName("euN1LambdaF")
                .payload(sdkBytes)
                .build();

        try {
            // Invoke Lambda function
            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
            String responsePayload = invokeResponse.payload().asString(StandardCharsets.UTF_8);

            // Print the response
            System.out.println("Lambda function response:");
            System.out.println(responsePayload);
            retval=0;
            responsereturn=responsePayload;
        } catch (LambdaException e) {
            System.err.println("Error invoking Lambda function: " + e.getMessage());
            e.printStackTrace();
            retval=1;
        }finally {
            lambdaClient.close();
            if(retval==0){return responsereturn;}
            else return "error";
        }


    }




}
