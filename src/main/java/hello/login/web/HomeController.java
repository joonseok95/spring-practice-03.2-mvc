package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model m) {
        if (memberId == null) {
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }

        m.addAttribute("member", loginMember);
        return "loginHome";

    }

    //    @GetMapping("/")
    public String homeLogin2(HttpServletRequest request, Model m) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        //로그인
        if (member == null) {
            return "home";
        }

        m.addAttribute("member", member);
        return "loginHome";

    }

//    @GetMapping("/")
    public String homeLogin3(HttpServletRequest request, Model m) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        //로그인
        if (member == null) {
            return "home";
        }

        m.addAttribute("member", member);
        return "loginHome";

    }

//    @GetMapping("/")
    public String homeLogin4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member, Model m) {

        if (member == null) {
            return "home";
        }

        m.addAttribute("member", member);
        return "loginHome";

    }

    @GetMapping("/")
    public String homeLogin4ArgumentResolver(@Login Member member, Model m) {

        if (member == null) {
            return "home";
        }

        m.addAttribute("member", member);
        return "loginHome";

    }


}