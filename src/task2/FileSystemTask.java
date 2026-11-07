package task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileSystemTask {

    public static void main(String[] args) {
        // Определяем базовые имена по условию: фамилия Lutskiy, имя Maksim
        String surname = "Lutskiy";
        String name = "Maksim";

        // Paths.get() формирует объект Path для корневой директории <surname>
        Path rootDir = Paths.get(surname);

        try {
            // 1. Создание директории <surname>
            // Files.createDirectories() создаёт директорию (и все родительские, если их нет)
            Files.createDirectories(rootDir);
            System.out.println("1. Создана корневая директория: " + rootDir.toAbsolutePath());

            // 2. Внутри создаём файл <name>
            // resolve() объединяет родительский путь с относительным именем файла
            Path initialFile = rootDir.resolve(name);
            // Проверяем, существует ли файл, чтобы избежать FileAlreadyExistsException
            if (!Files.exists(initialFile)) {
                Files.createFile(initialFile);
            }
            System.out.println("2. Создан файл: " + initialFile);

            // 3. Создание вложенных папок dir1/dir2/dir3 и копирование туда файла <name>
            Path nestedDirs = rootDir.resolve("dir1/dir2/dir3");
            // Рекурсивно создаём всю цепочку dir1 -> dir2 -> dir3
            Files.createDirectories(nestedDirs);

            // Целевой путь скопированного файла: Lutskiy/dir1/dir2/dir3/Maksim
            Path targetCopiedFile = nestedDirs.resolve(name);
            // Files.copy() копирует файл с флагом REPLACE_EXISTING (перезаписать, если существует)
            Files.copy(initialFile, targetCopiedFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("3. Файл скопирован в: " + targetCopiedFile);

            // 4. Внутри директории dir1 создаём файл file1
            Path file1 = rootDir.resolve("dir1").resolve("file1");
            if (!Files.exists(file1)) {
                Files.createFile(file1);
            }
            System.out.println("4. Создан файл: " + file1);

            // 5. Внутри директории dir2 создаём файл file2
            Path file2 = rootDir.resolve("dir1/dir2").resolve("file2");
            if (!Files.exists(file2)) {
                Files.createFile(file2);
            }
            System.out.println("5. Создан файл: " + file2);

            // 6. Рекурсивный обход директории <surname>
            System.out.println("\n6. Рекурсивный обход директории " + surname + ":");
            // Использование try-with-resources обязательно для Files.walk(),
            // так как возвращаемый Stream<Path> держит открытый файловый дескриптор
            try (Stream<Path> stream = Files.walk(rootDir)) {
                stream.forEach(path -> {
                    // Проверяем, является ли текущий элемент каталогом
                    if (Files.isDirectory(path)) {
                        // Префикс [D] перед именем директории
                        System.out.println("[D] : " + path.getFileName());
                    } else {
                        // Префикс [F] перед именем файла
                        System.out.println("[F] : " + path.getFileName());
                    }
                });
            }

            // 7. Удаление директории dir1 со всем её содержимым
            Path dir1 = rootDir.resolve("dir1");
            System.out.println("\n7. Удаление директории: " + dir1);

            // Директорию с файлами нельзя удалить напрямую через Files.delete().
            // Необходимо обойти всё поддерево и отсортировать пути в обратном порядке (reverseOrder),
            // чтобы файлы и дочерние папки удалялись раньше своих родительских директорий
            try (Stream<Path> walkToDelete = Files.walk(dir1)) {
                walkToDelete
                        .sorted(Comparator.reverseOrder()) // Сортировка: листья дерева -> корень
                        .forEach(p -> {
                            try {
                                // Files.delete() удаляет пустую папку или отдельный файл
                                Files.delete(p);
                                System.out.println("   Удалено: " + p);
                            } catch (IOException e) {
                                System.err.println("   Ошибка удаления " + p + ": " + e.getMessage());
                            }
                        });
            }

            System.out.println("Директория dir1 успешно удалена со всем содержимым.");

        } catch (IOException e) {
            // Обрабатываем возможные ошибки ввода/вывода
            System.err.println("Ошибка при работе с файловой системой: " + e.getMessage());
            e.printStackTrace();
        }
    }
}