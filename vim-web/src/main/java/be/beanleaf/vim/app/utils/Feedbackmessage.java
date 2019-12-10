package be.beanleaf.vim.app.utils;

public class Feedbackmessage {

  private MessageType type;
  private String message;
  private String stackTrace;
  private boolean isCloseable;
  private boolean isValidation;

  public Feedbackmessage(MessageType type, String message, String stackTrace, boolean isValidation,
      boolean isCloseable) {
    this.type = type;
    this.message = message;
    this.stackTrace = stackTrace;
    this.isValidation = isValidation;
    this.isCloseable = isCloseable;
  }

  public Feedbackmessage(MessageType type, String message, boolean isValidation,
      boolean isCloseable) {
    this(type, message, null, isValidation, isCloseable);
  }

  public MessageType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public String getStackTrace() {
    return stackTrace;
  }

  public boolean isCloseable() {
    return isCloseable;
  }

  public Feedbackmessage setCloseable(boolean closeable) {
    isCloseable = closeable;
    return this;
  }

  public boolean isValidation() {
    return isValidation;
  }
}