package org.lab.tariff.calculator.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class RedirectController {

	@RequestMapping("/")
	public ModelAndView swagger() {
		RedirectView redirectView = new RedirectView("swagger-ui.html");
		redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
		return new ModelAndView(redirectView);
	}

}