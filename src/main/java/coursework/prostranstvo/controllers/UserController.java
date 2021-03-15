package coursework.prostranstvo.controllers;

import coursework.prostranstvo.model.dto.PasswordChangeRequest;
import coursework.prostranstvo.model.dto.UserDetailsDto;
import coursework.prostranstvo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/updateDetails")
    public ResponseEntity<String> updateDetails(@RequestBody UserDetailsDto userDetails) {
        userService.updateDetails(userDetails);
        return new ResponseEntity<>("Details successfully updated.", HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordChangeRequest request) {
        userService.updateUserPassword(request);
        return new ResponseEntity<>("Details successfully updated.", HttpStatus.OK);
    }
}
