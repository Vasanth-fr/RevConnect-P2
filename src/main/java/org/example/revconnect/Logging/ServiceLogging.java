package org.example.revconnect.Logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ServiceLogging {

    private static final Logger logger =
            LogManager.getLogger(ServiceLogging.class);

    // All service-layer methods
    @Pointcut("execution(* org.example.revconnect.Service.*.*(..))")
    public void serviceLayer() {}

    // Before execution
    @Before("serviceLayer()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering {}() with args {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    // After successful execution
    @AfterReturning("serviceLayer()")
    public void logAfterSuccess(JoinPoint joinPoint) {
        logger.info("Exiting {}() successfully",
                joinPoint.getSignature().getName());
    }

    // On exception
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logAfterException(JoinPoint joinPoint, Throwable ex) {
        logger.warn("Exception in {}(): {}",
                joinPoint.getSignature().getName(),
                ex.getMessage());
    }
}
