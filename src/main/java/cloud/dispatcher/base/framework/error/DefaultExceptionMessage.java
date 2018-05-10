package cloud.dispatcher.base.framework.error;

public final class DefaultExceptionMessage {

    private DefaultExceptionMessage() {}

    public static final ExceptionMessage ILLEGAL_ARGUMENT = ExceptionMessage.newInstance(100001, "Illegal argument, {}: {}");

    public static final ExceptionMessage INVALID_ARGUMENT = ExceptionMessage.newInstance(100002, "Invalid argument, {}: {}");

    public static final ExceptionMessage INCOMPATIBLE_OPERATOR = ExceptionMessage.newInstance(100003, "Incompatible operator, {}: {}");

    public static final ExceptionMessage DUPLICATED_KEY = ExceptionMessage.newInstance(100004, "Duplicated key, {}: {}");
}
