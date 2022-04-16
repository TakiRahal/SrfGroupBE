package com.takirahal.srfgroup.config;


import com.takirahal.srfgroup.constants.AuthoritiesConstants;
import com.takirahal.srfgroup.security.CustomUserDetailsService;
import com.takirahal.srfgroup.security.JwtAuthenticationEntryPoint;
import com.takirahal.srfgroup.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // For user principal.id inside repository
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/user/public/**").permitAll()
                .antMatchers("/api/offer/public/**").permitAll()
                .antMatchers("/api/find-offer/public/**").permitAll()
                .antMatchers("/api/sell-offer/public/**").permitAll()
                .antMatchers("/api/rent-offer/public/**").permitAll()
                .antMatchers("/api/address/public/**").permitAll()
                .antMatchers("/api/category/public/**").permitAll()
                .antMatchers("/api/faq/public/**").permitAll()
                .antMatchers("/api/news-letter/public/**").permitAll()
                .antMatchers("/api/description-add-offers/public/**").permitAll()
                .antMatchers("/api/faq/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/aboutus/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/aboutus/public/**").permitAll()
                .antMatchers("/api/contactus/public/**").permitAll()
                .antMatchers("/api/post-home-feature/public/**").permitAll()
                .antMatchers("/api/top-home-slides-images/public/**").permitAll()
                .antMatchers("/api/contactus/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/post-home-feature/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/news-letter/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/top-home-slides-images/admin/**").hasAnyAuthority(AuthoritiesConstants.SUPER_ADMIN, AuthoritiesConstants.ADMIN)
                .antMatchers("/api/offer-images/public/**").permitAll()
                .antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
                .permitAll()
                .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // Add our custom JWT security filter
         http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}