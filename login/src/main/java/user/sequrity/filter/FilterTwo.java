//package user.sequrity.filter;
//
//import user.jwt.util.JWTService;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//public class FilterTwo implements Filter {
//    //스프링 실행파일보다 먼저 실행되기때문에 안됨
//    //@AutoWired
//    //JWTService jwtservice; //null
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//        //토큰의 위조여부를 확인하는 필터
//        System.out.println("Filter Two");
//
//        HttpServletRequest req = (HttpServletRequest)request;
//        HttpServletResponse res = (HttpServletResponse)response;
//        //응답 객체
//        res.setCharacterEncoding("utf-8");
//        PrintWriter out = res.getWriter();
//
//        String token = req.getHeader("Authorization"); //헤더에서 Authorization값을 얻는다
//
//        //토큰 위조 검사
//        try {
//            boolean result = JWTService.validateToken(token);
//            if (result) {
//                chain.doFilter(request, response);
//            } else {
//                out.println("토큰 만료");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            out.println("<h3>토큰 위조</h3>");
//        }
//        //chain.doFilter(requset, response); //다음 필터로 연결
//    }
//}
