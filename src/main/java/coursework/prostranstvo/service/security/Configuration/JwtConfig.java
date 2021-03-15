package coursework.prostranstvo.service.security.Configuration;

import coursework.prostranstvo.service.security.AuthManager;
import coursework.prostranstvo.service.security.JwtFilter;
import coursework.prostranstvo.service.security.TokenProvider;
import coursework.prostranstvo.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AllArgsConstructor
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;
    private final AuthManager authManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter filter = new JwtFilter();
        filter.setTokenProvider(tokenProvider);
        authManager.setUserDetailsService(userDetailsService);
        filter.setAuthenticationManager(authManager);
        http.addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
