package ru.Bochkarev.Annotations;

import java.lang.annotation.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания, должно ли поле (или класс) участвовать в формировании строкового представления объекта.
 * Может применяться к классам или полям.
 * По умолчанию включает элемент в строковое представление (YES).
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToString {

    /**
     * Перечисление, определяющее режим работы аннотации @ToString.
     * Используется для указания, должно ли поле включаться в строковое представление объекта.
     */
    enum ToStringMode {
        /**
         * Поле должно включаться в строковое представление.
         */
        YES,

        /**
         * Поле не должно включаться в строковое представление.
         */
        NO
    }

    /**
     * Указывает, включать ли элемент в строковое представление.
     * @return режим включения (YES или NO)
     */
    ToStringMode value() default ToStringMode.YES;
}