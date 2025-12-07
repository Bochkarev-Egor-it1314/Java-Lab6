package ru.Bochkarev.Annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания списка классов, подлежащих валидации.
 * Может применяться к типам (классам) или другим аннотациям.
 * Доступна во время выполнения программы.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
    /**
     * Обязательное свойство, содержащее массив классов для валидации.
     *
     * @return массив классов, которые должны быть проверены
     */
    Class<?>[] value();
}
