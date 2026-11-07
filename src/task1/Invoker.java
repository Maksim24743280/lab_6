package task1;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Invoker {

    public static void main(String[] args) {
        try {
            // Создаём экземпляр целевого класса MyClass, над которым будут производиться вызовы
            MyClass targetObject = new MyClass();

            // Получаем объект Class, содержащий метаданные о MyClass
            Class<?> clazz = targetObject.getClass();

            // getDeclaredMethods() возвращает массив всех объявленных в классе методов,
            // включая public, protected, package-private и private (без унаследованных)
            Method[] methods = clazz.getDeclaredMethods();

            System.out.println("=== Запуск рефлексивного вызова методов MyClass ===");

            // Перебираем каждый объявленный метод класса
            for (Method method : methods) {
                // isAnnotationPresent проверяет наличие аннотации @Repeat на текущем методе
                if (method.isAnnotationPresent(Repeat.class)) {
                    // Получаем модификаторы доступа текущего метода в виде битовой маски
                    int modifiers = method.getModifiers();

                    // Проверяем, является ли метод protected или private (публичные отсекаются)
                    boolean isProtected = Modifier.isProtected(modifiers);
                    boolean isPrivate = Modifier.isPrivate(modifiers);

                    // Условие: вызываем только защищённые и приватные методы
                    if (isProtected || isPrivate) {
                        // Извлекаем саму аннотацию @Repeat для чтения её параметров
                        Repeat repeatAnnotation = method.getAnnotation(Repeat.class);
                        // Получаем количество повторов из параметра times()
                        int timesToInvoke = repeatAnnotation.times();

                        // setAccessible(true) отключает проверку уровня доступа Java,
                        // позволяя вызывать private и protected методы извне класса
                        method.setAccessible(true);

                        System.out.println("\nМетод: " + method.getName()
                                + " | Модификатор: " + (isPrivate ? "private" : "protected")
                                + " | Повторений: " + timesToInvoke);

                        // Подготавливаем аргументы в зависимости от типов параметров метода
                        Object[] arguments = prepareArguments(method);

                        // Вызываем метод заданное число раз
                        for (int i = 0; i < timesToInvoke; i++) {
                            System.out.print("[" + (i + 1) + "/" + timesToInvoke + "] ");
                            // invoke() выполняет метод на объекте targetObject с подготовленными аргументами
                            method.invoke(targetObject, arguments);
                        }
                    }
                }
            }

            System.out.println("\n=== Вызовы успешно завершены ===");

        } catch (Exception e) {
            // Перехватываем и выводим возможные ошибки рефлексии (IllegalAccessException, InvocationTargetException и т.д.)
            e.printStackTrace();
        }
    }

    /**
     * Вспомогательный метод для динамической подстановки аргументов
     * на основе типов принимаемых параметров метода.
     */
    private static Object[] prepareArguments(Method method) {
        // Получаем массив типов входных параметров метода
        Class<?>[] paramTypes = method.getParameterTypes();
        // Создаём массив значений нужной длины
        Object[] args = new Object[paramTypes.length];

        // Заполняем аргументы подходящими тестовыми данными
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> type = paramTypes[i];
            if (type.equals(String.class)) {
                // Для строкового параметра передаём тестовую строку
                args[i] = "Тестовое сообщение";
            } else if (type.equals(int.class) || type.equals(Integer.class)) {
                // Для целочисленных параметров передаём произвольные числа
                args[i] = (i + 1) * 10;
            } else if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                // Для логических параметров передаём true
                args[i] = true;
            } else {
                // Для остальных типов передаём null
                args[i] = null;
            }
        }
        return args;
    }
}