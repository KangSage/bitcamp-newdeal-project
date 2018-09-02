package xyz.breadcrumb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("signUp")
    public Object signUp(Member member) {
        System.out.println(member);
        HashMap<String, Object> result = new HashMap<>();

        try {
            memberService.add(member);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "fail");
        }
        return result;
    }

    @PostMapping("update")
    public Object update(
            String oldPassword, String newPassword, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String, Object> result = new HashMap<>();

        try {
            int count = memberService.update(loginUser.getNo(), oldPassword, newPassword);
            if (count == 1) {
                // 삭제 된 유저 정보가 있는 세션을 무효화시킨다.
                session.invalidate();
                result.put("status", "success");
                result.put("location", "login.html");
            } else {
                result.put("status", "update-fail");
            }
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }

    @PostMapping("exit")
    public Object exit(String email, String password, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String, Object> result = new HashMap<>();

        try {
            int count = memberService.delete(loginUser.getNo(), email, password);
            if (count == 1) {
                // 삭제 된 유저 정보가 있는 세션을 무효화시킨다.
                session.invalidate();
                result.put("status", "success");
            }
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
}
