package com.mysqlExample.kuroko.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
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
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView integrityConflict(HttpServletRequest req, Exception ex){
        ModelAndView mav=new ModelAndView();
        mav.addObject("msg", ex.getLocalizedMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exceptionView");
        return mav;
    }
//ex.getLocalizedMessage() can be used for overview of exception
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConstraintViolationException.class)
    public ModelAndView constraintConflict(HttpServletRequest req, Exception ex){
        ModelAndView mav=new ModelAndView();
        mav.addObject("msg", ex.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("exceptionView");
        return mav;
    }
}
