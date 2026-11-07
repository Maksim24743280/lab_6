package task1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Retention(RetentionPolicy.RUNTIME) указывает, что аннотация сохраняется
// в скомпилированном байт-коде (.class) и доступна во время выполнения программы через Reflection API
@Retention(RetentionPolicy.RUNTIME)
// @Target(ElementType.METHOD) задаёт область применения аннотации — только методы
@Target(ElementType.METHOD)
public @interface Repeat {
    // Параметр times определяет, сколько раз необходимо вызвать помеченный метод
    int times();
}