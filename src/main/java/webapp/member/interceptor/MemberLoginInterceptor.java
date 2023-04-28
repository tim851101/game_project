package webapp.member.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import webapp.member.service.MemberService;

@Component
public class MemberLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberServiceImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(memberServiceImpl);
        String sessionId=memberServiceImpl.getJsessionIdFromCookie(request);
        Integer memNo=memberServiceImpl.getMemberNoFromSession(sessionId);
        System.out.println("preHandle : "+sessionId);
        if (sessionId == null || memNo == null) {
            request.getSession().setAttribute("currentUrl",request.getRequestURI());
            response.sendRedirect(request.getContextPath()+"/foreground/login.html");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
