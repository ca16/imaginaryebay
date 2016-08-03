package com.imaginaryebay.Controller;

/**
 * Created by Ben_Big on 7/1/16.
 */

import org.springframework.web.servlet.ModelAndView;


public class FrontPageControllerImpl implements FrontPageController {

    @Override
    public ModelAndView getFirstPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }


    @Override
    public ModelAndView getLoginPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getShopPage(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }

    @Override
    public ModelAndView getSearchResult(){
        ModelAndView model= new ModelAndView("/WEB-INF/index.jsp");
        return model;
    }
}
