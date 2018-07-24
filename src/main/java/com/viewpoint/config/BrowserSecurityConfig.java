package com.viewpoint.config;


import com.viewpoint.properties.SecurityConstants;
import com.viewpoint.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


/**
 * security 配置
 */
@EnableWebSecurity
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);
        http
                .authorizeRequests()
                    .antMatchers(
                        SecurityConstants.DEFAULT_UNAUTHENTICATION_URL
                        ,securityProperties.getBrowser().getLoginPage()
                        , "/layui/**","/js/**","/css/**","/images/*","/fonts/**","/**/*.png","/**/*.jpg")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                .csrf().disable();
    }
}