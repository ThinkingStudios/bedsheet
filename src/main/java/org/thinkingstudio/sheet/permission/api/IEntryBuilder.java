package org.thinkingstudio.sheet.permission.api;

@SuppressWarnings("unused")
public interface IEntryBuilder<T> {
    T build(String name);
}
