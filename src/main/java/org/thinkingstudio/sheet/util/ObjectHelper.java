package org.thinkingstudio.sheet.util;

public class ObjectHelper {
    public static <S, T> T unsafeCast(S src) {
        // noinspection unchecked
        return (T) src;
    }
}
