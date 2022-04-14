package com.project.karrot.controller.auth;

import com.project.karrot.src.member.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomMemberSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomMember>{
/*
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

 */
    @Override
    public SecurityContext createSecurityContext(WithMockCustomMember customMember) {

        //UserDetails principal = customUserDetailsService.loadUserByUsername(customMember.username());

        Member member = new Member("test", customMember.username());
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(member, customMember.password(), List.of(new SimpleGrantedAuthority(customMember.role())));

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);

        return context;
    }
}
