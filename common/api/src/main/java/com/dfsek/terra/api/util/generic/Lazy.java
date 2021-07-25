package com.dfsek.terra.api.util.generic;

import java.util.function.Supplier;

public final class Lazy<T> {
    private final Supplier<T> valueSupplier;
    private T value = null;
    private boolean got = false;

    private Lazy(Supplier<T> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }

    public static <T> Lazy<T> lazy(Supplier<T> valueSupplier) {
        return new Lazy<>(valueSupplier);
    }

    public T value() {
        if(!got && value == null) {
            got = true;
            value = valueSupplier.get();
        }
        return value;
    }
}
