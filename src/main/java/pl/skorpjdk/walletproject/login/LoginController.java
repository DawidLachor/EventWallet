package pl.skorpjdk.walletproject.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.skorpjdk.walletproject.jwt.AuthenticationResponse;
import pl.skorpjdk.walletproject.user.UserService;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping()
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
}
