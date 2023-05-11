package org.unibl.etf.webshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/log-out")
@AllArgsConstructor
@Slf4j
public class LogOutController {

    @GetMapping()
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("Session successfully invalidated.");
        }
        return "redirect:/login";
    }

    @PostMapping
    public void signOut(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
    }
}
