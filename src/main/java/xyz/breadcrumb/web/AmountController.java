package xyz.breadcrumb.web;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import xyz.breadcrumb.domain.Amount;
import xyz.breadcrumb.domain.DayHistory;
import xyz.breadcrumb.domain.Member;
import xyz.breadcrumb.service.AmountService;
import xyz.breadcrumb.web.util.Base64Decoder;
import xyz.breadcrumb.web.util.ThumbnailMaker;

@RestController
@RequestMapping("/amount")
public class AmountController {
    
    @Autowired AmountService amountService;
    @Autowired ServletContext servletContext;

    @CrossOrigin()
    @RequestMapping("list")
    public Object list(int monthOperator, HttpServletRequest httpRequest) throws Exception {

        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        Calendar cal = new GregorianCalendar(Locale.KOREA);
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, monthOperator); //들어오는 숫자만큼 더 한다.

        SimpleDateFormat yearMonthFormat =
                new SimpleDateFormat("yyyy-MM");

        String selectDate = yearMonthFormat.format(cal.getTime());

        HashMap<String, Object> data = new HashMap<>();

        try {
            List<DayHistory> list = amountService.list(
                    loginUser.getNo(), selectDate + '%');

            data.put("list", list);
            data.put("selectDate",  selectDate);

            Integer totalIncomeAmount =
                    amountService.getTotalAmount(
                            loginUser.getNo(), "수입", selectDate + '%');

            Integer totalBudgetAmount =
                    amountService.getTotalAmount(
                            loginUser.getNo(), "지출", selectDate + '%');

            if (totalBudgetAmount != null && totalIncomeAmount != null ) {
                Integer monthlyTotalAmount = (totalIncomeAmount - totalBudgetAmount);
                System.out.printf("monthlyTotalAmount => %d", monthlyTotalAmount);
                data.put("monthlyTotalAmount", monthlyTotalAmount);
            }

            System.out.printf("totalIncomeAmount => %d", totalIncomeAmount);
            System.out.printf("totalBudgetAmount => %d", totalBudgetAmount);

            data.put("totalIncomeAmount",  totalIncomeAmount);
            data.put("totalBudgetAmount",  totalBudgetAmount);
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("message", e.getMessage());
            data.put("selectDate",  selectDate);

            System.out.println("catch 문으로 넘어 옴");
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    @GetMapping("{no}")
    public Object get(
            @PathVariable int no, HttpSession session) {
        Member loginUser = (Member)session.getAttribute("loginUser");

        HashMap<String,Object> result = new HashMap<>();
        try {
            Amount amount = amountService.get(no, loginUser.getNo());
            result.put("status", "success");
            result.put("data", amount);
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }    
    
    @PostMapping("add")
    public Object add(
            Amount amount,
            @RequestParam(value="base64Image", required=false) String base64Image,
            HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        if (!base64Image.isEmpty()) {

            String uploadDir = servletContext.getRealPath("/download");
            String filename = getNewFilename(".jpg");
            Base64Decoder.decoder(base64Image, uploadDir + "//" + filename);
            amount.setReceiptFile(filename);

            String thumbnail100 = ThumbnailMaker.thumbnailMaker(100, 100, uploadDir, filename, "100");
            String thumbnail200 = ThumbnailMaker.thumbnailMaker(200, 200, uploadDir, filename, "200");
            String thumbnail300 = ThumbnailMaker.thumbnailMaker(300, 300, uploadDir, filename, "300");
        }

        HashMap<String,Object> result = new HashMap<>();
        try {
            amount.setMemberNo(loginUser.getNo());
            amountService.add(amount);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    
    @PostMapping("update")
    public Object update(
            Amount amount,
            HttpServletRequest httpRequest) {
        
        HttpSession session = httpRequest.getSession();
        
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String, Object> result = new HashMap<>();
        try {
            amount.setMemberNo(loginUser.getNo());
            amountService.update(amount);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "fail");
            result.put("message", e.getMessage());
        }
        return result;
    }
    
    @PostMapping("delete")
    public Object delete(
            int no,
            HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession();
        Member loginUser = (Member) session.getAttribute("loginUser");

        HashMap<String,Object> data = new HashMap<>();
        try {
            amountService.delete(no, loginUser.getNo());
            data.put("status", "success");
        } catch (Exception e) {
            data.put("status", "fail");
            data.put("message", e.getMessage());
        }
        return data;
    }

    long prevMillis = 0;
    int count = 0;

    // 다른 클라이언트가 보낸 파일명과 중복되지 않도록
    // 서버에 파일을 저장할 때는 새 파일명을 만든다.
    synchronized private String getNewFilename(String png) {
        long currMillis = System.currentTimeMillis();
        if (prevMillis != currMillis) {
            count = 0;
            prevMillis = currMillis;
        }

        return  currMillis + "_" + count++ + extractFileExtName(png);
    }

    // 파일명에서 뒤의 확장자명을 추출한다.
    private String extractFileExtName(String filename) {
        int dotPosition = filename.lastIndexOf(".");

        if (dotPosition == -1)
            return "";

        return filename.substring(dotPosition);
    }

}
