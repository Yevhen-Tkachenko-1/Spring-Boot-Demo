package com.yevhent.springbootdemo.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CollectionUtil {

    public static <P, R> List<R> toList(Iterable<P> iterable, Function<P, R> mapper) {
        return StreamSupport.stream(iterable.spliterator(), false).map(mapper).collect(Collectors.toList());
    }
}