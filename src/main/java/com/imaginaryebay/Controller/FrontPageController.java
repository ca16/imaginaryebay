package com.imaginaryebay.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ben_Big on 7/1/16.
 */

@Controller
@RequestMapping(value="/")

public interface FrontPageController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getFirstPage();


    @RequestMapping(value="/app/login" , method=RequestMethod.GET)
    public ModelAndView getLoginPage();

    @RequestMapping(value="/app/shop/{id}", method=RequestMethod.GET)
    public ModelAndView getShopPage();

    @RequestMapping(value = "/app/search", method=RequestMethod.GET)
    public ModelAndView getSearchResult();

    @RequestMapping(value = "/app/admin", method=RequestMethod.GET)
    public ModelAndView getAdminPage();


}