package net.cozz.danco;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Review.class.getSimpleName());

    private String isbn;
    private String text;
    private Date addedDate;

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-DD");
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String dateStr) {
        try {
            Date date = df.parse(dateStr);
            setAddedDate(date);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
