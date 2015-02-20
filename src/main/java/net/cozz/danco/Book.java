package net.cozz.danco;

import com.fasterxml.jackson.annotation.JsonView;

public class Book {

	private String isbn;
	private String title;
	private String description;
	private String author;
	private String genre;
	private String image;
	private double price;

    public interface BaseContent {};
    public interface Price {};
    public interface Description {};
    public interface NoPrice extends BaseContent, Description {};
    public interface NoDescription extends BaseContent, Price {};

    @JsonView(BaseContent.class)
    public String getISBN() {
		return isbn;
	}
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}

    @JsonView(BaseContent.class)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

    @JsonView(Description.class)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    @JsonView(BaseContent.class)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}

    @JsonView(BaseContent.class)
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

    @JsonView(Price.class)
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
