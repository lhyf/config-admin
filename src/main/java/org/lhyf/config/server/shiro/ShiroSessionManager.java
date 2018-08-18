package org.lhyf.config.server.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/****
 * @author YF
 * @date 2018-07-11 10:31
 * @desc ShiroSessionManager
 *
 **/
@Component
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionManager.class);

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        Serializable sessionId = getSessionId(sessionKey);
        ServletRequest request = null;
        if (sessionKey instanceof WebSessionKey) {
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        if (request != null && sessionId != null) {
            Session session = (Session) request.getAttribute(sessionId.toString());
            if(session != null){
                return session;
            }
        }
        Session session = super.retrieveSession(sessionKey);
        if (request != null && sessionId != null) {
            request.setAttribute(sessionId.toString(), session);
        }

        return session;
    }
}
