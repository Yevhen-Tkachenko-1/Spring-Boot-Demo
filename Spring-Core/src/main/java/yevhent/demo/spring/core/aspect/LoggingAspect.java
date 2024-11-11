package yevhent.demo.spring.core.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
    private static int errorCount = 0;

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {
    }

    @Before("executeLogging()")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info(methodToString(joinPoint) + " is being called ...");
    }

    @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logAfterReturning(JoinPoint joinPoint, Object returnValue) {
        String message = methodToString(joinPoint) + " is completed successfully";
        if (returnValue != null) {
            message += String.format(" with return value: {%s}", returnValue);
        }
        LOGGER.info(message + ".");
    }

    @AfterThrowing("executeLogging()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        errorCount++;
        String message = methodToString(joinPoint) + " has failed. Total number of Exceptions is " + errorCount;
        LOGGER.info(message + ".");
    }

    public static String methodToString(JoinPoint joinPoint) {
        String message = "Method " + joinPoint.getSignature().getName() + "(";
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            message += (Arrays.stream(args).map(Object::toString).collect(Collectors.joining(", ")));
        }
        message += ")";
        return message;
    }
}