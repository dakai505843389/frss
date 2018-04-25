package com.hd.clc.frss.interceptor;

import com.hd.clc.frss.db.entity.Teacher;
import com.hd.clc.frss.db.impl.TeacherMapper;
import com.hd.clc.frss.service.ITeacherService;
import com.hd.clc.frss.vo.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static TeacherMapper teacherMapper;

    private static Map<String, UserSession> sessionMap = new HashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        /*String sessionId = (String) httpServletRequest.getSession().getAttribute("sessionId");
        if (sessionId != null && sessionId.length() != 0) {

        } else {
            httpServletRequest.setAttribute("code", -1);
            httpServletRequest.setAttribute("msg", "请先登录！");
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    /**-----------------------------------------公共方法-----------------------------------------**/

    /**
     * 通过request获取当前登录教师
     * @param httpServletRequest
     * @return
     */
    public static Teacher getTeacher(HttpServletRequest httpServletRequest){
        String sessionId = (String) httpServletRequest.getSession().getAttribute("sessionId");
        UserSession userSession = sessionMap.get(sessionId);
        if (userSession != null && userSession.getIsTeacher() == 1){
            return teacherMapper.queryById(userSession.getUserId());
        }else {
            return null;
        }
    }

}
