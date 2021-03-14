package coursework.prostranstvo.Service;

import coursework.prostranstvo.Model.DTO.*;
import coursework.prostranstvo.Model.Entity.User;
import coursework.prostranstvo.Model.Entity.VerificationToken;
import coursework.prostranstvo.Model.Enumerable.Role;
import coursework.prostranstvo.Model.Exception.ApiException;
import coursework.prostranstvo.Repository.UserRepository;
import coursework.prostranstvo.Repository.VerificationTokenRepository;
import coursework.prostranstvo.Service.Mail.MailService;
import coursework.prostranstvo.Service.Security.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    public AuthResponse authorizeUser(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = authRequest.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);

        return AuthResponse.builder()
                .username(authRequest.getUsername())
                .jwt(jwt)
                .build();
    }

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        userRepository.findByMail(registerRequest.getMail()).ifPresent(u -> {
            throw new ApiException("User with this mail already exists. MAIL: " + u.getMail());
        });
        userRepository.findByUsername(registerRequest.getUsername())
                .ifPresent(u -> {
                    throw new ApiException("Username " + registerRequest.getUsername() + " already exists");
                });

        User user = mapUserFromRequest(registerRequest);
        userRepository.save(user);
        sendActivationMail(user);
    }

    private void sendActivationMail(User user) {
        String token = generateVerificationToken(user);
        mailService.sendMail(new Mail(
                "PROSTRANSTVO.COM activation email",
                user.getMail(),
                "Thank you for signing up to marketplace, please click on the below url to activate " +
                        "your account : http://localhost:8080/api/auth/verify/" + token));
    }

    @Transactional
    protected String generateVerificationToken(User user) {
        String s = UUID.randomUUID().toString();
        VerificationToken token = new VerificationToken();
        token.setToken(s);
        token.setUser(user);

        verificationTokenRepository.save(token);

        return s;
    }

    @Transactional
    public UserDto userVerify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.getVerificationTokenByToken(token).orElseThrow(() ->
                new ApiException("Token not found"));
        User user = fetchUserAndActivate(verificationToken);
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .mail(user.getMail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    private User fetchUserAndActivate(VerificationToken token) {
        String username = token.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ApiException("User not found. USERNAME: " + username));
        user.setActivated(true);
        addUserRole(user);
        return userRepository.save(user);
    }


    private void addUserRole(User user) {
        user.getRoles().add(Role.ROLE_USER);
    }

    @Transactional
    public User getCurrentUser() {
        try {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();

            return userRepository.findByUsername(principal.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found, username: " + principal.getUsername()));
        } catch (ClassCastException ex) {
            throw new ApiException("Invalid token");
        }
    }

    public UserDto getCurrentUserDto() {
        User user = getCurrentUser();
        return UserDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .mail(user.getMail())
                .build();

    }

    private User mapUserFromRequest(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .mail(request.getMail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
