package com.imaginaryebay.Models;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * Created by Brian on 6/27/2016.
 *
 * S3FileUploader - Handles file uploads, configures upload settings for S3, and manages URL storage.
 *
 */
public class S3FileUploader {


    /**
     * Define some constants for setting credentials, and building URLs to store in the DB
     * TODO: The Credentials need to go somewhere safer. Make a config file.
     */
    private static final String         FILENAME_HEADER = "filename";
    private static final String         BUCKET          = "odbneu";
    private static       String         keyName         = "Object-" + UUID.randomUUID();
    private static final String         HTTPS           = "https://";
    private static final String         REGION          = ".s3.us-west-2.com/";
    private static final AWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials("AKIAJSNMBTJ6HVQZ3CKQ",
                                                                   "HM8jw0ZSIZekX/b1Rcohu39Mfq1mlNWQ+o2Qk54N");


    /**
     * fileUploader - Uploads a MultipartFile object to S3.
     * @param multipartFile- A MultipartFile to be uploaded to S3. The URL of this file will be stored.
     * @return String result - A message detailing the results. Will contain error messages for bad uploads
     * @throws IOException
     */
    public String fileUploader(MultipartFile multipartFile) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        String result = null;
        try {
            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(multipartFile.getContentType());
            omd.setContentLength(multipartFile.getSize());
            omd.setHeader(FILENAME_HEADER, multipartFile.getName());

            ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());

            TransferManager transferManager = new TransferManager(s3);
            transferManager.upload(BUCKET, keyName, bis, omd);

            String extension = multipartFile.getContentType().split("/")[1];
            // TODO: Test URL building (i.e. is the URL correct) then add URL storage
            result = HTTPS + BUCKET + REGION + keyName + "." + extension;
            System.out.println("File uploaded to: " + result);

        } catch (AmazonServiceException ase) {
            printErrorInfo(ase);
        } catch (AmazonClientException ace) {
            printErrorInfo(ace);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    private void printErrorInfo(AmazonServiceException ase){
        System.out.println("AmazonServiceException! Your request made it to Amazon S3, but was "
                + "rejected with an error response for some reason.");

        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
        System.out.println(ase.getMessage());
    }

    private void printErrorInfo(AmazonClientException ace){
        System.out.println("AmazonClientException! The client encountered an internal error while "
                + "trying to communicate with S3. This could be due to network issues.");
        System.out.println(ace.getMessage());
    }
}