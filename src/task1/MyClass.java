package task1;

public class MyClass {

    // --- Публичные методы (2-3 шт.) ---

    // Публичный метод без аннотации
    public void publicMethodWithoutAnnotation() {
        // Выводит сообщение в консоль для фиксации вызова
        System.out.println("Вызов publicMethodWithoutAnnotation()");
    }

    // Публичный метод с аннотацией @Repeat (Invoker должен его проигнорировать по условию)
    @Repeat(times = 2)
    public void publicMethodWithAnnotation() {
        // Выводит сообщение в консоль
        System.out.println("Вызов publicMethodWithAnnotation()");
    }

    // --- Защищённые (protected) методы (2-3 шт.) ---

    // Защищённый метод с аннотацией @Repeat (times = 3)
    @Repeat(times = 3)
    protected void protectedMethodOne() {
        // Сообщает о вызове первого защищённого метода
        System.out.println("  -> Выполнен protectedMethodOne()");
    }

    // Защищённый метод с аннотацией @Repeat (times = 1)
    @Repeat(times = 1)
    protected void protectedMethodTwo() {
        // Сообщает о вызове второго защищённого метода
        System.out.println("  -> Выполнен protectedMethodTwo()");
    }

    // Защищённый метод без аннотации (не должен вызываться)
    protected void protectedMethodWithoutAnnotation() {
        // Сообщает о вызове (если вызван ошибочно)
        System.out.println("  -> Выполнен protectedMethodWithoutAnnotation()");
    }

    // --- Приватные (private) методы с параметрами (2-3 шт.) ---

    // Приватный метод с аннотацией @Repeat (times = 2) и строковым параметром
    @Repeat(times = 2)
    private void privateMethodString(String message) {
        // Печатает переданный аргумент
        System.out.println("  -> Выполнен privateMethodString с аргументом: \"" + message + "\"");
    }

    // Приватный метод с аннотацией @Repeat (times = 4) и числовыми параметрами
    @Repeat(times = 4)
    private void privateMethodCalculation(int a, int b) {
        // Вычисляет сумму параметров и выводит результат
        int sum = a + b;
        System.out.println("  -> Выполнен privateMethodCalculation: " + a + " + " + b + " = " + sum);
    }

    // Приватный метод без аннотации (не должен вызываться)
    private void privateMethodWithoutAnnotation(boolean flag) {
        // Выводит переданный логический флаг
        System.out.println("  -> Выполнен privateMethodWithoutAnnotation: " + flag);
    }
}