package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Two;

/**
 * Класс с намеренно некорректными значениями аннотации {@link Two}.
 * <p>
 * first = "" — пустая строка (ошибка),
 * second = -1 — отрицательное число (ошибка).
 */
@Two(first = "", second = -1)
public class InvalidTwoClass {
    // Класс используется только для теста аннотации
}

