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
