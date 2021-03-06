package xyz.breadcrumb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.repository.MemberRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    MemberRepository memberRepository;
    
   @PostMapping("/login")
    public Object login(
           @RequestParam("email") String email,
           @RequestParam("password") String password,
           @RequestParam(value = "saveEmail", required = false) boolean saveEmail,
           HttpSession session, HttpServletResponse response) throws Exception {

       Cookie cookie = null;
       if (saveEmail) {
           // 입력폼에서 로그인할 때 사용한 ID를 자동으로 출력할 수 있도록
           // 웹브라우저로 보내 저장시킨다.
           cookie = new Cookie("email", email);
           cookie.setMaxAge(60 * 60 * 24 * 7);
       } else { // "아이디 저장" 체크박스를 체크하지 않았다면
           cookie = new Cookie("email", "");
           cookie.setMaxAge(0); // 웹브라우저에 "id"라는 이름으로 저장된 쿠키가 있다면 제거한다.
           // 즉 유효기간을 0으로 설정함으로써 "id"라는 이름의 쿠키를 무효화시키는 것이다.
       }

        response.addCookie(cookie);

        HashMap<String,Object> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        Member member = memberRepository.findByEmailAndPassword(params);

        HashMap<String,Object> result = new HashMap<>();
        if (member != null) { // 로그인 성공!
            System.out.println("로그인 성공");
            session.setAttribute("loginUser", member);
            result.put("status", "success");
        } else { // 로그인 실패!
            System.out.println("로그인 실패");
            result.put("status", "fail");
            session.invalidate();
        }
        return result;
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpSession session) throws Exception {
        System.out.println("로그아웃");
        // 세션을 꺼내 무효화시킨다.
        session.invalidate();
        // 웹 애플리케이션의 시작 페이지로 가라고 웹브라우저에게 얘기한다.
        return "login.html";
    }
}
