package com.example.calendar_backendv2.services.CrudTests;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class S3Test {

    // Define your AWS credentials and bucket information
    private static final String accessKey = "";
    private static final String secretKey = "";
    private static final String bucketName = "bucketprojpaj";
    private static final String keyName = "test.txt";
    private static final String filePath = "D:\\Facultate master\\downloaded-example.txt"; // Destination file path

    public static void main(String[] args) {
        // Create AWS credentials
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

        // Create an S3 client with correct region configuration
        S3Client s3 = S3Client.builder()
                .region(Region.EU_NORTH_1)  // Specify the correct region where your S3 bucket is located
                .credentialsProvider(credentialsProvider)
                .build();

        // Upload a file to S3 for testing
        uploadFile(s3);

        // Test function to retrieve an object from S3
        //testRetrieve(s3,"test.txt,a.txt,b.txt,c.txt","D:\\Facultate master");
        //deleteFile(s3);
    }

    private static void uploadFile(S3Client s3) {
        String filePath = "D:\\Facultate master\\test.txt"; // File to upload

        // Specify the file to upload and the S3 key (object key)
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        try {
            // Upload the file to the S3 bucket
            s3.putObject(putObjectRequest, Paths.get(filePath));
            System.out.println("File uploaded successfully to S3 bucket: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("Error uploading file to S3: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testRetrieve(S3Client s3, String keyNames,String baseFilePath) {
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
                String filePath = baseFilePath + "\\download_"+keyName;
                FileOutputStream outputStream = new FileOutputStream(filePath);
                outputStream.write(objectBytes);
                outputStream.close();
                System.out.println("Object downloaded successfully to: " + filePath);

            } catch (S3Exception | IOException e) {
                System.err.println("Error downloading object from S3: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void deleteFile(S3Client s3){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        try {
            // Delete the file from the S3 bucket
            s3.deleteObject(deleteObjectRequest);
            System.out.println("File deleted successfully from S3 bucket: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("Error deleting file from S3: " + e.getMessage());
            e.printStackTrace();
        }
    }
}