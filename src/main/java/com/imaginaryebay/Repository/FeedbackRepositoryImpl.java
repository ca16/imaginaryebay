package com.imaginaryebay.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.Models.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Brian on 8/1/2016.
 */
@Service
public class FeedbackRepositoryImpl implements FeedbackRepository{

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String DISQUS_PUBLIC_KEY = System.getenv("DISQUS_PUBLIC_KEY");
    private static final String FORUM = "guarded-journey-11719-herokuapp-com";
    private static final String LIST_POSTS_URL = "https://disqus.com/api/3.0/threads/listPosts.json?api_key=" + DISQUS_PUBLIC_KEY;
    private static final String AND_FORUM = "&forum=";
    private static final String AND_THREAD_LINK = "&thread:link=";
    private static final String LINK_STEM = "http://localhost:8080/app/item/";
    private static final String HTTP_GET = "GET";

    /**
     * For a given Item, requests Feedback thread from the Disqus API
     * A Feedback thread is identified by the URL, which is unique in its Item ID
     * @param itemId The ID for the item whose Feedback is requested
     * @return Feedback for the given Item
     */
    @Override
    public Feedback getFeedbackForItem(Long itemId) {
        // Build the URL for the list of posts
        final String URL = LIST_POSTS_URL + AND_THREAD_LINK + LINK_STEM + itemId + AND_FORUM + FORUM;
        Feedback feedbackResponse = null;
        try {
            // Create HTTP GET connection for URL
            URL obj = new URL(URL);
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

                // Map JSON-formatted response to Feedback Object
                feedbackResponse = mapJSON(response.toString());

                if (feedbackResponse == null){
                    // Disqus returned success code, but no data
                    throw new RestException("Not available.",
                            "There is no feedback available.", HttpStatus.OK);
                }
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
        return feedbackResponse;
    }

    /**
     *
     * @param json A JSON-formatted String representing an item's Feedback
     * @return Feedback for a given auction item.
     */
    private static Feedback mapJSON(String json){
        ObjectMapper mapper = new ObjectMapper();
        Feedback feedback = null;
        try {
            // Convert input JSON-formatted String to Feedback Object
            feedback = mapper.readValue(json, Feedback.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedback;
    }
}
