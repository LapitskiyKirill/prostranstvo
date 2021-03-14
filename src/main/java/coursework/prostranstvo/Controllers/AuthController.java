package coursework.prostranstvo.Controllers;

import coursework.prostranstvo.Model.DTO.AuthRequest;
import coursework.prostranstvo.Model.DTO.AuthResponse;
import coursework.prostranstvo.Model.DTO.RegisterRequest;
import coursework.prostranstvo.Model.DTO.UserDto;
import coursework.prostranstvo.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest request) {
        authService.signUp(request);
        return new ResponseEntity<>("Check your email", HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<UserDto> verify(@PathVariable("token") String token) {
        return new ResponseEntity<>(authService.userVerify(token), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody AuthRequest request) {
        return new ResponseEntity<>(authService.authorizeUser(request), HttpStatus.OK);
    }

    @PostMapping("/autologin")
    public ResponseEntity<UserDto> signIn(HttpServletRequest request) {
        if (request.getHeader("Authorization") != null) {
            return new ResponseEntity<>(authService.getCurrentUserDto(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
