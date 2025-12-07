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