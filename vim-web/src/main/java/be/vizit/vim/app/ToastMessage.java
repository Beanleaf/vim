package be.vizit.vim.app;

import be.vizit.vim.domain.utilities.MessageType;

public class ToastMessage {

  private MessageType type;
  private String message;
  private boolean isCloseable;

  public ToastMessage(MessageType type, String message, boolean isCloseable) {
    this.type = type;
    this.message = message;
    this.isCloseable = isCloseable;
  }

  public ToastMessage(MessageType type, String message) {
    this(type, message, true);
  }

  public MessageType getType() {
    return type;
  }

  public String getMessage() {
    return message;
  }

  public boolean isCloseable() {
    return isCloseable;
  }
}