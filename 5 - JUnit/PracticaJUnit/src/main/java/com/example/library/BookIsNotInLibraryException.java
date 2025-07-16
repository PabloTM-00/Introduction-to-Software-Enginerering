package com.example.library;

/**
 * Exception to be raised when an instance of {@link Book} is not within a {@link Library}
 */
public class BookIsNotInLibraryException extends RuntimeException {

  public BookIsNotInLibraryException(String errorMessage) {
    super(errorMessage);
  }
}
