package com.example.bookingsite.Configuration;

import com.example.bookingsite.Service.Implementation.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomUsernameAndPasswordAuthProvider customUsernameAndPasswordAuthProvider;

    public WebSecurityConfiguration(CustomUsernameAndPasswordAuthProvider authProvider) {
        this.customUsernameAndPasswordAuthProvider = authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/home/*", "/place/{id}", "/register", "/assets/**").permitAll()
                .antMatchers("/person/settings","/person/reservations/**","/person/edit/reservation","/person/delete").hasAnyRole("USER","OWNER")
                .antMatchers("/person/**","/place/**").hasRole("OWNER")
                .antMatchers("/user/admin").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/home")
                .and()
                .oauth2Login()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=BadCredentials")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuthLoginSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customUsernameAndPasswordAuthProvider);
    }

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;

    @Autowired
    private OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
}
