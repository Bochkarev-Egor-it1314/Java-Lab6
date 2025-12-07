package ru.Bochkarev.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания кешируемых областей класса.
 * Может применяться только к типам (классам).
 * Доступна во время выполнения программы.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    /**
     * Список кешируемых областей.
     * По умолчанию — пустой массив.
     * Массив строк с названиями кешируемых областей
     */
    String[] value() default {};
}
