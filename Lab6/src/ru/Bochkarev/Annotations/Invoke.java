package ru.Bochkarev.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация @Invoke.
 * Эта аннотация может применяться только к методам.
 * Она сохраняется во время выполнения программы (RUNTIME),
 * что позволяет использовать её через Reflection API.
 * Аннотация не содержит параметров (маркерная).
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoke {
}
