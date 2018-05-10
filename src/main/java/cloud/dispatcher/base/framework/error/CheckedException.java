package cloud.dispatcher.base.framework.error;

import java.io.Serializable;

import org.slf4j.helpers.MessageFormatter;

import lombok.Getter;
import lombok.Setter;

public class CheckedException extends RuntimeException implements Serializable {

    @Getter @Setter private ExceptionMessage exceptionMessage;

    @Getter @Setter private Object[] parameter;

    @Override
    public String getMessage() {
        return MessageFormatter.arrayFormat(exceptionMessage.template, parameter).getMessage();
    }

    public CheckedException(ExceptionMessage exceptionMessage, Object... parameter) {
        this.parameter = parameter;
        this.exceptionMessage = exceptionMessage;
    }

    public CheckedException(ExceptionMessage exceptionMessage, Throwable throwable) {
        super(throwable);
        this.exceptionMessage = exceptionMessage;
    }
}