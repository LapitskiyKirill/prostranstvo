package coursework.prostranstvo.configuration;

import coursework.prostranstvo.model.enumerable.Role;
import coursework.prostranstvo.service.security.AuthManager;
import coursework.prostranstvo.service.security.Component.JwtAccessDeniedHandler;
import coursework.prostranstvo.service.security.Component.JwtAuthenticationEntryPoint;
import coursework.prostranstvo.service.security.Configuration.JwtConfig;
import coursework.prostranstvo.service.security.TokenProvider;
import coursework.prostranstvo.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenProvider tokenProvider;
    private final AuthManager authManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .logout()
                .logoutUrl("/api/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .cors()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationErrorHandler)
                    .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                    .authorizeRequests()
                            .antMatchers("/api/auth/autologin")
                            .fullyAuthenticated()
                .and()
                    .authorizeRequests()
                        .antMatchers("/api/auth/**")
                        .not().fullyAuthenticated()
                    .antMatchers("/api/admin/**")
                    .hasAuthority(Role.ROLE_ADMIN.getAuthority())
                    .antMatchers("/api/lot/**")
                    .hasAnyAuthority(
                            Role.ROLE_ADMIN.getAuthority(),
                            Role.ROLE_USER.getAuthority()
                    )
                .anyRequest().authenticated()
                .and()
                    .apply(securityConfigurerAdapter());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                );
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtConfig securityConfigurerAdapter() {
        return new JwtConfig(tokenProvider, authManager, userDetailsService);
    }

}
