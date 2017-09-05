package com.wildbeeslabs.rest.configuration;

//import com.wildbeeslabs.rest.handler.CustomAccessDeniedHandler;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAutoConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ImportResource({ "classpath:spring-security.config.xml" })
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomAccessDeniedHandler accessDeniedHandler;
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("user123").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("admin123").roles("ADMIN").build());
        manager.createUser(User.withUsername("dba").password("dba123").roles("ADMIN", "DBA").build());
        return manager;
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().realmName("REST API")
                .and().authorizeRequests()
                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN", "DBA")
                .antMatchers("/**").permitAll()
                //.and().formLogin().loginPage("/login")
                //.usernameParameter("ssid").passwordParameter("password")
                //.and().exceptionHandling().accessDeniedPage("/denied")
                //.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and().headers().cacheControl().disable()
                .and().logout();
    }
}
