package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Cache;

import java.lang.reflect.AnnotatedElement;

/**
 * Обработчик аннотации @Cache.
 * Анализирует переданный класс и выводит список кешируемых областей.
 */
public class CacheProcessor {

    /**
     * Выводит список кешируемых областей из аннотации @Cache для указанного класса.
     * "Clazz" класс, для которого нужно проверить аннотацию
     * "IllegalArgumentException" если переданный класс null
     */
    public static void processCacheAnnotation(Class<?> clazz) {
        if (clazz == null) {
            System.out.println("Ошибка: передан null вместо класса.");
            return;
        }

        if (clazz.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = clazz.getAnnotation(Cache.class);
            String[] cachedAreas = cacheAnnotation.value();

            if (cachedAreas.length == 0) {
                System.out.println("Класс " + clazz.getSimpleName() + " помечен аннотацией @Cache, но список кешируемых областей пуст.");
            } else {
                System.out.println("Найдены кешируемые области для класса " + clazz.getSimpleName() + ":");
                for (String area : cachedAreas) {
                    System.out.println("   - " + area);
                }
            }
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не помечен аннотацией @Cache.");
        }
    }
}