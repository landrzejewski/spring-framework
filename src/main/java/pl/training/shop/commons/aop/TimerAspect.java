package pl.training.shop.commons.aop;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.training.shop.commons.aop.Timer.TimeUnit;

import static pl.training.shop.commons.aop.Timer.TimeUnit.NS;

@Aspect
@Component
@Log
public class TimerAspect {

    @Around("@annotation(timer)")
    public Object measure(ProceedingJoinPoint joinPoint, Timer timer) throws Throwable {
        var timeUnit = timer.timeUnit();
        var startTime = getTime(timeUnit);
        var result = joinPoint.proceed();
        var endTime = getTime(timeUnit);
        var totalTime = endTime - startTime;
        log.info("Method %s executes in %d %s".formatted(joinPoint.getSignature(), totalTime, timeUnit.name().toLowerCase()));
        return result;
    }

    private long getTime(TimeUnit timeUnit) {
        return timeUnit == NS ? System.nanoTime() : System.currentTimeMillis();
    }

}
