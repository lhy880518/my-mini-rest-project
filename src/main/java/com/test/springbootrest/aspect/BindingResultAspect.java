package com.test.springbootrest.aspect;

import com.test.springbootrest.exception.HandleBindingResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Method;

@Aspect
@Component
public class BindingResultAspect {

    @Around("@annotation(com.test.springbootrest.exception.HandleBindingResult)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint)throws Throwable{
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        HandleBindingResult handleBindingResult = method.getAnnotation(HandleBindingResult.class);
        Class<? extends RuntimeException> value = handleBindingResult.value();

        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg : args){
            if(arg instanceof BindingResult){
                BindingResult result = (BindingResult) arg;
                if(result.hasErrors()){
                    throw value.getConstructor(new Class[]{String.class}).newInstance(result.getFieldErrors().get(0).getDefaultMessage());
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
