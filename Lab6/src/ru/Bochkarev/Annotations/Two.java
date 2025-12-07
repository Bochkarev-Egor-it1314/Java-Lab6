package ru.Bochkarev.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация {@code @Two}, которая может применяться к типам (классам),
 * доступна во время выполнения и содержит два обязательных параметра:
 * строку и целое число.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Two {
    /**
     * Первое обязательное свойство — строка.
     *
     * @return строковое значение
     */
    String first();

    /**
     * Второе обязательное свойство — целое число.
     *
     * @return числовое значение
     */
    int second();
}
