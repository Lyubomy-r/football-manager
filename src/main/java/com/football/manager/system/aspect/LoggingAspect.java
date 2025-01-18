package com.football.manager.system.aspect;

import com.football.manager.system.exception.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
  private final String aspectName = this.getClass().getSimpleName();

  @Pointcut("within(com.football.manager.system.controller..*)")
  public void restControllerMethods() {
  }

  @Pointcut("within(com.football.manager.system.service..*)")
  public void restServiceMethods() {
  }

  @Before("restServiceMethods()")
  public void logBeforeServiceMethods(JoinPoint joinPoint) {
    try {
      String className = joinPoint.getTarget().getClass().getSimpleName();
      String methodName = joinPoint.getSignature().getName();
      log.info("{} - Method called: {} - with args: {}.", className, methodName, Arrays.toString(joinPoint.getArgs()));
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  @AfterReturning(pointcut = "restServiceMethods()", returning = "result")
  public void logAfterServiceMethods(JoinPoint joinPoint, Object result) {
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      String returnType = result != null ? result.getClass().getSimpleName() : "void";
      log.info("{} -Method finished: {}. Return type: {}. Entity id ({})",
          mapClassMethod.get("className"),
          mapClassMethod.get("methodName"),
          returnType,
          getEntityIdFromResult(result));
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  @Around("restServiceMethods()")
  public Object logExecutionTimeServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      Object result = joinPoint.proceed();
      long executionTime = System.currentTimeMillis() - start;
      log.info("{} Method executed: {} in {} ms",
          mapClassMethod.get("className"),
          mapClassMethod.get("methodName"),
          executionTime);
      return result;
    } catch (Throwable throwable) {
      long executionTime = System.currentTimeMillis() - start;
      log.error("Method failed: {} after {} ms. Error: {}",
          joinPoint.getSignature(),
          executionTime,
          throwable.getMessage());
      throw throwable;
    }
  }

  @Before("restControllerMethods()")
  public void logBeforeControllerMethods(JoinPoint joinPoint) {
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      log.info("{} - Method called: {} - with args: {}.",
          mapClassMethod.get("className"),
          mapClassMethod.get("methodName"),
          Arrays.toString(joinPoint.getArgs()));
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
  public void logAfterControllerMethods(JoinPoint joinPoint, Object result) {
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      if (result instanceof ResponseEntity<?>) {
        Object resultBody = ((ResponseEntity<?>) result).getBody();
        String returnType = (resultBody != null) ? resultBody.getClass().getSimpleName() : "void";
        log.info("{} -Method finished: {} - with result: {}. Return type: {}.",
            mapClassMethod.get("className"),
            mapClassMethod.get("methodName"),
            result.getClass(),
            returnType);
      }
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  @AfterReturning(pointcut = "execution(* com.football.manager.system.exception.controllerAdvice.*.*(..))", returning = "exception")
  public void logAfterControllerAdvice(JoinPoint joinPoint, ResponseEntity<ErrorResponseDto> exception) {
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      log.info("{} - Class {} Method {} - threw an exception: {}", aspectName,
          mapClassMethod.get("className"),
          mapClassMethod.get("methodName"),
          Objects.requireNonNull(exception.getBody()).getMessage());
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  @AfterReturning(pointcut = "execution(* com.football.manager.system.exception.controllerAdvice.GlobalExceptionHandler.handleValidationExceptions(..))", returning = "exception")
  public void logAfterControllerAdviceValidationExceptions(JoinPoint joinPoint, ResponseEntity<ErrorResponseDto> exception) {
    try {
      Map<String, String> mapClassMethod = getClassNameAndMethod(joinPoint);
      log.info("{} - Class {} Method {} - threw an exception: {}", aspectName,
          mapClassMethod.get("className"),
          mapClassMethod.get("methodName"),
          Objects.requireNonNull(exception.getBody()).getDetails());
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
  }

  private Map<String, String> getClassNameAndMethod(JoinPoint joinPoint) {
    try {
      String className = joinPoint.getTarget().getClass().getSimpleName();
      String methodName = joinPoint.getSignature().getName();
      return Map.of("className", className,
          "methodName", methodName);
    } catch (Exception e) {
      log.error("{} - Failed to log controller method execution. Reason: {}", aspectName, e.getMessage());
    }
    return Map.of("className", "null",
        "methodName", "null");
  }

  private String getEntityIdFromResult(Object result) {
    try {
      if (result == null) {
        return "N/A";
      }
      Class<?> resultClass = result.getClass();
      Field idField = resultClass.getDeclaredField("id");
      idField.setAccessible(true);
      Object idValue = idField.get(result);

      return idValue != null ? idValue.toString() : "N/A";
    } catch (NoSuchFieldException e) {
      log.warn("Field 'id' not found in class {}.", result.getClass().getSimpleName());
    } catch (IllegalAccessException e) {
      log.error("Failed to access field 'id' in class {}. Reason: {}", result.getClass().getSimpleName(), e.getMessage());
    }
    return "N/A";
  }
}
