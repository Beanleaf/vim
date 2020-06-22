package be.beanleaf.vim.app.utils;

public class Feedbackmessage {

  private final MessageType type;
  private final String message;
  private final String stackTrace;
  private final boolean isValidation;
  private boolean isCloseable;

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