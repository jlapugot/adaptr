package io.github.jlapugot.adaptr;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class AdaptRInvocationHandler implements InvocationHandler {

    private final Map<String, Object> source;

    public AdaptRInvocationHandler(Map<String, Object> source) {
        this.source = source;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Handle Object methods
        if (method.getDeclaringClass() == Object.class) {
            return handleObjectMethod(proxy, method, args);
        }

        // Get the key to lookup in the map
        String key = getMapKey(method);

        // Return the value from the map
        return source.get(key);
    }

    private String getMapKey(Method method) {
        // Check if @Mapping annotation is present
        Mapping mapping = method.getAnnotation(Mapping.class);
        if (mapping != null) {
            return mapping.value();
        }

        // Derive key from method name
        String methodName = method.getName();

        // Handle getters: getName() -> name, getFullName() -> fullName
        if (methodName.startsWith("get") && methodName.length() > 3) {
            String key = methodName.substring(3);
            return Character.toLowerCase(key.charAt(0)) + key.substring(1);
        }

        // Handle boolean getters: isActive() -> active
        if (methodName.startsWith("is") && methodName.length() > 2) {
            String key = methodName.substring(2);
            return Character.toLowerCase(key.charAt(0)) + key.substring(1);
        }

        // Use method name as-is
        return methodName;
    }

    private Object handleObjectMethod(Object proxy, Method method, Object[] args) {
        String methodName = method.getName();

        return switch (methodName) {
            case "toString" -> "AdaptR Proxy: " + source.toString();
            case "hashCode" -> source.hashCode();
            case "equals" -> proxy == args[0];
            default -> throw new UnsupportedOperationException("Method " + methodName + " not supported");
        };
    }
}
