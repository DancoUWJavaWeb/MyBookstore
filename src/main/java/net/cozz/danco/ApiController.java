package net.cozz.danco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by costd035 on 2/26/15.
 */
// @RestController automatically makes the methods with request mappings return a response body
@RestController
@RequestMapping("/api")     // append the /api string to each request mapping
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class.getName());

    @Autowired
    private BookManager bookManager;


    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> getBooks() {
        return bookManager.getBooks();
    }


    @RequestMapping(value = "/books/{isbn}", method = RequestMethod.GET)
    public Object getBook(@PathVariable("isbn") String isbn) {

        Book book = bookManager.getByIsbn(isbn);
        if (book == null) {
            return new ApiMessage(ApiMessage.MsgType.ERROR, String.format("Unable to find book with isbn = %s", isbn));
        }

        return book;
    }


    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public Object createBook(@RequestBody Book book, HttpServletResponse response) {
        if (bookManager.getByIsbn(book.getISBN()) != null) {
            return new ApiMessage(ApiMessage.MsgType.ERROR,
                    String.format("Book with isbn %s already exists.", book.getISBN()));
        }

        bookManager.addBook(book);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return bookManager.getByIsbn(book.getISBN());
    }


    @RequestMapping(value = "/books/{isbn}", method = RequestMethod.PUT)
    public Object updateBook(@RequestBody Book book, HttpServletResponse response) {
        bookManager.updateBook(book);

        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        return bookManager.getByIsbn(book.getISBN());
    }


    @RequestMapping(value = "/books/{isbn}", method = RequestMethod.DELETE)
    public ApiMessage deleteBook(@PathVariable("isbn") String isbn, HttpServletResponse response) {
        boolean success = bookManager.removeBook(isbn);

        if (success) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return new ApiMessage(ApiMessage.MsgType.INFO, String.format("Book with isbn %s removed.", isbn));
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new ApiMessage(ApiMessage.MsgType.ERROR, String.format("Book with isbn %s not found", isbn));
        }
    }
}
