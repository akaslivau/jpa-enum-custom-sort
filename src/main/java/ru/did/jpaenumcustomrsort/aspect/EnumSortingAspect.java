package ru.did.jpaenumcustomrsort.aspect;

import com.querydsl.core.types.dsl.EnumPath;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.persistence.Enumerated;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

@Aspect
@Component
public class EnumSortingAspect {

    @Around("@annotation(ru.did.jpaenumcustomrsort.aspect.EnumSorting)")
    public Object modifyPageableIfEnumSort(ProceedingJoinPoint point) throws Throwable {
        Pageable pageable = getPageable(point);
        if (Objects.isNull(pageable))
            return point.proceed();

        String sortByField = pageable.getSort().stream().map(Sort.Order::getProperty).findFirst().orElse(null);
        if (trimToEmpty(sortByField).isEmpty())
            return point.proceed();

        Class<?> entityClass = getEntityClass(point);
        Field classField = getClassField(sortByField, entityClass);
        if (Objects.isNull(classField))
            return point.proceed();

        Map<String, Integer> sortOrders = getSortOrders(classField);
        if (sortOrders.isEmpty())
            return point.proceed();

        EnumPath<?> enumPath = getEnumPath(entityClass, sortByField);
        if (Objects.isNull(enumPath))
            return point.proceed();

        Pageable modifiedPageable = OrderUtils.enumPageable(enumPath, sortOrders, pageable);
        Object[] modifiedArgs = modifyArgs(point.getArgs(), modifiedPageable);

        Page<?> res = (Page<?>)point.proceed(modifiedArgs);
        return new PageImpl<>(res.getContent(), pageable, res.getTotalElements());
    }

    private static Pageable getPageable(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        for (Object arg : args) {
            if (arg instanceof Pageable) {
                return (Pageable) arg;
            }
        }
        return null;
    }

    private static Class<?> getEntityClass(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        EnumSorting myAnnotation = method.getAnnotation(EnumSorting.class);
        return myAnnotation.className();
    }

    private static Field getClassField(String sortByField, Class<?> entityClass) {
        return FieldUtils.getFieldsListWithAnnotation(entityClass, Enumerated.class)
                .stream().filter(x -> x.getName().equalsIgnoreCase(sortByField)).findFirst().orElse(null);
    }

    private Object[] modifyArgs(Object[] args, Pageable modifiedPageable) {
        Object[] res = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object originalArg = args[i];
            if (originalArg instanceof Pageable) {
                res[i] = modifiedPageable;
            } else {
                res[i] = originalArg;
            }
        }
        return res;
    }

    private static Map<String, Integer> getSortOrders(Field classField) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object[] enums = classField.getType().getEnumConstants();
        Map<String, Integer> sortOrders = new HashMap<>();
        try {
            for (Object enumItem : enums) {
                Method mName = classField.getType().getMethod("name", null);
                String name = mName.invoke(enumItem, null).toString();

                Method mGetSortOrder = classField.getType().getMethod(OrderlyEnum.ORDER_METHOD, null);
                Integer sortOrder = (Integer) mGetSortOrder.invoke(enumItem, null);
                sortOrders.put(name, sortOrder);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new HashMap<>();
        }
        return sortOrders;
    }

    private static EnumPath<?> getEnumPath(Class<?> entityClass, String sortByField) throws ClassNotFoundException, IllegalAccessException {
        String shortName = entityClass.getName().substring(entityClass.getName().lastIndexOf(".") + 1);
        Class<?> qClass = Class.forName(entityClass.getName().replace(shortName, "Q" + shortName));

        Object root = FieldUtils.getField(qClass, shortName.substring(0, 1).toLowerCase() + shortName.substring(1)).get(null);
        Object enumPath = FieldUtils.getField(qClass, sortByField).get(root);
        if (enumPath instanceof EnumPath) {
            return (EnumPath<?>) enumPath;
        }
        return null;
    }
}