package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Default;

/**
 * Пример класса, аннотированного с помощью {@link Default}.
 * Указывает, что типом по умолчанию для этого класса является {@link String}.
 */
@Default(value = String.class)
public class DefaultClass {
    /**
     * Пример поля, аннотированного как имеющее тип по умолчанию {@link Integer}.
     */
    @Default(value = Integer.class)
    public Object someField;
}
