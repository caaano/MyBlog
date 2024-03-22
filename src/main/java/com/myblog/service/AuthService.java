package com.myblog.service;

import com.myblog.domain.Session;
import com.myblog.domain.User;
import com.myblog.exception.InvalidSigninInformation;
import com.myblog.repository.UserRepository;
import com.myblog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();

        return session.getAccessToken();
    }
}
