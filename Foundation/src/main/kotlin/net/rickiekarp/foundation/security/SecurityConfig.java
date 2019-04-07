package net.rickiekarp.foundation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BasicAuthEncryPoint authenticationEntryPoint;

//    @Autowired
//    private AuthenticationTokenProcessingFilter processingFilter;


    public static final String[] NOT_SECURED = {"/account/authorize"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(NOT_SECURED);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(NOT_SECURED).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(getBasicAuthEntryPoint())
                .and()
                .csrf().disable();


    }

    @Bean
    public BasicAuthEncryPoint getBasicAuthEntryPoint(){
        return new BasicAuthEncryPoint();
    }

}