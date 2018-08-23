package xyz.breadcrumb.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.breadcrumb.domain.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;

public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {

        System.out.println("인터셉터!");
        HttpSession session = request.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");
        System.out.printf("loginUser:%s\n", loginUser);

        if (loginUser == null) { // 로그인을 하지 않았으면 로그인 폼으로 보낸다.
            HashMap<String,Object> result = new HashMap<>();
            result.put("status", "fail");
            result.put("message", "사용 권한이 없습니다.");

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();

            ObjectMapper mapper = new ObjectMapper();
            out.print(mapper.writeValueAsString(result));
            return false;
            // 로그인 된 상태가 아니라면 다음 인터셉터의 실행을 모두 멈추고,
            // 즉시 로그인 폼으로 간다.
        }

        // 로그인 했으면 다음 인터셉터나 페이지 컨트롤러를 실행한다.
        return true;
    }
}