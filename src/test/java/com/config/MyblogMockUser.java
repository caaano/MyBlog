package com.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MyblogMockSecurityContext.class)
public @interface MyblogMockUser {

    String name() default "Jingi";
    String email() default "wlsrl515@gmail.com";

    String password() default "";

//    String role() default "ROLE_ADMIN";
}
