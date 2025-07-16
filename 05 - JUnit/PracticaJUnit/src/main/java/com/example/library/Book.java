package com.example.library;

/**
 * Represents a book with a title, author, and year of publication. This class provides methods for
 * accessing the book's information.
 */
public class Book {
  private final String title; // Title of the book
  private final String author; // Author of the book
  private final int publicationYear; // Year the book was published

  /**
   * Constructs a new book with the provided details.
   *
   * @param title Title of the book.
   * @param author Author of the book.
   * @param yearPublished Year the book was published.
   */
  public Book(String title, String author, int yearPublished) {
    this.title = title;
    this.author = author;
    this.publicationYear = yearPublished;
  }

  /**
   * Returns the title of the book.
   *
   * @return The title of the book.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Returns the author of the book.
   *
   * @return The author of the book.
   */
  public String getAuthor() {
    return author;
  }

  /**
   * Returns the year of publication of the book.
   *
   * @return The year of publication of the book.
   */
  public int getPublicationYear() {
    return publicationYear;
  }
}