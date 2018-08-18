package org.lhyf.config.server.shiro;

import org.lhyf.config.server.constant.WebConstant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/****
 * @author YF
 * @date 2018-07-11 11:12
 * @desc UrlPermissionsFilter
 *
 **/
@Component
public class UrlPermissionsFilter extends PermissionsAuthorizationFilter {
//    @Autowired
//    private PrivilegeService privilegeService;

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String curUrl = getRequestUrl(request);
        Subject subject = SecurityUtils.getSubject();
        String[] perms = (String[]) mappedValue;
        if (perms != null && perms.length > 0) {
            String p = perms[0];
            if (!StringUtils.isEmpty(p)) {
                if (p.equals("access")) {
                    return true;
                } else if (p.equals("user")) {
                    return subject.isAuthenticated();
                }
            }
        }
        String username = (String) subject.getSession().getAttribute(WebConstant.SESSION_USERNAME);
        if (!subject.isAuthenticated() || StringUtils.isEmpty(username)) {
            return false;
        }
       return true;
//        return privilegeService.checkUserPrivilege(username,curUrl);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (!subject.isAuthenticated()) {
            saveRequestAndRedirectToLogin(request, response);
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

    /**
     * 获取当前URL+Parameter
     * @param request	拦截请求request
     * @return			返回完整URL
     */

    private String getRequestUrl(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest)request;
        return req.getRequestURI();
    }
}
