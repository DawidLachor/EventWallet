package pl.skorpjdk.walletproject.user;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.skorpjdk.walletproject.jwt.AuthenticationResponse;
import pl.skorpjdk.walletproject.login.LoginRequest;
import pl.skorpjdk.walletproject.registration.token.ConfirmationToken;
import pl.skorpjdk.walletproject.registration.token.ConfirmationTokenService;
import pl.skorpjdk.walletproject.jwt.JwtProvider;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public String singUpUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            if(userOptional.get().getEnabled())
                throw new IllegalStateException(String.format("Email %s exist in database", user.getEmail()));
        }
        
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        
        userRepository.save(user);

        return getToken(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        if (!checkEnabled(loginRequest))
            throw new IllegalStateException("Username don't enable account");
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String generateToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(generateToken, loginRequest.getUsername());
    }

    public User getCurrentUser(){
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User don't found by username"));
    }

    private boolean checkEnabled(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new IllegalStateException("Username exist in database"));
        return user.getEnabled();
    }

    private String getToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }


    public void enableAppUser(String email) {
        userRepository.enableUser(email);
    }
}
