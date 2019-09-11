package com.example.demo;

import com.example.demo.Model.User;
import com.example.demo.Services.MyUserDetailsService;
import com.example.demo.Services.SecurityService;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeContoller {
    
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;


    @GetMapping("/")
    public String hello(HttpServletRequest request, HttpSession session){
        String name = request.getParameter("name");
        session.setAttribute("name",name);
        return "hello";
    }

    @GetMapping("/welcome")
    public ModelAndView welcome(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject( "name","Junjie");
        mv.setViewName("welcome");
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView register(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("newUser", new User());
        mv.setViewName("registration");
        return mv;
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("newUser")User u, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (!userService.addNewUser(u)) {
            return "/register";
        }
        securityService.autoLogin(u.getUsername(),u.getPassword(),request);
        redirectAttributes.addAttribute("name",u.getUsername());
        return "redirect:/";
    }
}
