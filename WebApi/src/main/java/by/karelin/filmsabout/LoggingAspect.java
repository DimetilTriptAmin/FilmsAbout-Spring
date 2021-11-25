package by.karelin.filmsabout;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static Logger log = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* by.karelin.business.services.CommentService.getDeletedCommentPage(..))")
    public void getDeletedComments() {
    }

    @Before("getDeletedComments()")
    public void beforeAdvice() {
        log.info("Before get deleted comments");
    }

    @After("getDeletedComments()")
    public void afterAdvice() {
        log.info("After get deleted comments");
    }

    @AfterReturning(pointcut = "getDeletedComments()", returning = "value")
    public void afterReturningAdvice(Object value) {
        log.info("AfterReturning get deleted comments" + value.toString());
    }

    @AfterThrowing(pointcut = "getDeletedComments()", throwing = "e")
    public void inCaseOfExceptionThrowAdvice(ClassCastException e) {
        log.error(e.getMessage());
    }

}
