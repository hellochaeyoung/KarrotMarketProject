package com.project.karrot.controller.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomMemberSecurityContextFactory.class)
public @interface WithMockCustomMember {

    String username() default "cyahn@gmail.com";

    String password() default "cyahn";

    String role() default "ROLE_MEMBER";

}
