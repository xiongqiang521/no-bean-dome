package com.ntocc.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mechrevo
 * @version v1.0
 * 2019/11/9 23:47
 */
@Controller
public class PathController {

    @RequestMapping({"*.html","*/*.html","*/*/*.html"})
    public String welcome() {
        return null;
    }
}
