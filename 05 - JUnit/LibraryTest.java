package com.example.library;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LibraryTest {

    private Library library;

    @BeforeEach
    public void setUp() {
        library = new Library();
    }

    @AfterEach
    public void tearDown() {
        library = null;
    }

    @Test
    public void shouldConstructorCreateAnEmptyLibrary() {
        assertEquals(0, library.countBooks());
    }

    @Test
    public void shouldALibraryContainOneBookWhenTheFirstBookIsAdded() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        library.addBook(book);
        assertEquals(1, library.countBooks());
    }

    @Test
    public void shouldFindBooksByAuthorsReturnANonEmptyListWhenABookOfTheAuthorIsInTheLibrary() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        library.addBook(book);
        assertFalse(library.findBooksByAuthor("J.R.R. Tolkien").isEmpty());
    }

    @Test
    public void shouldALibraryBecomeEmptyWhenTheLastBookIsRemoved() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        library.addBook(book);
        library.removeBook("The Hobbit", "J.R.R. Tolkien");
        assertEquals(0, library.countBooks());
    }

    @Test
    public void shouldRemoveBookDecreaseTheNumberOfBooks() {
        Book book1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        Book book2 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);
        library.addBook(book1);
        library.addBook(book2);
        library.removeBook("The Hobbit", "J.R.R. Tolkien");
        assertEquals(1, library.countBooks());
    }

    @Test
    public void shouldRemoveBookRaiseAnExceptionIfTheBookDoesNotExist() {
        Book book = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        library.addBook(book);
        assertThrows(BookIsNotInLibraryException.class, () -> library.removeBook("Nonexistent Book", "Unknown Author"));
    }
}
