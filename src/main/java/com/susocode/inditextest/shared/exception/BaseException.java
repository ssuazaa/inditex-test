package com.susocode.inditextest.shared.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

  private final String key;
  private final Integer statusCode;

  protected BaseException(String key, String message, int statusCode) {
    super(message);
    this.key = key;
    this.statusCode = statusCode;
  }

}
