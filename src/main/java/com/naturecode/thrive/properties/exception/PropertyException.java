package com.naturecode.thrive.properties.exception;

public class PropertyException extends Exception {
  private static final long serialVersionUID = 1L;
  private final String message;

  public PropertyException(String errorMessage) {
    this.message = errorMessage;
  }

  @Override
  public String getMessage(){
    return this.message;
  }
}
