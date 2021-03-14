package coursework.prostranstvo.Service.Security;

import coursework.prostranstvo.Model.Entity.User;
import coursework.prostranstvo.Model.Exception.ApiException;
import coursework.prostranstvo.Service.UserDetailsServiceImpl;
import lombok.Setter;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Primary
@Setter
@Service
public class AuthManager implements AuthenticationManager {

    private UserDetailsServiceImpl userDetailsService;
    private TokenProvider tokenProvider;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        try {
            if(auth instanceof TokenAuth){
                return processAuthentication((TokenAuth) auth);
            } else {
                auth.setAuthenticated(false);
                return auth;
            }
        } catch (AuthenticationServiceException e){
            throw new ApiException(e.getMessage());
        }
    }

    private TokenAuth processAuthentication(TokenAuth authentication) throws AuthenticationException {
        String token = authentication.getToken();
        if(tokenProvider.validateToken(token)){
            return buildFullTokenAuthentication(authentication);
        } else {
            throw new ApiException("Bad token");
        }
    }

    private TokenAuth buildFullTokenAuthentication(TokenAuth authentication) {
        User user = (User) userDetailsService.loadUserByUsername(authentication.getName());
        if (user.isEnabled()) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            return new TokenAuth(authentication.getToken(), authorities, true, user);
        } else {
            throw new AuthenticationServiceException("User disabled");
        }
    }
}
