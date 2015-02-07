package net.cozz.danco;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class MailingAddress implements Serializable {


	private String streetAddress;
	private String city;
	private String state;

    @Pattern(regexp = "\\d{5}",
            message="must be a valid zipcode")
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
