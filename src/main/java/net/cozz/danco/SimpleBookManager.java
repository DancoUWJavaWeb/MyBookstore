package net.cozz.danco;

import com.sun.tools.javac.tree.JCTree;

import java.util.List;

public class SimpleBookManager implements BookManager {

	private List<Book> books;
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public List<Book> getBooks() {
		return books;
	}
	
	
	@Override
	public Book getByTitle(final String title) {
		Book target = null;
		for (Book book : books) {
			if (book.getTitle().equalsIgnoreCase(title)) {
				target = book;
				break;
			}
		}
		
		return target;
	}


	@Override
	public Book getByIsbn(final String isbn) {
		Book target = null;
		for (Book book : books) {
			if (book.getISBN().equalsIgnoreCase(isbn)) {
				target = book;
				break;
			}
		}
		
		return target;
	}


    @Override
    public void addBook(Book book) {
        books.add(book);
    }


    @Override
    public void updateBook(Book book) {
        Book target = getByIsbn(book.getISBN());
        if (target != null) {
            books.remove(target);
        }
        books.add(book);
    }


    @Override
    public boolean removeBook(String isbn) {
        Book book = getByIsbn(isbn);
        if (book != null) {
            return books.remove(book);
        }

        return false;
    }
}
