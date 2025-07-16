package com.example.library;

import java.util.ArrayList;
import java.util.List;

/** Represents a library managing a collection of books. */
public class Library {
  private final List<Book> books; // List to store the books in the library.

  /** Constructor that initializes the library with an empty list of books. */
  public Library() {
    this.books = new ArrayList<>();
  }

  /**
   * Adds a book to the library's collection.
   *
   * @param book The book to add to the library.
   */
  public void addBook(Book book) {
    books.add(book);
  }

  /**
   * Attempts to remove a book from the library's collection.
   *
   * @param title The title of the book to remove.
   * @param author The author of the book to remove.
   * @throws BookIsNotInLibraryException exception if the book is not in the library .
   */
  public void removeBook(String title, String author) {
    boolean bookNotFound = true;
    int index = 0;

    while (bookNotFound && (index < books.size())) {
      Book book = books.get(index);
      if (book.getAuthor().equalsIgnoreCase(author) && book.getTitle().equalsIgnoreCase(title)) {
        books.remove(book);
        bookNotFound = false;
      }
      index++;
    }
    if (bookNotFound) {
      throw new BookIsNotInLibraryException(
          "The book " + title + ", by " + author + " is not in the library");
    }
  }

  /**
   * Searches for books in the library by author.
   *
   * @param author The author whose books to search for.
   * @return A list of found books matching the given author.
   */
  public List<Book> findBooksByAuthor(String author) {
    List<Book> foundBooks = new ArrayList<>();
    for (Book book : books) {
      if (book.getAuthor().equalsIgnoreCase(author)) {
        foundBooks.add(book);
      }
    }
    return foundBooks;
  }

  /**
   * Searches for books in the library by title.
   *
   * @param title The title to search for.
   * @return A list of found books matching the given title.
   */
  public List<Book> findBooksByTitle(String title) {
    List<Book> foundBooks = new ArrayList<>();
    for (Book book : books) {
      if (book.getTitle().equalsIgnoreCase(title)) {
        foundBooks.add(book);
      }
    }
    return foundBooks;
  }

  /**
   * Counts the total number of books in the library.
   *
   * @return The total number of books available in the library.
   */
  public int countBooks() {
    return books.size();
  }
}
