package cloud.dispatcher.base.framework.context;

import org.springframework.context.ApplicationContext;

public final class ApplicationContextCapture {

    private ApplicationContextCapture() {}

    private static ApplicationContext context;

    public static void setApplicationContext(ApplicationContext ctx) {
        ApplicationContextCapture.context = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }
}
