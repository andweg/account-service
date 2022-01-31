package account.config;

import account.event.EventRegistrationService;
import account.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final EventRegistrationService eventRegistrationService;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(UserService userService,
                             EventRegistrationService eventRegistrationService,
                             RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                             LoginSuccessHandler loginSuccessHandler,
                             LoginFailureHandler loginFailureHandler) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.userService = userService;
        this.eventRegistrationService = eventRegistrationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.

                httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)

                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())

                .and()
                .csrf().disable().headers().frameOptions().disable()

                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/signup")
                    .permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/changepass")
                    .authenticated()
                .antMatchers(HttpMethod.GET, "/api/empl/payment")
                    .hasAnyRole("USER", "ACCOUNTANT")
                .antMatchers(HttpMethod.POST, "/api/acct/payments")
                    .hasRole("ACCOUNTANT")
                .antMatchers(HttpMethod.PUT, "/api/acct/payments")
                    .hasRole("ACCOUNTANT")
                .antMatchers(HttpMethod.GET, "/api/security/events/")
                    .hasRole("AUDITOR")
                .antMatchers("/api/admin/user/**")
                    .hasRole("ADMINISTRATOR")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            eventRegistrationService.registerAccessDenied(
                    request.getUserPrincipal().getName()
            );
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access Denied!"
            );
        };
    }

}