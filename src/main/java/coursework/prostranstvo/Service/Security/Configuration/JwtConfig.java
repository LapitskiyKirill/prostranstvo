package coursework.prostranstvo.Service.Security.Configuration;

import coursework.prostranstvo.Service.Security.AuthManager;
import coursework.prostranstvo.Service.Security.JwtFilter;
import coursework.prostranstvo.Service.Security.TokenProvider;
import coursework.prostranstvo.Service.UserDetailsServiceImpl;
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
