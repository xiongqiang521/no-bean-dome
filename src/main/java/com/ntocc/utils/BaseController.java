package com.ntocc.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Mechrevo
 * @version v1.0
 * 2019/11/10 0:11
 */
public class BaseController {
    private Map<String, Serializable> param;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        request = requestAttributes.getRequest();
        return this.request;
    }

    public HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        response = requestAttributes.getResponse();
        return this.response;
    }

    public HttpSession getSession() {
        session = request.getSession();
        return this.session;
    }

    public Map<String, Serializable> getParam() throws Exception {


        // HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        // HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String, String[]> parameterMap = request.getParameterMap();

        // 数组长度为1的数据提出
        Map<String, Serializable> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {

            int length = entry.getValue().length;
            if (length == 0) {
                map.put(entry.getKey(), null);
            } else if (length == 1) {
                String s = entry.getValue()[0];
                if ("GET".equals(request.getMethod())) {
                    s = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                }
                // 匹配int
                Pattern pattern = Pattern.compile("-?[0-9]{1,9}");
                boolean matches = pattern.matcher(s).matches();
                if (matches) {
                    map.put(entry.getKey(), Integer.parseInt(s));
                    continue; // 结束本次循环，后面代码不用执行
                }
                // 匹配double
                pattern = Pattern.compile("-?[0-9]{1,9}\\.[0-9]{1,9}");
                matches = pattern.matcher(s).matches();
                // 数字最大长度为9，过长会抛出异常，可以用BigDecimal更高精度的数字
                if (matches && s.replace("-", "").replace(".", "").length() < 10) {
                    map.put(entry.getKey(), Double.parseDouble(s));
                } else {
                    map.put(entry.getKey(), s);
                }

            } else {
                String[] values = entry.getValue();
                String val = "";
                for (String value : values) {
                    val += value + ",";
                }
                if ("GET".equals(request.getMethod())) {
                    val = new String(val.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                }

                map.put(entry.getKey(), val.substring(0, val.length() - 1));
            }

        }

        return map;
    }
}
