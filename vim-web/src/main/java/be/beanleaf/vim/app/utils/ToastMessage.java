package be.beanleaf.vim.app.utils;

public class ToastMessage {

  private MessageType type;
  private String messagePropertie;
  private boolean isCloseable;

  public ToastMessage(MessageType type, String messagePropertie, boolean isCloseable) {
    this.type = type;
    this.messagePropertie = messagePropertie;
    this.isCloseable = isCloseable;
  }

  public ToastMessage(MessageType type, String messagePropertie) {
    this(type, messagePropertie, true);
  }

  public MessageType getType() {
    return type;
  }

  public String getMessagePropertie() {
    return messagePropertie;
  }

  public boolean isCloseable() {
    return isCloseable;
  }
}