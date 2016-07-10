package com.imaginaryebay.Controller;

/**
 * Created by Ben_Big on 7/1/16.
 */

import org.springframework.web.servlet.ModelAndView;


public class FrontPageControllerImpl implements FrontPageController {

    @Override
    public ModelAndView getFirstPage(){
        ModelAndView model= new ModelAndView("index");
        return model;
    }

}