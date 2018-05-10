package cloud.dispatcher.base.framework.error;

import java.io.Serializable;

public final class ExceptionMessage implements Serializable {

    public final int code;

    public final String template;

    public static ExceptionMessage newInstance(int code, String template) {
        return new ExceptionMessage(code, template);
    }

    private ExceptionMessage(int code, String template) {
        this.code = code;
        this.template = template;
    }
}
