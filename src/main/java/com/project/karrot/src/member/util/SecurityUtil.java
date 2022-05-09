package com.project.karrot.src.member.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    //private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {
    }

    public static Optional<String> getCurrentMembername() {
        log.info("SecurityUtil getCurrentMembername 시작");
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            log.info("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String email = null;
        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            email = springSecurityUser.getUsername();
            log.info("Security Context에 등록된 회원 {}", email);
        }else if(authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
            log.info("Security Context에 등록된 회원 {}", email);
        }

        return Optional.ofNullable(email);
    }
}
