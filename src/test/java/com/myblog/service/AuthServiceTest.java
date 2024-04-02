package com.myblog.service;

import com.myblog.crypto.PasswordEncoder;
import com.myblog.domain.User;
import com.myblog.exception.AlreadyExistsEmailException;
import com.myblog.exception.InvalidSigninInformation;
import com.myblog.repository.UserRepository;
import com.myblog.request.Login;
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

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("wlsrl0515@naver.com")
                .password(encryptPassword)
                .name("Haemin")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("wlsrl0515@naver.com")
                .password("1234")
                .build();
        // when
        Long userId = authService.signin(login);

        // then
        assertNotNull(userId);

    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test4() {
        // given
        PasswordEncoder encoder = new PasswordEncoder();
        String encryptPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("wlsrl0515@naver.com")
                .password(encryptPassword)
                .name("Haemin")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("wlsrl0515@naver.com")
                .password("5678")
                .build();

        // expected
        assertThrows(InvalidSigninInformation.class, () -> authService.signin(login));

    }

}