package com.myblog.service;

import com.myblog.domain.User;
import com.myblog.exception.AlreadyExistsEmailException;
import com.myblog.repository.UserRepository;
import com.myblog.request.Signup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .email("wlsrl0515@naver.com")
                .password("1234")
                .name("Jingi")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("wlsrl0515@naver.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotEquals("1234", user.getPassword());
        assertEquals("Jingi", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복 이메일")
    void test2() {
        // given
        User user = User.builder()
                .email("wlsrl0515@naver.com")
                .password("1234")
                .name("Haemin")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .password("1234")
                .email("wlsrl0515@naver.com")
                .name("Jingi")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

}