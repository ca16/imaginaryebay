package com.imaginaryebay;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Security.SecurityJavaConfig;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Chloe on 7/12/16.
 * Basic testing for logging in with good credentials
 * Assuming Userr table has a user tom@hotmail.com with password haha
 * and a Userr jackie@gmail.com with password dragon (both admin)
 * and a Userr butters@gmail.com with password Stotch (not admin)
 * To package without tests mvn package -Dmaven.test.skip=true
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityJavaConfig.class})
@WebAppConfiguration
public class LoginTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    //private FooController fc;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @org.junit.Before
    public void setUp() throws Exception {

        System.setOut(new PrintStream(outContent));
        //fc = new FooController();

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @org.junit.After
    public void tearDown() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
        System.setOut(null);

    }


    @org.junit.Test
    public void widerTesting() throws Exception{

        mvc.perform(post("/login")).andExpect(status().isUnauthorized());
        mvc.perform(get("http://localhost:8080/user")).andExpect(status().isUnauthorized());

        // Good credentials
        MvcResult result1 = mvc.perform(post("/login").param("username", "tom@hotmail.com").param("password", "haha")).andExpect(status().isOk()).andReturn();
        MvcResult result2 = mvc.perform(post("/login").param("username", "jackie@gmail.com").param("password", "dragon")).andExpect(status().isOk()).andReturn();
        // Not admin
        MvcResult result3 = mvc.perform(post("/login").param("username", "butters@gmail.com").param("password", "Stotch")).andExpect(status().isOk()).andReturn();

        // Wrong email
        MvcResult result4 = mvc.perform(post("/login").param("username", "tomas@hotmail.com").param("password", "haha")).andExpect(status().isUnauthorized()).andReturn();

        // Wrong password
        mvc.perform(post("/login").param("username", "jackie@gmail.com").param("password", "fire")).andExpect(status().isUnauthorized());



        //This is the part that isn't working yet..
        HttpSession session1 = result1.getRequest().getSession();
        HttpSession session2 = result2.getRequest().getSession();
        HttpSession session3 = result3.getRequest().getSession();
        HttpSession session4 = result4.getRequest().getSession();

        //RequestBuilder req  = MockMvcRequestBuilders.get("http://localhost:8080/api/foos").session(session);

        // Admins acccessing /user
        // These only work when @JsonIgnore is added to items part of user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)session1)).andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)session2)).andExpect(status().isOk());

        // Nonadmins accessing /user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)session3)).andExpect(status().isForbidden());
        // Totally unauthorized person accessing /user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)session4)).andExpect(status().isUnauthorized());

        // users accessing other users: Admins
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/2").session((MockHttpSession)session1)).andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/2").session((MockHttpSession)session2)).andExpect(status().isOk());

        // users accessing other users: Non-admins - I think this still returns Ok because access here is dealt with somewhere else (and for now it just returns null)
        // or maybe these tests should just go in the controller part
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/1").session((MockHttpSession)session3)).andExpect(status().isOk());
        // Totally unauthorized person accessing a user - unauthorized
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/1").session((MockHttpSession)session4)).andExpect(status().isUnauthorized());

        // new user
        String newUserJson = "{\"email\":\"new@gmail.com\", \"name\":\"New Person\", \"password\":\"goodPassword\", \"isAdmin\": \"false\"}";
        mvc.perform(post("http://localhost:8080/user/new").content(newUserJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // update user
        // admin
        String updateJackie = "{\"email\":\"jackie@gmail.com\", \"name\":\"Jackie Fires\", \"password\":\"dragon\", \"isAdmin\": \"true\"}";
        mvc.perform((put("http://localhost:8080/user/3").session((MockHttpSession) session2)).content(updateJackie).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        //update user
        // not authorized
        mvc.perform((put("http://localhost:8080/user/3").session((MockHttpSession) session4)).content(updateJackie).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());


        //Cookie[] cookie = result1.getResponse().getCookies();
        //System.out.println("Cookie is:" + cookie.toString());
        //Assert.assertNotEquals(cookie.length, 0);


        // Testing that endpoint requires authorization, so calling without it produces an error
        // Also testing that it produces the right error
        // requires deployment
        /*
        RestTemplate template = new RestTemplate();
        try {
            template.getForEntity("http://localhost:8080/user", String.class);
        } catch (HttpClientErrorException httpExc) {
                assertEquals(httpExc.getStatusCode(), HttpStatus.UNAUTHORIZED);
        }*/

    }




}