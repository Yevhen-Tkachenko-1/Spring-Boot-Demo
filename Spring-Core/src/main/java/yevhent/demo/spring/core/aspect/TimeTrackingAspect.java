package yevhent.demo.spring.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTrackingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTrackingAspect.class);

    @Pointcut("@annotation(TimeTrackable)")
    public void trackTime() {
    }

    @Around(value = "trackTime()")
    public Object countMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object returnValue = joinPoint.proceed();
            print(joinPoint, startTime);
            return returnValue;
        } catch (Throwable e) {
            print(joinPoint, startTime);
            throw e;
        }
    }

    private void print(JoinPoint joinPoint, long startTime) {
        long time = System.currentTimeMillis() - startTime;
        String message = LoggingAspect.methodToString(joinPoint) + " execution has taken " + time + " milliseconds.";
        LOGGER.info(message);
    }
}