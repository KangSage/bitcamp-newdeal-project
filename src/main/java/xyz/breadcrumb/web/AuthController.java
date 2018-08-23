package xyz.breadcrumb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    MemberRepository memberRepository;

   @PostMapping("/login")
    public Object login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            boolean saveEmail,
            HttpSession session) throws Exception {
       System.out.println(saveEmail);
        Cookie cookie = null;
        if (saveEmail != true) {
            // 입력폼에서 로그인할 때 사용한 ID를 자동으로 출력할 수 있도록
            // 웹브라우저로 보내 저장시킨다.
            cookie = new Cookie("email", email);
            cookie.setMaxAge(60 * 60 * 24 * 7);
        } else { // "아이디 저장" 체크박스를 체크하지 않았다면
            cookie = new Cookie("email", "");
            cookie.setMaxAge(0); // 웹브라우저에 "id"라는 이름으로 저장된 쿠키가 있다면 제거한다.
            // 즉 유효기간을 0으로 설정함으로써 "id"라는 이름의 쿠키를 무효화시키는 것이다.
        }
        /*response.addCookie(cookie);*/

        HashMap<String,Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        Member member = memberRepository.findByEmailAndPassword(params);

        System.out.println(member);
        HashMap<String,Object> result = new HashMap<>();
        /*PrintWriter out = response.getWriter();*/
        /*ObjectMapper mapper = new ObjectMapper();*/
        if (member != null) { // 로그인 성공!
            System.out.println("로그인 성공");
            session.setAttribute("loginUser", member);
            System.out.println(session.getAttribute("loginUser").toString());
            result.put("status", "success");
            /*out.print(mapper.writeValueAsString(result));*/
        } else { // 로그인 실패!
            System.out.println("로그인 실패");
            result.put("status", "fail");
            session.invalidate();
            /*out.print(mapper.writeValueAsString(result));*/
        }
        return result;
    }

    @RequestMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpSession session) throws Exception {

        // 세션을 꺼내 무효화시킨다.
        session.invalidate();

        // 웹 애플리케이션의 시작 페이지로 가라고 웹브라우저에게 얘기한다.
        return "loginform.html"; // ==> "/java106-java-project"
    }
}
