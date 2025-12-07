package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Cache;

/**
 * Пример класса, помеченного аннотацией @Cache.
 * Указывает, что кешируются области: "users", "profiles", "sessions".
 */
@Cache({"users", "profiles", "sessions"})
public class CacheClass {
    // Пустой класс для демонстрации аннотации
}
