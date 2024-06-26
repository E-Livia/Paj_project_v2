package com.example.calendar_backendv2.services.CrudTests;

import com.example.calendar_backendv2.models.ServiceResponse;
import com.example.calendar_backendv2.services.S3ServiceWithLambda;

public class LambdaTest {

    public static void main(String[] args) {
        testUploadS3();
        testRetrieveS3();
        testDeleteS3();
    }

    public static void testUploadS3() {
        String filePath = "D:\\Facultate master\\testePAJ\\test-upload.txt"; // Replace with your test file path
        String keyName = "test-upload.txt"; // Replace with your desired S3 key name
        ServiceResponse result = S3ServiceWithLambda.uploadS3(filePath, keyName);
        if (result.getCode()== 0) {
            System.out.println("File uploaded successfully and Lambda function invoked.");
        } else {
            System.out.println("File upload failed or Lambda invocation error.");
        }
    }

    public static void testDeleteS3() {
        String keyName = "test-upload.txt"; // Replace with the key name of the file to delete
        ServiceResponse result = S3ServiceWithLambda.deleteS3(keyName);
        if (result.getCode() == 0) {
            System.out.println("File deleted successfully and Lambda function invoked.");
        } else {
            System.out.println("File deletion failed or Lambda invocation error.");
        }
    }

    public static void testRetrieveS3() {
        String keyNames = "test-upload.txt,a.txt,b.txt"; // Replace with the key names of the files to retrieve (comma-separated if multiple)
        String baseFilePath = "D:\\Facultate master\\testePAJ"; // Replace with the base path where files should be saved
        ServiceResponse result = S3ServiceWithLambda.retrieveS3(keyNames, baseFilePath);
        if (result.getCode() == 0) {
            System.out.println("Files retrieved successfully and Lambda function invoked.");
        } else {
            System.out.println("File retrieval failed or Lambda invocation error.");
        }
    }

}