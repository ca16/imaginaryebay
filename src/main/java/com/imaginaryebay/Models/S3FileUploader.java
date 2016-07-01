package com.imaginaryebay.Models;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
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
    private static final String         BUCKET          = "odbneu";
    private static       String         keyName         = "Object-" + UUID.randomUUID();
    private static final String         HTTPS           = "https://";
    private static final String         REGION          = ".s3.us-west-2.com/";
    private static final AWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials("AKIAJSNMBTJ6HVQZ3CKQ",
                                                                   "HM8jw0ZSIZekX/b1Rcohu39Mfq1mlNWQ+o2Qk54N");


    /**
     * fileUploader - Uploads a chunked file to S3. This is for large files. (We probably won't use this)
     * @param List<fileData ></fileData>- A List of chunked file data from a file.
     * @return String result - A message detailing the results. Will contain error messages for bad uploads
     * @throws IOException
     */
    public String fileUploader(List<FileItem> fileData) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        String result = "Upload unsuccessfull because ";
        try {

            S3Object s3Object = new S3Object();

            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(fileData.get(0).getContentType());
            omd.setContentLength(fileData.get(0).getSize());
            omd.setHeader("filename", fileData.get(0).getName());

            ByteArrayInputStream bis = new ByteArrayInputStream(fileData.get(0).get());

            s3Object.setObjectContent(bis);
            s3.putObject(new PutObjectRequest(BUCKET, keyName, bis, omd));
            s3Object.close();

            // TODO: Test URL building (i.e. is the URL correct) then add URL storage
            String fileName = HTTPS + BUCKET + REGION + keyName;

            result = "Uploaded Successfully.";
        } catch (AmazonServiceException ase) {

            printErrorInfo(ase);
            result = result + ase.getMessage();

        } catch (AmazonClientException ace) {

            printErrorInfo(ace);
            result = result + ace.getMessage();

        }catch (Exception e) {
            result = result + e.getMessage();
        }

        return result;
    }


    /**
     * fileUploader - Uploads a MultipartFile object to S3.
     * @param MultipartFile- A MultipartFile to be uploaded to S3. The URL of this file will be stored.
     * @return String result - A message detailing the results. Will contain error messages for bad uploads
     * @throws IOException
     */
    public String fileUploader(MultipartFile multipartFile) throws IOException {
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        String result = null;
        try {

            S3Object s3Object = new S3Object();

            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(multipartFile.getContentType());
            omd.setContentLength(multipartFile.getSize());
            omd.setHeader("filename", multipartFile.getName());

            ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());

//            s3Object.setObjectContent(bis);
//            s3.putObject(new PutObjectRequest(BUCKET, keyName, bis, omd));
//            s3Object.close();

            System.out.println(multipartFile.getContentType());

            TransferManager transferManager = new TransferManager(s3);
            transferManager.upload(BUCKET, keyName, bis, omd);

            String extension = multipartFile.getContentType().split("/")[1];

            // TODO: Test URL building (i.e. is the URL correct) then add URL storage
            result = HTTPS + BUCKET + REGION + keyName + "." + extension;
            System.out.println(result);
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
        System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was "
                + "rejected with an error response for some reason.");

        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
        System.out.println(ase.getMessage());
    }

    private void printErrorInfo(AmazonClientException ace){
        System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while "
                + "trying to communicate with S3, such as not being able to access the network.");
        System.out.println(ace.getMessage());
    }
}