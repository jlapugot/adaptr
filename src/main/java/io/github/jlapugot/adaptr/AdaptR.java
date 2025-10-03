package io.github.jlapugot.adaptr;

import java.lang.reflect.Proxy;
import java.util.Map;

public class AdaptR {

    private AdaptR() {
        // Private constructor to prevent instantiation
    }

    @SuppressWarnings("unchecked")
    public static <T> T adapt(Object source, Class<T> targetInterface) {
        if (source == null) {
            throw new IllegalArgumentException("Source cannot be null");
        }

        if (targetInterface == null) {
            throw new IllegalArgumentException("Target interface cannot be null");
        }

        if (!targetInterface.isInterface()) {
            throw new IllegalArgumentException("Target must be an interface");
        }

        if (!(source instanceof Map)) {
            throw new IllegalArgumentException("Source must be a Map");
        }

        Map<String, Object> sourceMap = (Map<String, Object>) source;

        return (T) Proxy.newProxyInstance(
            targetInterface.getClassLoader(),
            new Class<?>[]{targetInterface},
            new AdaptRInvocationHandler(sourceMap)
        );
    }
}
