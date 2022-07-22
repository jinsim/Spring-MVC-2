package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // 파라미터에 Login 애너테이션이 있는지 확인한다.
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); // 파라미터의 타입이 Member인지 확인한다.

        return hasLoginAnnotation && hasMemberType; // true면 resolveArgument가 실행된다.
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 실제 argument를 만들어서 반환해주어야 한다.

        log.info("resolveArgument 실행");

        /// WebRequest를 통해 HttpServletRequest와 HttpSession을 가져온다.
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest(); // HttpServletRequest를 가져온다.
        HttpSession session = request.getSession(false); // true로 하면 의미없는 세션이 만들어질 수 있기 때문이다.
        if (session == null) {
            return null; // session이 null 이면 @Login Member member에 null이 들어간다.
        }

        return session.getAttribute(SessionConst.LOGIN_MEMBER); // 있으면 member가, 없으면 null이 반환된다.
    }
}
