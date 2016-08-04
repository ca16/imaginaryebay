package com.imaginaryebay.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Brian on 8/1/2016.
 */
@Service
public class CommentThreadRepositoryImpl implements CommentThreadRepository {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String DISQUS_PUBLIC_KEY = "5F6JARYvwDM8lfp5xCybGFDDo6K2Tl5PXJyEDekZzEcThbXZ2HUtEUWOTksgCbul";
    private static final String DISQUS_PRIVATE_KEY = "kGr5ZmIRAj4UT1DWSHw8ugHOdC1mUgynGGfCFKnxjaaVrLxZc6QfKOUVZ1BP0fLS";
//    private static final String DISQUS_PUBLIC_KEY = System.getenv("DISQUS_PUBLIC_KEY");
    private static final String FORUM = "guarded-journey-11719-herokuapp-com";
    private static final String LIST_POSTS_URL = "https://disqus.com/api/3.0/threads/listPosts.json?api_key=" + DISQUS_PUBLIC_KEY;
    private static final String THREAD_DETAILS_URL = "https://disqus.com/api/3.0/threads/details.json?api_key=" + DISQUS_PUBLIC_KEY;
    private static final String CREATE_POST_URL = "https://disqus.com/api/3.0/posts/create.json" ;
    private static final String endpart = "api_key=" + DISQUS_PUBLIC_KEY;
    private static final String AND_FORUM = "&forum=";
    private static final String AND_MESSAGE = "&message=";
    private static final String AND_THREAD_ID = "&thread=";
    private static final String AND_THREAD_LINK = "&thread:link=";
    private static final String AND_NAME = "&author_name=";
    private static final String LINK_STEM = "http://localhost:8080/app/item/";
    private static final String HTTP_GET = "GET";

    private static final String NOT_AVAILABLE       = "Not available.";
    private static final String NO_AUTHORITY        = "You do not have the authority to ";

    private UserrDao userrDao;
    public void setUserrDAO(UserrDao userr) {
        this.userrDao = userr;
    }

    /**
     * For a given Item, requests CommentThread thread from the Disqus API
     * A CommentThread thread is identified by the URL, which is unique in its Item ID
     * @param itemId The ID for the item whose CommentThread is requested
     * @return CommentThread for the given Item
     */
    @Override
    public CommentThread getFeedbackForItem(Long itemId) {
        // Build the URL for the list of posts
        final String URL = LIST_POSTS_URL + AND_THREAD_LINK + LINK_STEM + itemId + AND_FORUM + FORUM;

        // Map JSON-formatted String resposne to CommentThread
        CommentThread commentThreadResponse = mapFeedbackJSON(doGET(URL));
        // Disqus returned success code, but no data
        if (commentThreadResponse == null) {
            throw new RestException("Not available.", "There is no feedback available.", HttpStatus.OK);
        }
        return commentThreadResponse;
    }

    public static String getThreadIdForIdentifier(Long itemId) {
        // Build the URL for the list of posts
        final String URL = THREAD_DETAILS_URL + AND_THREAD_LINK + LINK_STEM + itemId + AND_FORUM + FORUM;
        ThreadIdResponse threadIdResponse = mapThreadIDResponseJSON(doGET(URL));
        // Disqus returned success code, but no data
        if (threadIdResponse == null){
            throw new RestException("Not available.", "There is no feedback available.", HttpStatus.OK);
        }
        return threadIdResponse.getResponse().getId();
    }
//    POST message=Hello+There&thread=1


    private static String doGET(String url) {
        // Build the URL for the list of posts
        String getResponse;

        try {
            // Create HTTP GET connection for URL
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(HTTP_GET);
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            // If success code is returned
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Consume HTTP response as JSON-formatted String
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                getResponse = response.toString();
            } else {
                // Disqus returned an error code
                throw new RestException(Integer.toString(responseCode),
                        "Received error status from remote feedback store. The requested feedback may not exist.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(IOException ioex){
            ioex.printStackTrace();
            throw new RestException("Internal server error!.",
                    "Could not contact external feedback store.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return getResponse;
    }


    private static void sendPOST(String url, String params){
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);

            // For POST only - START
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(params);
            os.flush();
            os.close();
            // For POST only - END

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
            } else {
                // Disqus returned an error code
                throw new RestException(Integer.toString(responseCode),
                        "Received error status from remote feedback store. The requested thread may not exist.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch(IOException ioex){
            ioex.printStackTrace();
            throw new RestException("Internal server error!.",
                    "Could not contact external feedback store.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Comment createFeedbackCommentForItemAndUser(Long itemId, Comment comment, Long userId){
        String requestUserName = "Brian";
        if (userId != null){
            Userr requestUser = userrDao.getUserrByID(userId);
            String requestUserEmail = requestUser.getEmail();
            requestUserName = requestUser.getName();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String authEmail = auth.getName();

            if (!(requestUserEmail.equals(authEmail) && requestUserName.equals(comment.getAuthor().getName()))){
                throw new RestException(NOT_AVAILABLE, NO_AUTHORITY + "update this user.", HttpStatus.FORBIDDEN);
            }
        }else{
            comment.getAuthor().setName("Anonymous");
            requestUserName = "Anonymous";
        }
        String threadId = getThreadIdForIdentifier(itemId);
        System.out.println(threadId);
        final String URL = CREATE_POST_URL;
        final String params = endpart + AND_THREAD_ID + threadId + AND_MESSAGE + comment.getRaw_message() + AND_NAME + requestUserName;
        sendPOST(URL, params);
        return comment;
    }

    /**
     *
     * @param json A JSON-formatted String representing an item's CommentThread
     * @return CommentThread for a given auction item.
     */
    //TODO: Switch to RestExceptions
    private static CommentThread mapFeedbackJSON(String json){
        ObjectMapper mapper = new ObjectMapper();
        CommentThread commentThread = null;
        try {
            // Convert input JSON-formatted String to CommentThread Object
            commentThread = mapper.readValue(json, CommentThread.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commentThread;
    }

    private static ThreadIdResponse mapThreadIDResponseJSON(String json){
        ObjectMapper mapper = new ObjectMapper();
        ThreadIdResponse threadIdResponse = null;
        try {
            // Convert input JSON-formatted String to CommentThread Object
            threadIdResponse = mapper.readValue(json, ThreadIdResponse.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return threadIdResponse;
    }

//    public static void main(String[] args){
//        Comment fc = new Comment();
//        CommentAuthor ca = new CommentAuthor();
//        ca.setName("Brian");
//        fc.setAuthor(ca);
//        fc.setRaw_message("New comment");
//        createFeedbackCommentForItemAndUser(1L, fc, null);
//        System.out.println(getThreadIdForIdentifier(1L));
//
//    }
}
