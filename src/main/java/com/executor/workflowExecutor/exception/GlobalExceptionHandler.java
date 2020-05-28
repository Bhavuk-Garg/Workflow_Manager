package com.executor.workflowExecutor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(Exception.class)
    public ModelAndView integrityConflict(HttpServletRequest req, Exception ex){
        ModelAndView mav=new ModelAndView();
        mav.addObject("msg", ex.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exceptionView");
        return mav;
    }
}
