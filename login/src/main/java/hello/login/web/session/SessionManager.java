package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component // 스프링 빈으로 등록되도록 설정
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId"; // 쓸 일이 많으니 상수로 만들어준다.

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
    // 동시에 여러 요청(스레드)로 접근할 수 있으므로, 이런 동시성 문제가 있을 때는 ConcurrentHashMap을 사용한다.

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {

        // sessionId를 생성하고, 값을 세션에 저장한다.
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value); // UUID로 sessionId를 만들어주고, 넘어온 value를 값으로 넣는다.

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies) // 배열을 스트림으로 변경해준다.
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
