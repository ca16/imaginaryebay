package com.imaginaryebay.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imaginaryebay.Models.ContactFormEmail;

@RestController
@RequestMapping("/contact")
public interface ContactController {
	@RequestMapping(method= RequestMethod.POST)
	ResponseEntity<ContactFormEmail> sendEmail(@RequestBody ContactFormEmail contactEmail);
}
