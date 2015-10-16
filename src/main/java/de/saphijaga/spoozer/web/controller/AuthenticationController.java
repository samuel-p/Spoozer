package de.saphijaga.spoozer.web.controller;

import de.saphijaga.spoozer.core.service.UserService;
import de.saphijaga.spoozer.web.details.UserDetails;
import de.saphijaga.spoozer.web.domain.request.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by samuel on 16.10.15.
 */
@Controller
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerAction(@ModelAttribute("user") @Valid RegisterUserRequest register, BindingResult result) {
        if (isUserLoggedin()) {
            return "redirect:/";
        }
        if (result.hasErrors()) {
            return "register";
        }
        Optional<UserDetails> user = userService.registerUser(register);
        if (!user.isPresent()) {
            result.addError(new ObjectError("registration", "something went wrong"));
            return "register";
        }
        return "redirect:/login";
    }

    @RequestMapping("/register")
    public ModelAndView getRegisterContent() {
        if (isUserLoggedin()) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView register = new ModelAndView("register");
        register.addObject("user", new RegisterUserRequest());
        return register;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginContent() {
        if (isUserLoggedin()) {
            return "redirect:/";
        }
        return "login";
    }

    private boolean isUserLoggedin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }
}