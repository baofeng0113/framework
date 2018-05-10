package cloud.dispatcher.base.framework.monitor;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

@Aspect
public class PerformanceMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitor.class);

    @Pointcut("execution(* cloud.dispatcher..*.*(..))")
    public void performancePointcut() {}

    @Around("performancePointcut()")
    public Object performanceProfile(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTimeMillis = System.currentTimeMillis();
        String method = joinPoint.getTarget().getClass().getSimpleName() +
                "." + joinPoint.getSignature().getName();
        try {
            return joinPoint.proceed();
        } finally {
            long closeTimeMillis = System.currentTimeMillis();
            List<Object> args = Lists.newArrayList();
            if(joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                for (int i = 0; i < joinPoint.getArgs().length; i++) {
                    args.add(String.valueOf(joinPoint.getArgs()[i]));
                }
            }

            LOGGER.debug("Invoke {}, args: {}, cost: {}ms", method, args,
                    closeTimeMillis - beginTimeMillis);
        }
    }
}
