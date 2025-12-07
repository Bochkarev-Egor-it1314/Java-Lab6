package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Validate;

/**
 * Пример класса, помеченного аннотацией {@link Validate}.
 * Указывает, что валидации подлежат классы String, Integer и Double.
 */
@Validate({String.class, Integer.class, Double.class})
public class ValidateClass {
    // Пустой класс — используется только для демонстрации аннотации
}