package com.wildbeeslabs.rest.configuration;

//import com.wildbeeslabs.rest.handler.CustomAccessDeniedHandler;
//import org.springframework.beans.factory.annotation.Autowired;
import com.wildbeeslabs.rest.handler.CustomAccessDeniedHandler;
import com.wildbeeslabs.rest.handler.CustomAuthenticationSuccessHandler;
import com.wildbeeslabs.rest.security.CustomAuthenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 *
 * Web Security Configuration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ImportResource({ "classpath:spring-security.config.xml" })
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("user123").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("admin123").roles("ADMIN").build());
        manager.createUser(User.withUsername("dba").password("dba123").roles("ADMIN", "DBA").build());
        manager.createUser(User.withUsername("epadmin").password("epadmin123").roles("EPADMIN").build());
        return manager;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("user123").roles("USER")
                .and()
                .withUser("admin").password("admin123").roles("ADMIN")
                .and()
                .withUser("dba").password("dba123").roles("ADMIN", "DBA")
                .and()
                .withUser("epadmin").password("epadmin123").roles("EPADMIN");
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic().realmName("REST API")
                //                .exceptionHandling()
                //                .accessDeniedHandler(accessDeniedHandler)
                //                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN", "DBA")
                .antMatchers("/*").permitAll()
                //.anyRequest().authenticated()
                //.usernameParameter("ssid").passwordParameter("password")
                //.and().exceptionHandling().accessDeniedPage("/denied")
                //                .antMatchers("/api/**").authenticated()
                //                .and().formLogin().loginPage("/api/login")
                //                .successHandler(authenticationSuccessHandler)
                //                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and().headers().cacheControl().disable()
                .and().logout();
    }

    @Bean
    public CustomAuthenticationSuccessHandler mySuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}
