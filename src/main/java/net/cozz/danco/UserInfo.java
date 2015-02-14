package net.cozz.danco;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Size(min=2, max=40, message = "Your name must be between 2 & 40 characters")
	private String name;

    @Size(min = 8, max = 16, message = "You must choose a password between 8 and 16 characters inclusive")
	private String password;


    @Pattern(regexp=".*@.*[.com|.net|.org|.edu|.io|.info]",
            message="must be a valid email address in an acceptable domain: .com, .net, .org, .edu, .io, .info")
	private String emailAddress;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Please enter a valid phone number.")
	private String phoneNumber;

    @Pattern(regexp = "4\\d{15}", message = "You need to provide a cc number.")
	private String creditCard;

    @Future(message = "Your cc needs to expire in the future.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date ccExpDate;

    private MailingAddress defaultMailingAddress;
	private List<MailingAddress> mailingAddresses = new ArrayList<MailingAddress>();
	private List<String> creditCardNumbers = new ArrayList<String>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getCreditCard() {
		return creditCard;
	}
	
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}


    public Date getCcExpDate() {
        return ccExpDate;
    }


    public void setCcExpDate(Date ccExpDate) {
        this.ccExpDate = ccExpDate;
    }


    public List<MailingAddress> getMailingAddresses() {
		return mailingAddresses;
	}
	
	public void setMailingAddresses(List<MailingAddress> mailingAddresses) {
		this.mailingAddresses = mailingAddresses;
	}
	
	public MailingAddress getDefaultMailingAddress() {
		if (mailingAddresses == null || mailingAddresses.isEmpty()) {
			return defaultMailingAddress = null;
		} else {
            defaultMailingAddress = mailingAddresses.get(0);
        }
		return defaultMailingAddress;
	}
	
	public void addAddress(final MailingAddress address) {
		if (mailingAddresses == null) {
			mailingAddresses = new ArrayList<MailingAddress>();
		}
		mailingAddresses.add(address);
		address.setIndex(mailingAddresses.size());
	}
	
	public void addCreditCard(final String cc) {
		if (creditCardNumbers == null) {
			creditCardNumbers = new ArrayList<String>();
		}
		creditCardNumbers.add(cc);
	}
	
	
}
