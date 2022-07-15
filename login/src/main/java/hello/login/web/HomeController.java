package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        // 쿠키를 가져오는 것은 HttpServletRequest에서도 가능하지만, 여기선 CookieValue를 사용하겠다.
        // 가져오는 쿠키 값은 String이지만, 자동으로 타입 컨버팅을 해줘서 Long으로 변경해준다.
        // 로그인하지 않은(쿠키 값이 없는) 사용자도 들어오기 때문에 required는 false로 둔다.

        if (memberId == null) {
            return "home";
        }

        // 로그인 된 사용자
        Member loginMember = memberRepository.findById(memberId);// 쿠키 값이 정상적으로 회원을 찾을 수 있는지 확인한다.
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome"; // 로그인 사용자 전용 홈을 보여준다.
    }

    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        // 로그인 판별 = 세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        // 로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

}