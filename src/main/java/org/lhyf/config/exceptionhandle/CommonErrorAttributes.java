package org.lhyf.config.exceptionhandle;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CommonErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("payload","");
        errorAttributes.put("success",false);
        errorAttributes.put("msg",webRequest.getAttribute("msg",RequestAttributes.SCOPE_REQUEST));
        errorAttributes.put("code",webRequest.getAttribute("code", RequestAttributes.SCOPE_REQUEST));
        errorAttributes.put("timestamp",new Date());
        return errorAttributes;
    }
}
