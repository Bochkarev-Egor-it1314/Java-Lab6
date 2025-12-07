# Java-Lab6

# Отчет по работе: Реализация задач на Java
Бочкарёв Егор ИТ-13,14

## Общее описание
Данная лабораторная работа демонстрирует применение аннотаций Java и фреймворка тестирования JUnit для создания гибкой и расширяемой системы обработки метаданных. Все задачи реализованы в едином проекте с модульной структурой, включая обработчики аннотаций через Reflection API и комплексное тестирование.

## Структура проекта

src/

└── ru.Bochkarev/

│   ├── Annotations/

│   │   ├── @Cache.java

│   │   ├── @Default.java

│   │   ├── @Invoke.java

│   │   ├── @ToString.java

│   │   ├── @Two.java

│   │   └── @Validate.java

│   ├── Classes/

│   │   ├── CacheClass.java

│   │   ├── DefaultClass.java

│   │   ├── InvalidTwoClass.java

│   │   ├── InvokeClass.java

│   │   ├── ToStringClass.java

│   │   ├── TwoClass.java

│   │   └── ValidateClass.java

│   ├── Main/

│   │   ├── Check.java

│   │   └── Main.java

│   ├── Processors/

│   │   ├── CacheProcessor.java

│   │   ├── DefaultProcessor.java

│   │   ├── InvokeProcessor.java

│   │   ├── ToStringProcessor.java

│   │   ├── TwoProcessor.java

│   │   └── ValidateProcessor.java

│   └── Tests/

│       ├── InvokeTest.java

│       └── TwoTest.java

└── pom.xml

## Детальный анализ методов

### Задание 1.1 (@Invoke)

**<ins>Задача:</ins>**

Разработайте аннотацию @Invoke, со следующими характеристиками:
+ Целью может быть только МЕТОД
+ Доступна во время исполнения программы
+ Не имеет свойств

Создайте класс, содержащий несколько методов, и проаннотируйте хотя бы один из них аннотацией @Invoke.

Реализуйте обработчик (через Reflection API), который находит методы, отмеченные аннотацией @Invoke, и вызывает их автоматически. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Invoke;

/**
 * Класс, содержащий методы, часть из которых помечена аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
 * Используется для демонстрации автоматического вызова аннотированных методов.
 */
public class InvokeClass {

    private boolean flagOne = false;
    private boolean flagTwo = false;
    private boolean privateFlag = false;

    /**
     * Метод, помеченный аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
     * Устанавливает флаг {@code flagOne} в {@code true}.
     */
    @Invoke
    public void methodOne() {
        System.out.println("Вызван methodOne() — помечен @Invoke");
        this.flagOne = true;
    }

    /**
     * Обычный метод без аннотации. Устанавливает флаг {@code flagTwo}.
     */
    public void methodTwo() {
        System.out.println("Вызван methodTwo() — без аннотации");
        this.flagTwo = true;
    }

    /**
     * Приватный метод с аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
     * Устанавливает флаг {@code privateFlag}.
     * Не будет вызван автоматически, если обработчик не настроен на работу с приватными методами.
     */
    @Invoke
    private void privateMethod() {
        System.out.println("Приватный метод с @Invoke");
        this.privateFlag = true;
    }

    // Геттеры для тестирования состояния
    public boolean isFlagOneSet() { return flagOne; }
    public boolean isFlagTwoSet() { return flagTwo; }
    public boolean isPrivateFlagSet() { return privateFlag; }
}
```

```
package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Invoke;
import java.lang.reflect.Method;

/**
 * Обработчик аннотации {@link Invoke}.
 * Сканирует указанный объект на наличие публичных методов,
 * помеченных аннотацией {@link Invoke}, и вызывает их.
 */
public class InvokeProcessor {

