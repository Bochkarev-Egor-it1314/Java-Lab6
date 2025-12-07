package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.ToString;

import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * Утилитный класс для формирования строкового представления объекта на основе аннотации @ToString.
 */
public class ToStringProcessor {

    /**
     * Формирует строковое представление объекта, включая только те поля,
     * которые либо не проаннотированы @ToString, либо проаннотированы как @ToString(YES).
     * Если класс аннотирован как @ToString(NO), возвращается стандартное Object.toString().
     *
     * @param obj объект, для которого формируется строка
     * @return строковое представление объекта
     * @throws IllegalArgumentException если передан null
     */
    public static String processToString(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null.");
        }

        Class<?> clazz = obj.getClass();

        // Проверяем аннотацию на классе
        if (clazz.isAnnotationPresent(ToString.class)) {
            ToString classAnnotation = clazz.getAnnotation(ToString.class);
            if (classAnnotation.value() == ToString.ToStringMode.NO) {
                return obj.toString(); // стандартное поведение
            }
        }

        StringJoiner joiner = new StringJoiner(", ", clazz.getSimpleName() + "{", "}");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            // По умолчанию поле включается
            boolean include = true;

            if (field.isAnnotationPresent(ToString.class)) {
                ToString fieldAnnotation = field.getAnnotation(ToString.class);
                if (fieldAnnotation.value() == ToString.ToStringMode.NO) {
                    include = false;
                }
            }

            if (include) {
                try {
                    Object value = field.get(obj);
                    joiner.add(field.getName() + "=" + (value == null ? "null" : value.toString()));
                } catch (IllegalAccessException e) {
                    joiner.add(field.getName() + "=<доступ запрещён>");
                }
            }
        }

        return joiner.toString();
    }
}
