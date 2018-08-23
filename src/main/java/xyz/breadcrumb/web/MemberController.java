package xyz.breadcrumb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.MemberService;

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
        /*
        ResponseEntity<String> entity = null;
        */
        try {
            memberService.add(member);
            result.put("status", "success");
            /*
            entity = new ResponseEntity<String> ("success", HttpStatus.OK);
            */
        } catch (Exception e) {
            result.put("status", "fail");
            /*
            entity = new ResponseEntity<String> ("fail", HttpStatus.NOT_FOUND);
            */
        }
        return result;
    }
}