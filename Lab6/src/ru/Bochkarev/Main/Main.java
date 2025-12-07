package ru.Bochkarev.Main;

import ru.Bochkarev.Classes.*;
import ru.Bochkarev.Processors.*;

import java.util.Scanner;

/**
 * Главный класс проекта.
 */
public class Main {

    /**
     * Точка входа в программу.
     * @param args аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.out.println("1) Задание 1. №1 ");
        System.out.println("2) Задание 1. №2 ");
        System.out.println("3) Задание 1. №3 ");
        System.out.println("4) Задание 1. №4 ");
        System.out.println("5) Задание 1. №5 ");
        System.out.println("6) Задание 1. №6 ");
        System.out.println("7) Задание 2. №2 ");
        System.out.println("7) Задание 2. №6 ");
        System.out.println("Выберите задание: ");

        Check check = new Check();
        Scanner scan = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        if (scan.hasNextInt()) {
            int choice = scan.nextInt();

            switch (choice) {

                case 1: {
                    System.out.println("\n~Демонстрация работы аннотации @Invoke~\n");

                    InvokeClass obj = new InvokeClass();
                    InvokeProcessor.processInvoke(obj);

                    System.out.println("Ручной вызов methodTwo():");
                    obj.methodTwo();
                    System.out.println();

                    System.out.println("Методы с аннотацией @Invoke были автоматически вызваны");

                    break;
                }
                case 2:
                {
                    System.out.println("~Демонстрация работы аннотации @Default~\n");

                    DefaultProcessor.processDefaultType(DefaultClass.class);
                    DefaultProcessor.processDefaultField(DefaultClass.class, "someField");

                    break;
                }
                case 3:
                {
                    System.out.println("~Демонстрация работы аннотации @ToString~\n");

                    try {
                        System.out.print("Введите имя: ");
                        String name = scanner.nextLine().trim();
                        if (name.isEmpty()) {
                            System.out.println("Имя не может быть пустым. Установлено значение по умолчанию: \"Аноним\".");
                            name = "Аноним";
                        }

                        System.out.print("Введите возраст: ");
                        int age;
                        try {
                            age = Integer.parseInt(scanner.nextLine().trim());
                            if (age < 0 || age > 150) {
                                System.out.println("Некорректный возраст. Установлено значение по умолчанию: 0.");
                                age = 0;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Некорректный формат возраста. Установлено значение по умолчанию: 0.");
                            age = 0;
                        }

                        System.out.print("Введите пароль: ");
                        String password = scanner.nextLine();
                        if (password.isEmpty()) {
                            password = "defaultPass";
                        }

                        ToStringClass person = new ToStringClass(name, age, password);

                        System.out.println("\n--- Стандартный toString (Object):");
                        System.out.println(person.toString());

                        System.out.println("\n--- Кастомный toString с @ToString:");
                        System.out.println(ToStringProcessor.processToString(person));


                    } catch (Exception e) {
                        System.err.println("Произошла ошибка: " + e.getMessage());
                        e.printStackTrace();
                    }

                    break;
                }
                case 4:
                {
                    System.out.println("~Демонстрация работы аннотации @Validate ~\n");

                    try {
                        ValidateProcessor.processValidate(ValidateClass.class);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }

                    break;
                }
                case 5:
                {
                    boolean running = true;

                    System.out.println("~Демонстрация работы аннотации @Two~");

                    while (running) {
                        System.out.println("\nВыберите действие:");
                        System.out.println("1. Обработать TwoClass");
                        System.out.println("2. Выход");
                        System.out.print("Введите номер действия: ");

                        String input = scanner.nextLine().trim();

                        switch (input) {
                            case "1": {
                                try {
                                    TwoProcessor.processTwo(TwoClass.class);
                                } catch (Exception e) {
                                    System.err.println("Ошибка при обработке аннотации: " + e.getMessage());
                                }
                                break;
                            }
                            case "2": {

                                running = false;
                                break;
                            }
                            default: {
                                System.out.println("Неверный ввод. Пожалуйста, введите 1 или 2.");
                                break;
                            }
                        }
                    }

                    break;
                }
                case 6:
                {
                    System.out.println("~Демонстрация работы аннотации @Cache~");

                    // Демонстрация работы с классом, помеченным @Cache
                    System.out.println("Проверка класса UserService:");
                    CacheProcessor.processCacheAnnotation(CacheClass.class);

                    System.out.println();

                    // Демонстрация работы с классом без аннотации
                    System.out.println("Проверка класса String (не имеет @Cache):");
                    CacheProcessor.processCacheAnnotation(String.class);

                    System.out.println();

                    // Демонстрация работы с null (проверка обработки ошибок)
                    System.out.println("Проверка null-входа:");
                    CacheProcessor.processCacheAnnotation(null);

                    break;
                }
                case 7:
                {
                    System.out.println("\n~Демонстрация работы аннотации @Invoke~\n");

                    InvokeClass obj = new InvokeClass();

                    // Автоматический вызов методов с @Invoke
                    InvokeProcessor.processInvoke(obj);

                    // Проверка состояния
                    System.out.println("Флаг methodOne установлен: " + obj.isFlagOneSet());
                    System.out.println("Флаг methodTwo установлен: " + obj.isFlagTwoSet());
                    System.out.println("Приватный флаг установлен: " + obj.isPrivateFlagSet());

                    System.out.println("\nРучной вызов methodTwo():");
                    obj.methodTwo();
                    System.out.println("Флаг methodTwo после ручного вызова: " + obj.isFlagTwoSet());
                    System.out.println("\nМетоды с аннотацией @Invoke были автоматически вызваны.");

                    break;
                }
                case 8:
                {
                    System.out.println("~Демонстрация работы аннотации @Two~");

                    boolean running = true;

                    while (running) {
                        System.out.println("\nВыберите действие:");
                        System.out.println("1. Обработать TwoClass (корректный)");
                        System.out.println("2. Обработать InvalidTwoClass (некорректный)");
                        System.out.println("3. Выход");
                        System.out.print("Введите номер действия: ");

                        String input = scanner.nextLine().trim();

                        switch (input) {
                            case "1":
                                try {
                                    TwoProcessor.processTwo(TwoClass.class);
                                } catch (Exception e) {
                                    System.err.println("Ошибка: " + e.getMessage());
                                }
                                break;
                            case "2":
                                try {
                                    TwoProcessor.processTwo(InvalidTwoClass.class);
                                } catch (Exception e) {
                                    System.err.println("Ошибка: " + e.getMessage());
                                }
                                break;
                            case "3":
                                running = false;
                                System.out.println("Выход из программы.");
                                break;
                            default:
                                System.out.println("Неверный ввод. Пожалуйста, введите 1, 2 или 3.");
                                break;
                        }
                    }

                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

}
