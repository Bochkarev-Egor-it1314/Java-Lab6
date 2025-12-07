package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.ToString;

/**
 * Пример класса с использованием аннотации @ToString.
 * Поле 'password' исключено из строкового представления.
 */
@ToString
public class ToStringClass {
    private String name;
    private int age;

    @ToString(ToString.ToStringMode.NO) // это поле не должно появляться в toString
    private String password;

    /**
     * Конструктор для создания объекта Person.
     *
     * @param name     имя
     * @param age      возраст
     * @param password пароль (скрыт в toString)
     */
    public ToStringClass(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

}
