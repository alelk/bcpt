package com.alelk.bcpt.model.util;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Util
 *
 * Created by Alex Elkin on 11.10.2017.
 */
public class Util {
    public static String toString(List<?> list) {
        return list == null ? null : '[' + list.stream().map(Objects::toString).collect(Collectors.joining(", ")) + ']';
    }

    public static String toString(Set<?> set) {
        return set == null ? null : '[' + set.stream().map(Objects::toString).collect(Collectors.joining(", ")) + ']';
    }
}
