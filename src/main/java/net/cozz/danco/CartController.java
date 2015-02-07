package net.cozz.danco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;


@Controller
public class CartController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CartController.class);
	
	private final NumberFormat numberFormat = new DecimalFormat("#0.00");

	@Autowired
	private BookManager bookManager;


    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(HttpSession session, 
    		@RequestParam(value="isbn", required=false) String isbn,
    		Model model) {
    	
    	ShoppingCart cart = session.getAttribute("cart") == null 
    			? new ShoppingCart() 
    			: (ShoppingCart) session.getAttribute("cart");
    	
        model.addAttribute("username", session.getAttribute("username"));
		if (isbn != null) {
			LOGGER.info("Removing isbn: " + isbn);
			cart.remove(isbn);
		}
		double orderTotal = 0;
		double tax = 0;
		double shipping = 0;
    	if (cart.getBooks().size() > 0) {
	    	orderTotal = cart.getCartTotal();
	    	tax = cart.getSalesTax();
	    	orderTotal += tax;
	    	shipping = cart.getShipping(); 
        	orderTotal += shipping;
    	} 

    	model.addAttribute("subtotal", numberFormat.format(cart.getCartTotal()));
    	model.addAttribute("tax", numberFormat.format(tax));
    	model.addAttribute("shipping", numberFormat.format(shipping));
    	model.addAttribute("orderTotal", numberFormat.format(orderTotal));

    	model.addAttribute("books", cart.getBooks());
    	if (cart.getBooks().isEmpty()) {
    		cart = null;
    	}
    	model.addAttribute("cart", cart);
    	LOGGER.info("Cart param is: " + cart == null ? "empty" : "not empty");
    	return "cart";
    }
	

    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    public String addToCart(HttpSession session,
    		@RequestParam(value="isbn", required=false) String isbn,
    		Model model,
            @ModelAttribute Book book) {

    	book = bookManager.getByIsbn(isbn);
    	ShoppingCart cart = session.getAttribute("cart") == null 
    			? new ShoppingCart() 
    			: (ShoppingCart) session.getAttribute("cart");
    	cart.addBook(book);
    	session.setAttribute("cart", cart);
    	model.addAttribute("cart", cart);
    	LOGGER.info("Cart param is: " + cart == null ? "empty" : "not empty");
    	
        return "redirect:";
    }
    
    
    @RequestMapping(value = "/checkout", method = {RequestMethod.POST, RequestMethod.GET}) 
    public String checkout(HttpSession session,
    		Model model) {
    	UserInfo userInfo = (UserInfo) session.getAttribute("userinfo");
    	LOGGER.info("Retrieved userinfo from session: " + userInfo);
    	
    	Enumeration sessionEnumerator = session.getAttributeNames();
    	while (sessionEnumerator.hasMoreElements()) {
    		String attrName = sessionEnumerator.nextElement().toString();
    		model.addAttribute(attrName, session.getAttribute(attrName));
    		LOGGER.info(String.format("Added %s with value %s to model", attrName, session.getAttribute(attrName).toString()));
    	}
    	
    	if (userInfo != null && userInfo.getMailingAddresses() != null) {
    		if (userInfo.getMailingAddresses().size() == 0) {
    			LOGGER.info(String.format("User %s has no mailing addresses", userInfo.getName()));
        		return "redirect:account";
    		}
    	}
    	LOGGER.info(String.format("User has %d mailing addresses", userInfo.getMailingAddresses().size()));
    	LOGGER.info(String.format("User %s has mailing address of %s", userInfo.getName(), userInfo.getDefaultMailingAddress()));
    	return "checkout";
    }
    
    @RequestMapping(value = "/confirmPurchase", method = RequestMethod.POST)
    public String finalCheckout(HttpSession session, Model model) {
    	
    	return "thankyou";
    }
    
    
    @RequestMapping(value = "/thankyou", method = RequestMethod.GET)
    public String purchaseConfirmation(HttpSession session, Model model) {
    	
    	return "redirect:";
    }
}
