package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        List<String> am = new ArrayList<>();
        List<String> pm = new ArrayList<>();
        Pattern pattern = Pattern.compile("([0][0-9]|[1][0-2])([:])([0-5][0-9])([:])([0-5][0-9])([ ])([P][M]|[A][M])");
        Pattern patternAM = Pattern.compile("([A][M])");

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputName), StandardCharsets.UTF_8);

            for (String line : lines) {
                boolean matcher = patternAM.matcher(line).find();
                boolean matches = pattern.matcher(line).matches();

                if (!matches) {
                    throw new Exception();
                }
                if (matcher) {
                    am.add(line);
                } else {
                    pm.add(line);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(am);
        Collections.sort(pm);
        am.addAll(pm);

        try (FileWriter writer = new FileWriter(outputName)) {
            for (String str : am) {
                writer.write(str);
                writer.append('\n');
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        ArrayList res2 = new ArrayList();
        Pattern pattern = Pattern.compile("([А-Яа-я]+)([ ])([А-Яа-я]+)([ ])([-])([ ])([А-Яа-я]+)([ ][0-9]+)");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boolean matches = line.matches(String.valueOf(pattern));
                if (!matches) {
                    throw new Exception();
                }
                res2.add(line);
            }
            reader.close();
            System.out.println(res2);

            File file = new File(outputName);
            FileWriter fileWriter = new FileWriter(file);
            for (Object str : res2) {
                fileWriter.write(str + "\n");
            }
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        List<String> res = new ArrayList();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                res.add(line);
            }
            reader.close();

            int min = 0;
            int max = 0;

            for(int i = 0; i < res.size(); i ++) {
                for (int j = 0;j < res.size();j++) {
                    if (res.get(i   ).equals(res.get(j))) {
                      min++;

                    }
                }
            }










            File file = new File(outputName);
            FileWriter fileWriter = new FileWriter(file);
            for (Object str : res) {
                fileWriter.write(str + "\n");
            }
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        System.arraycopy(first, 0, second, 0, first.length);
        Arrays.sort(second);
    }
}
