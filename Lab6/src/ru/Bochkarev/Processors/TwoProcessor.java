package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Two;

/**
 * Утилитный класс для обработки аннотации {@link Two}.
 */
public class TwoProcessor {

    /**
     * Проверяет аннотацию @Two на корректность и выводит её параметры.
     *
     * @param clazz класс с аннотацией @Two
     * @throws IllegalArgumentException если:
     *         - clazz == null
     *         - first пустой или null
     *         - second < 0
     */
    public static void processTwo(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null.");
        }

        if (!clazz.isAnnotationPresent(Two.class)) {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Two.");
            return;
        }

        Two ann = clazz.getAnnotation(Two.class);

        if (ann.first() == null || ann.first().isEmpty()) {
            throw new IllegalArgumentException("Свойство 'first' не может быть пустым.");
        }
        if (ann.second() < 0) {
            throw new IllegalArgumentException("Свойство 'second' не может быть отрицательным.");
        }

        // Если всё ок — выводим
        System.out.println("Аннотация @Two найдена:");
        System.out.println("  first = \"" + ann.first() + "\"");
        System.out.println("  second = " + ann.second());
    }
}
