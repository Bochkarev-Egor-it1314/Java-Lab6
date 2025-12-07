package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Invoke;
import java.lang.reflect.Method;

/**
 * Обработчик аннотации {@link Invoke}.
 * Сканирует указанный объект на наличие публичных методов,
 * помеченных аннотацией {@link Invoke}, и вызывает их.
 */
public class InvokeProcessor {

    /**
     * Автоматически вызывает все публичные методы объекта,
     * помеченные аннотацией {@link Invoke}.
     *
     * @param target Объект, методы которого нужно проверить и вызвать.
     * @throws IllegalArgumentException если входной объект равен {@code null}.
     */
    public static void processInvoke(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("Целевой объект не может быть null.");
        }

        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getMethods(); // только публичные методы (включая унаследованные)

        for (Method method : methods) {
            if (method.isAnnotationPresent(Invoke.class)) {
                try {
                    method.invoke(target);
                } catch (Exception e) {
                    System.err.println("Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}
