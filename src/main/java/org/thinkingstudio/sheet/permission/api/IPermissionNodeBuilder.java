package org.thinkingstudio.sheet.permission.api;

@SuppressWarnings("unused")
public interface IPermissionNodeBuilder<T> extends IEntryBuilder<T> {
    IPermissionNodeBuilder<T> level(int level);
}
