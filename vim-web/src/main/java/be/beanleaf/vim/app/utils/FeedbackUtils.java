package be.beanleaf.vim.app.utils;

import be.beanleaf.vim.domain.utilities.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FeedbackUtils {

  private final static Logger logger = LoggerFactory.getLogger(FeedbackUtils.class);

  private FeedbackUtils() {
  }

  public static Feedbackmessage createMessage(MessageType type, String message,
      boolean isValidation) {
    return new Feedbackmessage(type, message, isValidation, true);
  }

  public static Feedbackmessage createMessage(Exception e) {
    return createMessage(e, false);
  }

  public static Feedbackmessage createMessage(Exception e, boolean fullStacktrace) {
    Throwable rootCause = getRootCause(e);
    String stack = rootCause != null ?
        ExceptionUtils.getStackTrace(rootCause) : ExceptionUtils.getStackTrace(e);

    boolean isValidationException = e instanceof ValidationException;
    if (!isValidationException) {
      logger.error(ExceptionUtils.getStackTrace(e));
    }
    return new Feedbackmessage(
        isValidationException ? MessageType.WARNING : MessageType.ERROR,
        getMessage(e, rootCause, isValidationException),
        isValidationException ? null : fullStacktrace ? stack : StringUtils.abbreviate(stack, 500),
        isValidationException, false);
  }

  private static Throwable getRootCause(Throwable t) {
    Throwable rootCause = ExceptionUtils.getRootCause(t);
    return rootCause != null ? rootCause : t;
  }

  private static String getMessage(Throwable t, Throwable rootCause,
      boolean isValidationException) {
    Throwable cause = rootCause != null ? rootCause : t;
    return isValidationException ? cause.getMessage() : ExceptionUtils.getMessage(cause);
  }
}
