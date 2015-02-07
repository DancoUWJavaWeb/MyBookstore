package net.cozz.danco;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class UserInfoController {
	
	private static Logger LOGGER = Logger.getLogger(UserInfoController.class.getCanonicalName());

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView displayLoginForm(HttpSession session) {
		ModelAndView modelAndView = null;
		
		UserInfo userInfo = session.getAttribute("userinfo") == null ? new UserInfo() : (UserInfo) session.getAttribute("userinfo");
		modelAndView = new ModelAndView("login", "userinfo", userInfo);
		
		// this is equivalent to returning a string and adding: 
		//    model.addAttribute("userinfo", new UserInfo());
		// in this case you'd pass in the Model to the method
		
		return modelAndView;
	}
	
	
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String processLogin(HttpSession session, @ModelAttribute UserInfo user) {
//
//		LOGGER.info(String.format("setting user's name=%s in session", user.getName()));
//		session.setAttribute("username", user.getName());
//		// this is the same, essentially, as redirecting to root. Leaving the value after the colon empty goes to root
//		return "redirect:";	
//	}

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(HttpSession session,
                               @ModelAttribute @Valid UserInfo user) {

        session.setAttribute("username", user.getName());
        session.setAttribute("userinfo", user);
        LOGGER.info("Added userinfo object to session");
        
        return "redirect:";
    }
	
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.removeAttribute("username");
        session.removeAttribute("userinfo");
        session.removeAttribute("cart");
        
        return "redirect:";
    }
    
    
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(HttpSession session, Model model) {
    	
    	model.addAttribute("address", new MailingAddress());
    	model.addAttribute("username", session.getAttribute("username"));
    	model.addAttribute("cart", session.getAttribute("cart"));
    	LOGGER.info("Added cart to model");
    	Object obj = session.getAttribute("userinfo");
    	if (obj != null) {
    		UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
    		model.addAttribute("userinfo", userInfo);
    		List<MailingAddress> addresses = userInfo.getMailingAddresses();
    		if (addresses == null) {
    			addresses = new ArrayList<MailingAddress>();
    		}
    		model.addAttribute("addresses", addresses);
    		LOGGER.info("Added model param mailingAddresses of size: " + userInfo.getMailingAddresses().size());
    	} else {
    		model.addAttribute("userinfo", new UserInfo());
    		LOGGER.info("Unable to find userinfo object in session");
    	}
    	
    	return "account";
    }
    
    
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public String addAccount(HttpSession session, Model model, 
    		@ModelAttribute @Valid MailingAddress address) {
    	
    	LOGGER.info("Adding new user address");
    	UserInfo userInfo = new UserInfo();
    	if (session.getAttribute("userinfo") != null) {
    		LOGGER.info("Retrieved userinfo object from session.");
    		userInfo = (UserInfo) session.getAttribute("userinfo");
    	}
    	userInfo.addAddress(address);
       	LOGGER.info(String.format("Added address %s to userinfo named %s", address.getStreetAddress(), userInfo.getName()));
       	session.setAttribute("userinfo", userInfo);
       	model.addAttribute("userinfo", userInfo);
    	return "redirect:";
    }


    @RequestMapping(value = "/updateAccount", method = RequestMethod.POST)
    public String updateAccount(HttpSession session, Model model, 
    		@ModelAttribute @Valid UserInfo userInfo) {
    	
    	if (session.getAttribute("userinfo") != null) {
    		LOGGER.info("Retrieved userinfo object from session.");
    		UserInfo oldUserInfo = (UserInfo) session.getAttribute("userinfo");
    		userInfo.setMailingAddresses(oldUserInfo.getMailingAddresses());
    	}
        session.setAttribute("username", userInfo.getName());
        model.addAttribute("username", userInfo.getName());
       	session.setAttribute("userinfo", userInfo);
       	model.addAttribute("userinfo", userInfo);
    	return "redirect:";
    }
}
