package org.company.project.model.repository;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.company.project.common.jdbc.JDBCProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
//Aspect Oriented Programming Layer
@Component
@Scope("singleton")
@Lazy(value = false)
@Aspect
public class Rater {
    @Around(value = "execution(* org.company.project.model.repository.*Repository.*(..))")
    public <T> List<T>  moderate(ProceedingJoinPoint joinPoint) throws Throwable {
        List<T> tList = new ArrayList<>();

        GenericRepository genericRepository = (GenericRepository) joinPoint.getTarget();
        genericRepository.setConnection(JDBCProvider.getConnection(JDBCProvider.ORCLPDB1));

        if (joinPoint.getSignature().getName().equals("selectAll")){
            tList = (List<T>) joinPoint.proceed();
            genericRepository.close();
        } else if (joinPoint.getSignature().getName().equals("selectOne")) {
            tList = (List<T>) joinPoint.proceed();
            genericRepository.close();
        } else {
            joinPoint.proceed();
            genericRepository.commit();
            genericRepository.close();
        }
        return tList;
    }
}
