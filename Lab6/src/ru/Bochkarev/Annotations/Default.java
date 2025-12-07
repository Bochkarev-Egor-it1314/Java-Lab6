package ru.Bochkarev.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Пользовательская аннотация @Default.
 * Может применяться к типам (классам) и полям.
 * Доступна во время выполнения программы.
 * Содержит обязательное свойство {@code value}, указывающее тип по умолчанию.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Default {
    /**
     * Указывает тип по умолчанию, связанный с аннотированным элементом.
     *
     * @return Класс, представляющий тип по умолчанию.
     */
    Class<?> value();
}
