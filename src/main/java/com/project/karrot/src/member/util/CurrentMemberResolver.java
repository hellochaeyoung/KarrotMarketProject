package com.project.karrot.src.member.util;

import com.project.karrot.src.annotation.CurrentMemberId;
import com.project.karrot.src.member.MemberAuthService;
import com.project.karrot.src.member.MemberDetailsService;
import com.project.karrot.src.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentMemberResolver implements HandlerMethodArgumentResolver {

    private final MemberAuthService memberAuthService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentMemberId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return memberAuthService.login().get().getId();
    }
}
