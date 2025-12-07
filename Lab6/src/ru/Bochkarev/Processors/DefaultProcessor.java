package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Default;

import java.lang.reflect.Field;

/**
 * Класс для обработки аннотаций {@link Default}.
 * Содержит методы для извлечения и отображения информации о типах по умолчанию.
 */
public class DefaultProcessor {

    /**
     * Выводит имя класса по умолчанию, указанного в аннотации {@link Default} над заданным классом.
     *
     * @param clazz Класс, который может быть аннотирован {@link Default}.
     * @throws IllegalArgumentException если класс не аннотирован аннотацией {@link Default}.
     */
    public static void processDefaultType(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null.");
        }
        if (clazz.isAnnotationPresent(Default.class)) {
            Default annotation = clazz.getAnnotation(Default.class);
            System.out.println("Тип по умолчанию для класса " + clazz.getSimpleName() +
                    ": " + annotation.value().getSimpleName());
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Default.");
        }
    }

    /**
     * Выводит имя класса по умолчанию, указанного в аннотации {@link Default} над заданным полем.
     *
     * @param clazz Класс, содержащий поле.
     * @param fieldName Имя поля.
     * @throws IllegalArgumentException если поле не существует или не аннотировано {@link Default}.
     * @throws NullPointerException если переданы null-аргументы.
     */
    public static void processDefaultField(Class<?> clazz, String fieldName) {
        if (clazz == null || fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Некорректные аргументы: класс и имя поля не могут быть null или пустыми.");
        }

        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(Default.class)) {
                Default annotation = field.getAnnotation(Default.class);
                System.out.println("Тип по умолчанию для поля '" + fieldName + "' в классе " +
                        clazz.getSimpleName() + ": " + annotation.value().getSimpleName());
            } else {
                System.out.println("Поле '" + fieldName + "' в классе " + clazz.getSimpleName() +
                        " не аннотировано @Default.");
            }
        } catch (NoSuchFieldException e) {
            System.out.println("Поле '" + fieldName + "' не найдено в классе " + clazz.getSimpleName() + ".");
        }
    }
}
