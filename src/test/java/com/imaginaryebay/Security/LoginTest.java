package com.imaginaryebay.Security;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginaryebay.Configuration.DatabaseConfiguration;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
 * Assuming Userr table does not have users with emails: tom@hotmail.com,
 * jackie@gmail.com, butters@gmail.com and new@gmail.com
 * To package without tests mvn package -Dmaven.test.skip=true
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityJavaConfig.class})
@WebAppConfiguration
public class LoginTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserrDao dao;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @org.junit.Before
    public void setUp() throws Exception {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

    }

    @org.junit.After
    public void tearDown() throws Exception {

        SecurityContextHolder.getContext().setAuthentication(null);
    }


    @org.junit.Test
    @Transactional
    public void widerTesting() throws Exception{

        mvc.perform(post("/login")).andExpect(status().isUnauthorized());
        mvc.perform(get("http://localhost:8080/user")).andExpect(status().isUnauthorized());

        // admin
        Userr jackie = new Userr("jackie@gmail.com", "Jackie Fire", "dragon", true);
        Userr tom = new Userr("tom@hotmail.com", "Tom Doe", "haha", true);
        // non admin
        Userr butters = new Userr("butters@gmail.com", "Butters Stotch", "Stotch");

        // add to database
        dao.persist(jackie);
        dao.persist(tom);
        dao.persist(butters);
        Long jackieID = dao.getUserrByEmail("jackie@gmail.com").getId();
        Long buttersID = dao.getUserrByEmail("butters@gmail.com").getId();

        // Good credentials - logging in
        // admin
        MvcResult result1 = mvc.perform(post("/login").param("username", "tom@hotmail.com").param("password", "haha")).andExpect(status().isOk()).andReturn();
        MvcResult result2 = mvc.perform(post("/login").param("username", "jackie@gmail.com").param("password", "dragon")).andExpect(status().isOk()).andReturn();
        // Not admin
        MvcResult result3 = mvc.perform(post("/login").param("username", "butters@gmail.com").param("password", "Stotch")).andExpect(status().isOk()).andReturn();

        // Bad credentials - logging in
        // Wrong email
        MvcResult result4 = mvc.perform(post("/login").param("username", "tomas@hotmail.com").param("password", "haha")).andExpect(status().isUnauthorized()).andReturn();
        // Wrong password
        mvc.perform(post("/login").param("username", "jackie@gmail.com").param("password", "fire")).andExpect(status().isUnauthorized());


        HttpSession sessionTom = result1.getRequest().getSession();
        HttpSession sessionJackie = result2.getRequest().getSession();
        HttpSession sessionButters = result3.getRequest().getSession();
        HttpSession sessionRandom = result4.getRequest().getSession();

        // Admins acccessing /user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)sessionTom)).andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)sessionJackie)).andExpect(status().isOk());

        // Nonadmins accessing /user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)sessionButters)).andExpect(status().isForbidden());
        // Totally unauthorized person accessing /user
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user").session((MockHttpSession)sessionRandom)).andExpect(status().isUnauthorized());

        // users accessing other users: Admins
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession)sessionTom)).andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession)sessionJackie)).andExpect(status().isOk());

        // users accessing other users: Non-admins - forbidden
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/" + jackieID.toString()).session((MockHttpSession)sessionButters)).andExpect(status().isForbidden());
        // non-admin users accessing themselves - ok
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession)sessionButters)).andExpect(status().isOk());
        // Totally unauthorized person accessing a user - unauthorized
        mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession)sessionRandom)).andExpect(status().isUnauthorized());

        // adding a new user
        String newUserJson = "{\"email\":\"new@gmail.com\", \"name\":\"New Person\", \"password\":\"goodPassword\", \"isAdmin\": \"false\"}";
        mvc.perform(post("http://localhost:8080/user/new").content(newUserJson).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // ok updates
        // update self - admin
        String updateJackie = "{\"email\":\"jackie@gmail.com\", \"name\":\"Jackie Fires\", \"password\":\"dragon\", \"isAdmin\": \"true\"}";
        mvc.perform((put("http://localhost:8080/user/" + jackieID.toString()).session((MockHttpSession) sessionJackie)).content(updateJackie).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        // update self  - non-admin
        String updateButters = "{\"email\":\"butters@gmail.com\", \"name\":\"Butters Ice Cream\", \"password\":\"Stotch\", \"isAdmin\": \"false\"}";
        mvc.perform((put("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession) sessionButters)).content(updateButters).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        // update other user - admin
        updateButters = "{\"email\":\"butters@gmail.com\", \"name\":\"Butters Chocolate\", \"password\":\"Stotch\", \"isAdmin\": \"false\"}";
        mvc.perform((put("http://localhost:8080/user/" + buttersID.toString()).session((MockHttpSession) sessionJackie)).content(updateButters).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // not ok updates
        // update other user - not admin
        mvc.perform((put("http://localhost:8080/user/" + jackieID.toString()).session((MockHttpSession) sessionButters)).content(updateJackie).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
        // update from random non user
        mvc.perform((put("http://localhost:8080/user/" + jackieID.toString()).session((MockHttpSession) sessionRandom)).content(updateJackie).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());

        // users added to the database won't be there after the test is done

    }




}