    /**
     * Автоматически вызывает все публичные методы объекта,
     * помеченные аннотацией {@link Invoke}.
     *
     * @param target Объект, методы которого нужно проверить и вызвать.
     * @throws IllegalArgumentException если входной объект равен {@code null}.
     */
    public static void processInvoke(Object target) {
        if (target == null) {
            throw new IllegalArgumentException("Целевой объект не может быть null.");
        }

        Class<?> clazz = target.getClass();
        Method[] methods = clazz.getMethods(); // только публичные методы (включая унаследованные)

        for (Method method : methods) {
            if (method.isAnnotationPresent(Invoke.class)) {
                try {
                    method.invoke(target);
                } catch (Exception e) {
                    System.err.println("Ошибка при вызове метода " + method.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Invoke ~

Вызван methodOne() — помечен @Invoke

Ручной вызов methodTwo():

Вызван methodTwo() — без аннотации

Методы с аннотацией @Invoke были автоматически вызваны
***

### Задание 1.2 (@Default)

**<ins>Задача:</ins>**

Разработайте аннотацию @Default, со следующими характеристиками:
+ Целью может быть ТИП или ПОЛЕ
+ Доступна во время исполнения программы
+ Имеет обязательное свойство value типа Class

Проаннотируйте какой-либо класс данной аннотацией, указав тип по умолчанию.

Напишите обработчик, который выводит имя указанного класса по умолчанию. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
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
```

```
package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Default;

import java.lang.reflect.Field;

/**
 * Класс для обработки аннотаций {@link Default}.
 * Содержит методы для извлечения и отображения информации о типах по умолчанию.
 */
public class DefaultProcessor {

    /**
     * Выводит имя класса по умолчанию, указанного в аннотации {@link Default} над заданным классом.
     *
     * @param clazz Класс, который может быть аннотирован {@link Default}.
     * @throws IllegalArgumentException если класс не аннотирован аннотацией {@link Default}.
     */
    public static void processDefaultType(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null.");
        }
        if (clazz.isAnnotationPresent(Default.class)) {
            Default annotation = clazz.getAnnotation(Default.class);
            System.out.println("Тип по умолчанию для класса " + clazz.getSimpleName() +
                    ": " + annotation.value().getSimpleName());
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Default.");
        }
    }

    /**
     * Выводит имя класса по умолчанию, указанного в аннотации {@link Default} над заданным полем.
     *
     * @param clazz Класс, содержащий поле.
     * @param fieldName Имя поля.
     * @throws IllegalArgumentException если поле не существует или не аннотировано {@link Default}.
     * @throws NullPointerException если переданы null-аргументы.
     */
    public static void processDefaultField(Class<?> clazz, String fieldName) {
        if (clazz == null || fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Некорректные аргументы: класс и имя поля не могут быть null или пустыми.");
        }

        try {
            Field field = clazz.getDeclaredField(fieldName);
            if (field.isAnnotationPresent(Default.class)) {
                Default annotation = field.getAnnotation(Default.class);
                System.out.println("Тип по умолчанию для поля '" + fieldName + "' в классе " +
                        clazz.getSimpleName() + ": " + annotation.value().getSimpleName());
            } else {
                System.out.println("Поле '" + fieldName + "' в классе " + clazz.getSimpleName() +
                        " не аннотировано @Default.");
            }
        } catch (NoSuchFieldException e) {
            System.out.println("Поле '" + fieldName + "' не найдено в классе " + clazz.getSimpleName() + ".");
        }
    }
}
```

**<ins>Вывод на экран:</ins>**

~Демонстрация работы аннотации @Default~

Тип по умолчанию для класса DefaultClass: String

Тип по умолчанию для поля 'someField' в классе DefaultClass: Integer
***

### Задание 1.3 (@ToString)

**<ins>Задача:</ins>**

Разработайте аннотацию @ToString, со следующими характеристиками:
+ Целью может быть ТИП или ПОЛЕ
+ Доступна во время исполнения программы
+ Имеет необязательное свойство valuec двумя вариантами значений: YES или NO
+ Значение свойства по умолчанию: YES

Проаннотируйте класс аннотацией @ToString, а одно из полей – с @ToString(Mode.NO).
  
Создайте метод, который формирует строковое представление объекта, учитывая только те поля, где @ToString имеет значение YES. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
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
```

```
package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.ToString;

import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * Утилитный класс для формирования строкового представления объекта на основе аннотации @ToString.
 */
public class ToStringProcessor {

    /**
     * Формирует строковое представление объекта, включая только те поля,
     * которые либо не проаннотированы @ToString, либо проаннотированы как @ToString(YES).
     * Если класс аннотирован как @ToString(NO), возвращается стандартное Object.toString().
     *
     * @param obj объект, для которого формируется строка
     * @return строковое представление объекта
     * @throws IllegalArgumentException если передан null
     */
    public static String processToString(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null.");
        }

        Class<?> clazz = obj.getClass();

        // Проверяем аннотацию на классе
        if (clazz.isAnnotationPresent(ToString.class)) {
            ToString classAnnotation = clazz.getAnnotation(ToString.class);
            if (classAnnotation.value() == ToString.ToStringMode.NO) {
                return obj.toString(); // стандартное поведение
            }
        }

        StringJoiner joiner = new StringJoiner(", ", clazz.getSimpleName() + "{", "}");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            // По умолчанию поле включается
            boolean include = true;

            if (field.isAnnotationPresent(ToString.class)) {
                ToString fieldAnnotation = field.getAnnotation(ToString.class);
                if (fieldAnnotation.value() == ToString.ToStringMode.NO) {
                    include = false;
                }
            }

            if (include) {
                try {
                    Object value = field.get(obj);
                    joiner.add(field.getName() + "=" + (value == null ? "null" : value.toString()));
                } catch (IllegalAccessException e) {
                    joiner.add(field.getName() + "=<доступ запрещён>");
                }
            }
        }

        return joiner.toString();
    }
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @ToString ~

Введите имя: Ivan

Введите возраст: 19

Введите пароль: qwerty

--- Стандартный toString (Object):

ru.Bochkarev.Classes.ToStringClass@776ec8df

--- Кастомный toString с @ToString:

ToStringClass{name=Ivan, age=19}
***

### Задание 1.4 (@Validate)

**<ins>Задача:</ins>**

Разработайте аннотацию @Validate, со следующими характеристиками:
+ Целью может быть ТИП или АННОТАЦИЯ
+ Доступна во время исполнения программы
+ Имеет обязательное свойство value, типа Class[]

Проаннотируйте класс аннотацией @Validate, передав список типов для проверки.

Реализуйте обработчик, который выводит, какие классы указаны в аннотации. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Validate;

/**
 * Пример класса, помеченного аннотацией {@link Validate}.
 * Указывает, что валидации подлежат классы String, Integer и Double.
 */
@Validate({String.class, Integer.class, Double.class})
public class ValidateClass {
    // Пустой класс — используется только для демонстрации аннотации
}
```

```
package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Validate;

import java.util.Arrays;

/**
 * Класс-обработчик аннотации {@link Validate}.
 * Анализирует аннотацию у переданного класса и выводит список указанных типов.
 */
public class ValidateProcessor {
    /**
     * Обрабатывает аннотацию {@link Validate} у заданного класса.
     * Выводит в консоль список классов, указанных в аннотации.
     * Выполняет проверку входных данных: класс не должен быть null и должен иметь аннотацию {@link Validate}.
     *
     * @param clazz класс, у которого проверяется наличие аннотации {@link Validate}
     * @throws IllegalArgumentException если clazz равен null или не аннотирован {@link Validate}
     */
    public static void processValidate(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Переданный класс не может быть null.");
        }

        if (!clazz.isAnnotationPresent(Validate.class)) {
            throw new IllegalArgumentException("Класс " + clazz.getSimpleName() + " не аннотирован @Validate.");
        }

        Validate validateAnnotation = clazz.getAnnotation(Validate.class);
        Class<?>[] classesToValidate = validateAnnotation.value();

        if (classesToValidate == null || classesToValidate.length == 0) {
            System.out.println("В аннотации @Validate не указаны классы для проверки.");
        } else {
            System.out.println("Класс " + clazz.getSimpleName() + " указывает следующие типы для валидации:");
            Arrays.stream(classesToValidate)
                    .map(Class::getSimpleName)
                    .forEach(System.out::println);
        }
    }
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Validate ~

Класс ValidateClass указывает следующие типы для валидации:

String

Integer

Double
***

### Задание 1.5 (@Two)

**<ins>Задача:</ins>**

Разработайте аннотацию @Two, со следующими характеристиками:
+ Целью может быть ТИП
+ Доступна во время исполнения программы
+ Имеет два обязательных свойства: first типа String и second типа int

Проаннотируйте какой-либо класс аннотацией @Two, передав строковое и числовое значения.

Реализуйте обработчик, который считывает и выводит значения этих свойств. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Two;

/**
 * Пример класса, аннотированного с помощью {@link Two}.
 */
@Two(first = "Пример строки", second = 42)
public class TwoClass {
    // Пустой класс для демонстрации аннотации
}
```

```
package ru.Bochkarev.Processors;

import ru.Bochkarev.Annotations.Two;

/**
 * Утилитный класс для обработки аннотации {@link Two}.
 */
public class TwoProcessor {

    /**
     * Проверяет аннотацию @Two на корректность и выводит её параметры.
     *
     * @param clazz класс с аннотацией @Two
     * @throws IllegalArgumentException если:
     *         - clazz == null
     *         - first пустой или null
     *         - second < 0
     */
    public static void processTwo(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Класс не может быть null.");
        }

        if (!clazz.isAnnotationPresent(Two.class)) {
            System.out.println("Класс " + clazz.getSimpleName() + " не аннотирован @Two.");
            return;
        }

        Two ann = clazz.getAnnotation(Two.class);

        if (ann.first() == null || ann.first().isEmpty()) {
            throw new IllegalArgumentException("Свойство 'first' не может быть пустым.");
        }
        if (ann.second() < 0) {
            throw new IllegalArgumentException("Свойство 'second' не может быть отрицательным.");
        }

        // Если всё ок — выводим
        System.out.println("Аннотация @Two найдена:");
        System.out.println("  first = \"" + ann.first() + "\"");
        System.out.println("  second = " + ann.second());
    }
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Two ~

Выберите действие:

1. Обработать TwoClass
   
2. Выход
   
Введите номер действия: 1

Аннотация @Two найдена:

  first = "Пример строки"
  
  second = 42

Выберите действие:

1. Обработать TwoClass
   
2. Выход
   
Введите номер действия: 2
***

### Задание 1.6 (@Cache)

**<ins>Задача:</ins>**

Разработайте аннотацию @Cache, со следующими характеристиками:
+ Целью может быть ТИП
+ Доступна во время исполнения программы
+ Имеет необязательное свойство value, типа String[]
+ Значение свойства по умолчанию: пустой массив

Проаннотируйте класс аннотацией @Cache, указав несколько кешируемых областей.

Создайте обработчик, который выводит список всех кешируемых областей или сообщение, что список пуст. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
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
```

```
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
```

```
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
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Cache ~

Проверка класса UserService:

Найдены кешируемые области для класса CacheClass:

   - users
     
   - profiles
     
   - sessions

Проверка класса String (не имеет @Cache):

Класс String не помечен аннотацией @Cache.

Проверка null-входа:

Ошибка: передан null вместо класса.
***

### Задание 2.1 (Тестирование)

**<ins>Задача:</ins>**

Создайте тест, используя фреймворк JUnit, который проверяет корректность вызова методов, отмеченных аннотацией @Invoke.
+ Использовать Reflection API для поиска методов с аннотацией.
+ Убедиться, что метод действительно выполняется без исключений.
+ Проверить, что возвращаемое значение или побочный эффект соответствует ожиданиям (например, устанавливает флаг или изменяет состояние объекта).
+ Тест должен использовать аннотацию @BeforeEach для подготовки тестируемого экземпляра класса. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
package ru.Bochkarev.Tests;

import ru.Bochkarev.Annotations.Invoke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Bochkarev.Classes.InvokeClass;
import ru.Bochkarev.Processors.InvokeProcessor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Набор JUnit-тестов для проверки корректности работы обработчика аннотации {@link ru.Bochkarev.Annotations.Invoke}.
 */
public class InvokeTest {

    private InvokeClass testObject;

    /**
     * Подготавливает новый экземпляр {@link InvokeClass} перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testObject = new InvokeClass();
    }

    /**
     * Проверяет, что методы с аннотацией {@link ru.Bochkarev.Annotations.Invoke}
     * действительно вызываются и устанавливают соответствующие флаги.
     */
    @Test
    void testInvokeAnnotatedMethodsAreCalled() {
        assertFalse(testObject.isFlagOneSet(), "Флаг methodOne должен быть false до вызова.");
        assertFalse(testObject.isPrivateFlagSet(), "Приватный флаг должен быть false.");

        InvokeProcessor.processInvoke(testObject);

        assertTrue(testObject.isFlagOneSet(), "Флаг methodOne должен быть установлен после вызова.");
        assertFalse(testObject.isFlagTwoSet(), "methodTwo не должен быть вызван автоматически.");
        assertFalse(testObject.isPrivateFlagSet(), "Приватный метод не должен быть вызван.");
    }

    /**
     * Проверяет, что передача null в {@link InvokeProcessor#processInvoke(Object)}
     * вызывает {@link IllegalArgumentException}.
     */
    @Test
    void testProcessInvokeWithNullThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> InvokeProcessor.processInvoke(null)
        );
        assertEquals("Целевой объект не может быть null.", exception.getMessage());
    }
}
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Invoke;

/**
 * Класс, содержащий методы, часть из которых помечена аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
 * Используется для демонстрации автоматического вызова аннотированных методов.
 */
public class InvokeClass {

    private boolean flagOne = false;
    private boolean flagTwo = false;
    private boolean privateFlag = false;

    /**
     * Метод, помеченный аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
     * Устанавливает флаг {@code flagOne} в {@code true}.
     */
    @Invoke
    public void methodOne() {
        System.out.println("Вызван methodOne() — помечен @Invoke");
        this.flagOne = true;
    }

    /**
     * Обычный метод без аннотации. Устанавливает флаг {@code flagTwo}.
     */
    public void methodTwo() {
        System.out.println("Вызван methodTwo() — без аннотации");
        this.flagTwo = true;
    }

    /**
     * Приватный метод с аннотацией {@link ru.Bochkarev.Annotations.Invoke}.
     * Устанавливает флаг {@code privateFlag}.
     * Не будет вызван автоматически, если обработчик не настроен на работу с приватными методами.
     */
    @Invoke
    private void privateMethod() {
        System.out.println("Приватный метод с @Invoke");
        this.privateFlag = true;
    }

    // Геттеры для тестирования состояния
    public boolean isFlagOneSet() { return flagOne; }
    public boolean isFlagTwoSet() { return flagTwo; }
    public boolean isPrivateFlagSet() { return privateFlag; }
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Invoke ~

Вызван methodOne() — помечен @Invoke

Флаг methodOne установлен: true

Флаг methodTwo установлен: false

Приватный флаг установлен: false

Ручной вызов methodTwo():

Вызван methodTwo() — без аннотации

Флаг methodTwo после ручного вызова: true

Методы с аннотацией @Invoke были автоматически вызваны.

<img width="882" height="217" alt="image" src="https://github.com/user-attachments/assets/505752d2-5fc4-4b89-b298-849e33bf2c20" />
***

### Задание 2.2 (Тестирование)

**<ins>Задача:</ins>**

Разработайте тест, используя фреймворк JUnit, проверяющий корректность обработки аннотации @Two, если её свойства заданы некорректно. Например, строковое свойство first пустое (""), а числовое second отрицательное.
+ Создайте вспомогательный класс с аннотацией @Two(first = "", second = -1).
+ В тесте реализуйте метод, который через Reflection считывает значения аннотации.
+ Если одно из свойств нарушает ожидаемые условия (first – пустая строка, second < 0), то должен быть выброшен IllegalArgumentException.
+ Используйте assertThrows() из JUnit для проверки выбрасываемого исключения. 

**<ins>Метод решения:</ins>**



**<ins>Код реализации:</ins>**

```
package ru.Bochkarev.Tests;

import org.junit.jupiter.api.Test;
import ru.Bochkarev.Annotations.Two;
import ru.Bochkarev.Classes.InvalidTwoClass;
import ru.Bochkarev.Classes.TwoClass;
import ru.Bochkarev.Processors.TwoProcessor;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тестовый класс для проверки обработки некорректных значений
 * аннотации {@link Two} в классе {@link TwoProcessor}.
 */
public class TwoTest {

    /**
     * Проверяет, что метод {@link TwoProcessor#processTwo(Class)}
     * выбрасывает {@link IllegalArgumentException},
     * если аннотация {@code @Two} имеет пустое свойство {@code first}
     * и/или отрицательное свойство {@code second}.
     */
    @Test
    public void testProcessTwo_WithInvalidAnnotation_ThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TwoProcessor.processTwo(InvalidTwoClass.class)
        );


    }

    /**
     * Проверяет, что метод {@link TwoProcessor#processTwo(Class)}
     * не выбрасывает исключение при корректной аннотации.
     */
    @Test
    public void testProcessTwo_WithValidAnnotation_DoesNotThrow() {
        TwoProcessor.processTwo(TwoClass.class); // Не должно выбрасывать исключение
    }

    /**
     * Проверяет, что метод {@link TwoProcessor#processTwo(Class)}
     * выбрасывает {@link IllegalArgumentException} при передаче {@code null}.
     */
    @Test
    public void testProcessTwo_WithNullClass_ThrowsIllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TwoProcessor.processTwo(null)
        );
    }
}
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Two;

/**
 * Класс с намеренно некорректными значениями аннотации {@link Two}.
 * <p>
 * first = "" — пустая строка (ошибка),
 * second = -1 — отрицательное число (ошибка).
 */
@Two(first = "", second = -1)
public class InvalidTwoClass {
    // Класс используется только для теста аннотации
}
```

```
package ru.Bochkarev.Classes;

import ru.Bochkarev.Annotations.Two;

/**
 * Пример класса, аннотированного с помощью {@link Two}.
 */
@Two(first = "Пример строки", second = 42)
public class TwoClass {
    // Пустой класс для демонстрации аннотации
}
```

**<ins>Вывод на экран:</ins>**

~ Демонстрация работы аннотации @Two ~

Выберите действие:

1. Обработать TwoClass (корректный)

2. Обработать InvalidTwoClass (некорректный)
 
3. Выход

Введите номер действия: 1

Аннотация @Two найдена:

  first = "Пример строки"
  
  second = 42

Выберите действие:

1. Обработать TwoClass (корректный)

2. Обработать InvalidTwoClass (некорректный)

3. Выход

Введите номер действия: 2

Ошибка: Свойство 'first' не может быть пустым.

Выберите действие:

1. Обработать TwoClass (корректный)
  
2. Обработать InvalidTwoClass (некорректный)
  
3. Выход

Введите номер действия: 3

Выход из программы.

<img width="896" height="194" alt="image" src="https://github.com/user-attachments/assets/a5f5b0ce-38a9-4a70-b72d-9c136ae863ad" />
***

## Вспомогательные методы

+ `public int readInt(Scanner scanner)`

Что делает:

Читает из Scanner целое число, пока пользователь не введёт корректное значение.

Как работает (пошагово):
- Заходит в бесконечный цикл while (true).
- Печатает приглашение "Введите целое число: ".
- Проверяет scanner.hasNextInt() — есть ли следующий токен, который можно распарсить как int.
- Если true: читает int num = scanner.nextInt(); и возвращает num.
- Иначе: печатает сообщение об ошибке и вызывает scanner.next() — чтобы "съесть" неверный токен (иначе hasNextInt() будет снова false и цикл застрянет).

Примеры:

При вводе 42 вернёт 42; при вводе abc — попросит ввести ещё раз.

Код:
```
package ru.Bochkarev.Main;

import java.util.Scanner;

/**
 * Класс проверки.
 */
public class Check {

    // Проверка вводимого числа
    public int readInt(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                return num;
            } else {
                System.out.println("Ошибка: введите целое число!");
                scanner.next(); // очищаем неверный ввод
            }
        }
    }
}

```
