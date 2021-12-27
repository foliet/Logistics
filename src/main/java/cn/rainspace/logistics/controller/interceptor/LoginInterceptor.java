package cn.rainspace.logistics.controller.interceptor;

import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.service.Errors;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取HttpSession对象
        HttpSession session = request.getSession();
        // 获取我们登录后存在session中的用户信息，如果为空，表示session已经过期
        Object obj = session.getAttribute("user");
        if (!(obj instanceof User)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(Errors.NOT_LOGGED_IN.toJSONString());
            return false;
        }
        // 已经登录
        return true;
    }

}
