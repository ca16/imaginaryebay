package com.imaginaryebay.Models;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.model.UnsupportedMediaTypeException;
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
     */
    private static final String         IMAGE_JPEG      = "image/jpeg";
    private static final String         IMAGE_PNG       = "image/png";
    private static final String         FILENAME_HEADER = "filename";
    private static final String         BUCKET          = "odbneu";
    private static final String         INVALID_TYPE    = "Invalid file type. Files must be either .png or .jpg format.";
    private static final AWSCredentials AWS_CREDENTIALS = new BasicAWSCredentials(System.getenv("AWS_PUBLIC"),
                                                                                  System.getenv("AWS_SECRET"));

    /**
     * fileUploader - Uploads a MultipartFile object to S3.
     * @param multipartFile- A MultipartFile to be uploaded to S3. The URL of this file will be stored.
     * @return String result - The URL to access the uploaded MultiPartFile
     * @throws Exception
     */
    public String fileUploader(MultipartFile multipartFile) throws Exception{
        String keyName = "image-" + UUID.randomUUID();
        AmazonS3 s3 = new AmazonS3Client(AWS_CREDENTIALS);
        String result = null;
        try(S3Object s3Object = new S3Object()) {

            String fileType = multipartFile.getContentType();
//            String filename = multipartFile.getName();
//            String extension = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
//            System.out.println(extension);
            if (!IMAGE_JPEG.equals(fileType) || !IMAGE_PNG.equals(fileType)){
//                    || extension.equals(".jpg") || extension.equals(".png")){
                throw new UnsupportedMediaTypeException(INVALID_TYPE);
            }

            /** Prepare object metadata */
            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(multipartFile.getContentType());
            omd.setContentLength(multipartFile.getSize());
            omd.setHeader(FILENAME_HEADER, multipartFile.getName());

            /** Get bytestream from HTTP request */
            ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());

            /** Upload the object to S3, and get back the URL (String) in result */
            s3Object.setObjectContent(bis);
            s3.putObject(new PutObjectRequest(BUCKET, keyName, bis, omd)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            result = s3.getUrl(BUCKET, keyName).toString();

        } catch(UnsupportedMediaTypeException umte){
            this.printErrorInfo(umte);
            throw new UnsupportedMediaTypeException(getErrorInfo(umte));

        }catch (AmazonServiceException ase) {
            this.printErrorInfo(ase);
            throw new IOException(getErrorInfo(ase));

        } catch (AmazonClientException ace) {
            this.printErrorInfo(ace);
            throw new IOException(getErrorInfo(ace));

        }  catch (Exception e) {
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