package com.wildbeeslabs.rest.configuration;

import com.wildbeeslabs.rest.handler.AccessDeniedServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ImportResource({ "classpath:spring-security-config.xml" })
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedServiceHandler accessDeniedHandler;

    public void configureGlobalSecurity(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user123").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("dba").password("dba123").roles("ADMIN", "DBA");
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/").access("hasRole('USER') or hasRole('ADMIN') or hasRole('DBA')")
                .and().httpBasic() //.and().formLogin().loginPage("/login")
                //.usernameParameter("ssid").passwordParameter("password")
                //.and().exceptionHandling().accessDeniedPage("/prohibited");
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and().headers().cacheControl().disable();
    }
}
