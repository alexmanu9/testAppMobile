package mx.test.example.app.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = {"mx.test.example.app.controllers"} )
public class ExceptionsHandler {

	@ExceptionHandler(Exception.class)
    public ModelAndView myError(Exception exception) {
    ModelAndView mav = new ModelAndView();
    mav.addObject("exception", exception);
    mav.setViewName("error");
    return mav;
}
	
}
