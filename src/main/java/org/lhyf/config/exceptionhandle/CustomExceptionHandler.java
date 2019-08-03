package org.lhyf.config.exceptionhandle;

import org.lhyf.config.model.Bo.RestResponseBo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/****
 * @author YF
 * @date 2018-07-13 19:16
 * @desc CustomExceptionHandler
 *
 **/
@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public String handlerException(Exception e, HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",500);
        request.setAttribute("code",500);
        request.setAttribute("msg",e.getMessage());
        logger.error("",e);
        return "forward:/error";
    }
}
