package net.cozz.danco;

import java.util.List;

public interface BookManager {

	public List<Book> getBooks();
	public Book getByTitle(final String title);
	public Book getByIsbn(final String isbn);
    public void addBook(Book book);
    public void updateBook(Book book);
    public boolean removeBook(String isbn);
}
