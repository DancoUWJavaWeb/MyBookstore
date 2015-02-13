package net.cozz.danco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.NavigableMap;

@Controller
public class HomeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BookManager bookManager;

// Alternatively, we could be explicit about the setter
//	@Autowired always works on interfaces, not classes/objects.
//
//	@Autowired
//	public void setBookManager(BookManager bookManager) {
//		this.bookManager = bookManager;
//	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(Model model, 
			HttpServletRequest requestParam, 
			HttpServletResponse responseParam,
			HttpSession session,
			@RequestParam(value="title", required=true) String title,
			@RequestParam() Map<String, String> params) {

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("cart", session.getAttribute("cart"));
		model.addAttribute("book", bookManager.getByTitle(title));
    	LOGGER.info("Cart param is: " + session.getAttribute("cart") == null ? "empty" : "not empty");

		return "details";
	}


    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    public void head(ModelMap modelMap) {

    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, 
			HttpServletRequest requestParam, 
			HttpServletResponse responseParam,
			HttpSession session,
			@RequestParam(value="id", required=false) String id,
			@RequestParam() Map<String, String> params) {

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("cart", session.getAttribute("cart"));
		model.addAttribute("books", bookManager.getBooks());
		model.addAttribute("userinfo", session.getAttribute("userinfo"));

		return "home";
	}
	
}
