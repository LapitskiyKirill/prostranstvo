package coursework.prostranstvo.Service;

import coursework.prostranstvo.Model.DTO.PasswordChangeRequest;
import coursework.prostranstvo.Model.DTO.UserDetailsDto;
import coursework.prostranstvo.Model.Entity.User;
import coursework.prostranstvo.Model.Exception.ApiException;
import coursework.prostranstvo.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder;

    public void updateDetails(UserDetailsDto userDetails) {
        User user = authService.getCurrentUser();
        user.setPhoneNumber(userDetails.getPhoneNumber());
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        userRepository.save(user);
    }

    public void updateUserPassword(PasswordChangeRequest request){
        User user = authService.getCurrentUser();
        if(passwordEncoder.encode(request.getOldPassword()).equals(user.getPassword())){
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        else {
            throw new ApiException("Passwords mismatch");
        }
    }
}
