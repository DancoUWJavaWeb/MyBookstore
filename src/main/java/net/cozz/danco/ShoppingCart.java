package net.cozz.danco;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	private List<Book> books = new ArrayList<Book>();
	private double total;
	private double salesTax = 0;
	private double shipping = 0;
	
	
	public ShoppingCart() {
		super();
		books = new ArrayList<Book>();
		total = 0;
	}


	public List<Book> getBooks() {
		return books;
	}
	
	
	public void setBooks(List<Book> books) {
		this.books = books;
		double total = 0;
		for (Book book : books) {
			total += book.getPrice();
		}
		this.total = total;
	}


	public double getCartTotal() {
		return total;
	}
	
	public double getSalesTax() {
		return getCartTotal() * .08;
	}
	
	public double getShipping() {
		if (getBooks().isEmpty()) {
			return 0;
		}
		return shipping;
	}


	public void addBook(final Book book) {
		books.add(book);
		total += book.getPrice();
		salesTax += book.getPrice() * .08;
		shipping = getBooks().size() < 5 ? 5 : 15; 
	}
	
	
	public void remove(final String isbn) {
		for (Book book : books) {
			if (book.getISBN().equalsIgnoreCase(isbn)) {
				total -= book.getPrice();
				books.remove(book);
				break;
			}
		}
	}
}
