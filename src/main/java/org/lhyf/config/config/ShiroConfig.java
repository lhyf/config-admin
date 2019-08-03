package org.lhyf.config.config;

import org.lhyf.config.redis.RedisSessionDao;
import org.lhyf.config.shiro.CustomRealm;
import org.lhyf.config.shiro.ShiroCacheManager;
import org.lhyf.config.shiro.ShiroSessionManager;
import org.lhyf.config.shiro.UrlPermissionsFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/****
 * @author YF
 * @date 2018-07-10 18:49
 * @desc ShiroConfig
 *
 **/
@Configuration
public class ShiroConfig {

    private static final int SESSION_TIMEOUT = 1000 * 60 * 10;

    @Bean
    public SimpleCookie cookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("rememberme");
        cookie.setMaxAge(200);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(cookie());
        return manager;
    }

    @Bean
    public ShiroCacheManager cacheManager(){
        ShiroCacheManager cacheManager = new ShiroCacheManager();
        return cacheManager;
    }

    @Bean
//    @DependsOn("sessionDao")
    public ShiroSessionManager sessionManager(){
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(sessionDao());
        sessionManager.setGlobalSessionTimeout(SESSION_TIMEOUT);
        return sessionManager;
    }

    @Bean
//    @DependsOn(value = {"redisTemplate"})
    public RedisSessionDao sessionDao(){
        return new RedisSessionDao();
    }

    @Bean
    public HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(100);
        return matcher;
    }

    @Bean
    public CustomRealm realm(){
        CustomRealm realm = new CustomRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public UrlPermissionsFilter permissionsFilter(){
        UrlPermissionsFilter filter = new UrlPermissionsFilter();
        return filter;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(cacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setLoginUrl("/login");
        factoryBean.setUnauthorizedUrl("/403");
        factoryBean.setSecurityManager(securityManager());
        Map<String, Filter>filters = new LinkedHashMap();

        filters.put("perms", permissionsFilter());
        filters.put("anon", new AnonymousFilter());
        factoryBean.setFilters(filters);

        Map<String, String> chains = new LinkedHashMap<>();
        chains.put("/403", "perms[access]");
        chains.put("/", "perms[user]");
        chains.put("/home", "perms[user]");
        chains.put("/index", "perms[user]");
        chains.put("/adminInfo/**", "perms[user]");
        chains.put("/editor/**", "perms[user]");
        chains.put("/favicon.ico", "perms[access]");
        chains.put("/login", "perms[access]");
        chains.put("/logout", "logout,perms[access]");
        chains.put("/unauth/**", "perms[access]");

        chains.put("/api/config/**", "perms[access]");
        chains.put("/js/**", "perms[access]");
        chains.put("/images/**", "perms[access]");
        chains.put("/css/**", "perms[access]");
        chains.put("/fonts/**", "perms[access]");

//        chains.put("/error/**", "anon");

        chains.put("/defaultKaptcha", "perms[access]");
        chains.put("/**", "perms");

        factoryBean.setFilterChainDefinitionMap(chains);
        return factoryBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
    }
}
