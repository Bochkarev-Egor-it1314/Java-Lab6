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