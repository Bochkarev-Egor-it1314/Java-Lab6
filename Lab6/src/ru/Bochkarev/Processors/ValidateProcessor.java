package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Validate;

import java.util.Arrays;

/**
 * Класс-обработчик аннотации {@link Validate}.
 * Анализирует аннотацию у переданного класса и выводит список указанных типов.
 */
public class ValidateProcessor {
    /**
     * Обрабатывает аннотацию {@link Validate} у заданного класса.
     * Выводит в консоль список классов, указанных в аннотации.
     * Выполняет проверку входных данных: класс не должен быть null и должен иметь аннотацию {@link Validate}.
     *
     * @param clazz класс, у которого проверяется наличие аннотации {@link Validate}
     * @throws IllegalArgumentException если clazz равен null или не аннотирован {@link Validate}
     */
    public static void processValidate(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Переданный класс не может быть null.");
        }

        if (!clazz.isAnnotationPresent(Validate.class)) {
            throw new IllegalArgumentException("Класс " + clazz.getSimpleName() + " не аннотирован @Validate.");
        }

        Validate validateAnnotation = clazz.getAnnotation(Validate.class);
        Class<?>[] classesToValidate = validateAnnotation.value();

        if (classesToValidate == null || classesToValidate.length == 0) {
            System.out.println("В аннотации @Validate не указаны классы для проверки.");
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " указывает следующие типы для валидации:");
            Arrays.stream(classesToValidate)
                    .map(Class::getSimpleName)
                    .forEach(System.out::println);
        }
    }
}
