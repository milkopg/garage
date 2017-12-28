package com.development.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

    public static final String DEFAULT_ERROR_VIEW = "general_error";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, NullPointerException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        return new ModelAndView(DEFAULT_ERROR_VIEW);
    }
}