package com.myblog.service;

import com.myblog.domain.Session;
import com.myblog.domain.User;
import com.myblog.exception.AlreadyExistsEmailException;
import com.myblog.exception.InvalidSigninInformation;
import com.myblog.repository.UserRepository;
import com.myblog.request.Login;
import com.myblog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();

        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);

        String encryptedPassword = encoder.encode(signup.getPassword());

        var user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();

        userRepository.save(user);
    }
}
