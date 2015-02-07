package net.cozz.danco;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Size(min=2, max=40)
	private String name;

    @Size(min = 8, max = 16)
	private String password;


    @Pattern(regexp=".*@.*[.com|.net|.org|.edu|.io|.info]",
            message="must be a valid email address in an acceptable domain: .com, .net, .org, .edu, .io, .info")
	private String emailAddress;

    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
	private String phoneNumber;

    @Pattern(regexp = "")
	private String creditCard;

    @Future
    @DateTimeFormat()
    private String ccExpDate;
	
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


    public String getCcExpDate() {
        return ccExpDate;
    }


    public void setCcExpDate(String ccExpDate) {
        this.ccExpDate = ccExpDate;
    }


    public List<MailingAddress> getMailingAddresses() {
		return mailingAddresses;
	}
	
	public void setMailingAddresses(List<MailingAddress> mailingAddresses) {
		this.mailingAddresses = mailingAddresses;
	}
	
	public String getDefaultMailingAddress() {
		if (mailingAddresses == null || mailingAddresses.isEmpty()) {
			return "";
		}
		return mailingAddresses.get(0).toString();
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
