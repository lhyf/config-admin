package org.lhyf.config.server.exceptionhandle;

import org.lhyf.config.server.model.Bo.RestResponseBo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/****
 * @author YF
 * @date 2018-07-13 19:16
 * @desc CustomExceptionHandler
 *
 **/
@ControllerAdvice()
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RestResponseBo handlerException(Exception e){
        logger.error(ExceptionUtils.getStackTrace(e));
        return RestResponseBo.fail(e.getMessage());
    }
}
