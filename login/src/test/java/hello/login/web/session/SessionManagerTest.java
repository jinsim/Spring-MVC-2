package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        // 세션 생성
        // 서버에서 세션과 쿠키를 만들어 response에 담아두었다. 서버입장에서는 웹 브라우저로 응답이 나간 상황
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장
        // 웹 브라우저가 쿠키를 만들어서 서버에 전달해주는 상황. response에 있는 Cookie를 가지고, request의 Cookie에 넣었다.
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // mySessionId에 UUID가 들어있을 것임.

        // 세션 조회
        // 웹 브라우저에서 넘어와서, 서버에서 확인하는 상황. 세션에서 조회 되는지 확인한다.
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // 세션 만료
        // request에 있는 세션 쿠키를 sessionStore에서 삭제한다.
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}