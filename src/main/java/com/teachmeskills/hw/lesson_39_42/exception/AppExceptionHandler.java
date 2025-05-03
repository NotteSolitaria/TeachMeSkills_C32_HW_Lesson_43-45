package com.teachmeskills.hw.lesson_39_42.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView allExceptionsHandler(Exception exception) {
        System.out.println("Caught a general exception:");
        System.out.println("Exception: " + exception.getClass().getName());
        System.out.println("Message: " + exception.getMessage());
        System.out.println("Cause: " + (exception.getCause() != null ? exception.getCause() : "No cause"));
        exception.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("innerError");
        modelAndView.addObject("message", exception.getMessage());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }
}
