package com.imaginaryebay.Models;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Brian on 6/27/2016.
 *
 * S3FileUploader - Handles file uploads, configures upload settings for S3, and manages URL storage.
 */
public class S3FileUploader {


    /**
     * Define some constants for setting credentials, and building URLs to store in the DB
     * TODO: The Credentials need to go somewhere safer. Make a config file.
     */
    private static final String         FILENAME_HEADER = "filename";
    private static final String         BUCKET          = "odbneu";
    private static final String         keyName         = "Object-" + UUID.randomUUID();
    private static final AWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials("AKIAJSNMBTJ6HVQZ3CKQ",
                                                                   "HM8jw0ZSIZekX/b1Rcohu39Mfq1mlNWQ+o2Qk54N");

    /**
     * fileUploader - Uploads a MultipartFile object to S3.
     * @param multipartFile- A MultipartFile to be uploaded to S3. The URL of this file will be stored.
     * @return String result - The URL to access the uploaded MultiPartFile
     * @throws IOException
     */
    public String fileUploader(MultipartFile multipartFile) throws Exception {
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        S3Object s3Object = new S3Object();
        String result = null;
        try {

            /** Prepare object metadata */
            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(multipartFile.getContentType());
            omd.setContentLength(multipartFile.getSize());
            omd.setHeader(FILENAME_HEADER, multipartFile.getName());

            /** Get bytestream from HTTP request */
            ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());

            /** Upload the object to S3, and get back the URL in result */
            s3Object.setObjectContent(bis);
            s3.putObject(new PutObjectRequest(BUCKET, keyName, bis, omd)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
            result = s3.getUrl(BUCKET, keyName).toString();

            s3Object.close();

            System.out.println("File uploaded to: " + result);

        } catch (AmazonServiceException ase) {
            this.printErrorInfo(ase);
            throw new IOException(getErrorInfo(ase));

        } catch (AmazonClientException ace) {
            this.printErrorInfo(ace);
            throw new IOException(getErrorInfo(ace));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
        return result;
    }

    private void printErrorInfo(AmazonServiceException ase){
        System.out.println("AmazonServiceException! Your request made it to Amazon S3, but it was "
                + "rejected with an error response.");

        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
        System.out.println(ase.getMessage());
    }

    private String getErrorInfo(AmazonServiceException ase){
        String error_msg = "AmazonServiceException! Your request made it to Amazon S3," +
                " but it was rejected with an error response.";
        error_msg += "Error Message: "    + ase.getMessage();
        error_msg += "HTTP Status Code: " + ase.getStatusCode();
        error_msg += "AWS Error Code: "   + ase.getErrorCode();
        error_msg += "Error Type: "       + ase.getErrorType();
        error_msg += "Request ID: "       + ase.getRequestId();

        return error_msg;
    }

    private void printErrorInfo(AmazonClientException ace){
        System.out.println("AmazonClientException! The client encountered an internal error while "
                + "trying to communicate with S3. This could be due to network issues.");
        System.out.println(ace.getMessage());
    }

    private String getErrorInfo(AmazonClientException ace){
        System.out.println(ace.getMessage());
        return "AmazonClientException! The client encountered an internal error while "
                + "trying to communicate with S3. This could be due to network issues.";
    }
}