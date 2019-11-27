package be.vizit.vim.domain.utilities;

public class Feedbackmessage {

  private MessageType type;
  private String message;
  private String stackTrace;
  private boolean isCloseable;

  public Feedbackmessage(MessageType type, String message, String stackTrace,
      boolean isCloseable) {
    this.type = type;
    this.message = message;
    this.stackTrace = stackTrace;
    this.isCloseable = isCloseable;
  }

  public Feedbackmessage(MessageType type, String message, boolean isCloseable) {
    this(type, message, null, isCloseable);
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
}