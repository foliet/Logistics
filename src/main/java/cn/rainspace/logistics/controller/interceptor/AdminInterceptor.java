package cn.rainspace.logistics.controller.interceptor;

import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.service.Errors;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(user.getGroupId()!=1){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(Errors.NOT_ADMIN);
            return false;
        }
        return true;
    }

}
