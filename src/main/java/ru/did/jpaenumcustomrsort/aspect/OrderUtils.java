package ru.diasoft.micro.service.orderable;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberExpression;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

import static org.apache.commons.lang3.ObjectUtils.anyNull;

public class OrderUtils {
    private OrderUtils() {
    }

    public static <T extends Enum<T>> Pageable enumPageable(EnumPath<T> enumPath, Map<String, Integer> sortOrders, Pageable pageable) {
        Order order = pageable.getSort().stream().findFirst().map(x -> x.isAscending() ? Order.ASC : Order.DESC).orElse(Order.ASC);
        String field = pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse(null);
        if (anyNull(order, field))
            return pageable;

        OrderSpecifier<Integer> orderSpecifier = provideStatusOrder(enumPath, sortOrders, order);
        QSort qSort = new QSort(orderSpecifier);
        return QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), qSort);
    }

    private static <T extends Enum<T>> OrderSpecifier<Integer> provideStatusOrder(EnumPath<T> enumPath, Map<String, Integer> sortOrders, Order order) {
        CaseBuilder.Cases<Integer, NumberExpression<Integer>> cases = null;
        for (Map.Entry<String, Integer> e : sortOrders.entrySet()) {
            if (cases != null)
                cases = cases.when(enumPath.stringValue().eq(e.getKey())).then(e.getValue());
            else
                cases = new CaseBuilder().when(enumPath.stringValue().eq(e.getKey())).then(e.getValue());
        }
        if (Objects.nonNull(cases)) {
            NumberExpression<Integer> expression = cases.otherwise(Integer.MAX_VALUE);
            return new OrderSpecifier<>(order, expression);
        }
        return null;
    }
}
