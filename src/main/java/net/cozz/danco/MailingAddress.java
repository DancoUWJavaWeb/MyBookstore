package net.cozz.danco;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class MailingAddress implements Serializable {


    @Size(min=6, max=100, message = "Street address must include house number and street name")
	private String streetAddress;

    @Size(min=1, max=45, message = "Include up to 45 characters for the destination city")
	private String city;

    @Size(min=2, max=2, message = "Please enter a 2-letter state abreviation.")
	private String state;

    @Pattern(regexp = "\\d{5}",
            message="must be a valid 5-digit zipcode")
	private String zip;

	private int index;
	
	
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		return streetAddress + "\n" + 
				city + ", " + zip;
	}

	
	
}
