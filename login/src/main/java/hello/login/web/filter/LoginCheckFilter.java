package hello.login.web.filter;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/", "/members/add", "/login", "/logout", "/css/*"};
    // 홈 화면이나 회원가입, 로그인 등 비로그인 사용자가 들어올 수 있는 페이지를 설정한다.

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작{}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    // 로그인으로 redirect
                    // 로그인 성공한 후에 원래 접속항려고 했던 페이지로 redirect 시켜주기 위해서 쿼리 추가.
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response); // URI가 로그인 체크를 해서 false가 나온 경우 다음 필터로 넘어간다.
        } catch (Exception e) {
            throw e; // 예외 로깅이 가능하지만, 톰캣까지 예외를 보내주어야 한다.
            // 서블릿 필터 같은 곳에서 예외가 터졌으면 그게 올라와야하는데 여기서 먹어버리면 정상 요청으로 동작하기 때문에.
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }

    }

    /**
     * 화이트 리스트의 경우 인증 체크 하지 않음.
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
        // 스프링이 제공해준다. 화이트리스트와 URI가 패턴 매치되는지 확인한다.
    }
}
