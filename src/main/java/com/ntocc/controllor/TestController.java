package com.ntocc.controllor;

import com.ntocc.dao.DaoSupport;
import com.ntocc.utils.BaseController;
import com.ntocc.utils.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName TestController
 * @Description: TODO
 * @Author 熊强
 * @Date 2019/11/7
 * @Version V1.0
 */
@Controller
@RequestMapping("/user")
public class TestController extends BaseController {
    @Autowired
    @Qualifier("daoSupport")
    private DaoSupport dao;

    @RequestMapping("/login")
    public ModelAndView login() throws Exception {
        ModelAndView mv=new ModelAndView();

        Map<String, Serializable> param = getParam();
        Set<String> keySet = param.keySet();
        for (String o : keySet) {
            System.out.println(o.toString()+":"+param.get(o));
        }

        Map<String, Object> value = (Map<String, Object>)dao.findForObject("TestMapper.login", param);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        attributes.getRequest().getSession().setAttribute("user",value);
        if (value != null) {
            mv.setViewName("redirect:/index.html");
        }else{
            mv.setViewName("redirect:/login.html");
        }
        return mv;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public ResultPage getUser( HttpServletRequest request) throws Exception {
        Map<String ,Object > user = (Map<String, Object>) request.getSession().getAttribute("user");
        if (user==null || user.isEmpty()) {
            return ResultPage.failed("未登录");
        }
        return ResultPage.success(user);
    }
}
