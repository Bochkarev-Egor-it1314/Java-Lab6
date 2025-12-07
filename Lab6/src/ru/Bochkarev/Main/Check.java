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
