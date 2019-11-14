package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
        List<String> twelveAM = new ArrayList<>();
        List<String> pm = new ArrayList<>();
        List<String> twelvePM = new ArrayList<>();

        Pattern pattern = Pattern.compile("([0][0-9]|[1][0-2])([:])([0-5][0-9])([:])([0-5][0-9])([ ])([P][M]|[A][M])");
        Pattern patternAM = Pattern.compile("([A][M])");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String twelve = line.split(":")[0];

                boolean matcher = patternAM.matcher(line).find();
                boolean matches = pattern.matcher(line).matches();

                if (!matches) {
                    throw new IllegalArgumentException();
                }

                if (matcher) {
                    if (twelve.equals("12")) {
                        twelveAM.add(line);
                    } else {
                        am.add(line);
                    }

                } else {
                    if (twelve.equals("12")) {
                        twelvePM.add(line);
                    } else {
                        pm.add(line);
                    }

                }
            }
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException();
        } catch (IOException ex) {
            throw new IllegalArgumentException();
        }

        Collections.sort(am);       //O(n*log(n))
        Collections.sort(twelveAM);
        Collections.sort(pm);
        Collections.sort(twelvePM);

        try (FileWriter writer = new FileWriter(outputName)) {
            for (String str : twelveAM) {
                writer.write(str);
                writer.append('\n');
            }
            for (String str : am) {
                writer.write(str);
                writer.append('\n');
            }
            for (String str : twelvePM) {
                writer.write(str);
                writer.append('\n');
            }
            for (String str : pm) {
                writer.write(str);
                writer.append('\n');
            }
            writer.flush();

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    //Трудоемкость:O(n)
    //Ресурсоёмкость:O(n)

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
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile("([А-Яа-яA-Za-zёЁ-]+)([ ])([А-Яа-яA-Za-zёЁ-]+)([ ])([-])([ ])([А-Яа-яA-Za-zёЁ-]+)([ ][0-9]+)");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {                                                            //O(n)
                boolean matches = line.matches(String.valueOf(pattern));

                if (!matches) {
                    throw new IllegalArgumentException();
                }

                String key = line.split(" - ")[1].trim();
                String value = line.split(" - ")[0].trim();
                if (map.containsKey(key)) {
                    map.put(key, map.get(key) + "," + value);
                } else {
                    map.put(key, value);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException();
        } catch (IOException ex) {
            throw new IllegalArgumentException();
        }

        File file = new File(outputName);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            Stream stream = map.entrySet().stream().sorted((stringStringEntry, t1) -> {
                String key1 = stringStringEntry.getKey();
                String key2 = t1.getKey();

                String[] split = key1.split("\\s+");
                String street1 = split[0];
                Integer house1 = null;
                if (split.length > 1) {
                    house1 = Integer.parseInt(split[1]);
                }

                split = key2.split("\\s+");
                String street2 = split[0];
                Integer house2 = null;
                if (split.length > 1) {
                    house2 = Integer.parseInt(key2.split("\\s+")[1]);
                }
                if (street1.compareTo(street2) == 0 && house1 != null && house2 != null) {
                    return house1.compareTo(house2);
                }

                return street1.compareTo(street2);
            });

            Iterator iterator = stream.iterator();
            Map.Entry entry;
            while (iterator.hasNext()) {
                entry = (Map.Entry) iterator.next();
                String unValue = entry.getValue().toString().trim();
                String[] unsortedValues = unValue.split(",");
                Arrays.sort(unsortedValues);

                writer.write(entry.getKey() + " - " + String.join(", ", unsortedValues).trim());
                writer.write("\n");
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    //Трудоемкость:O(n)
    //Ресурсоёмкость:O(n)

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
        List<Integer> mas = new ArrayList<>();
        int length = 1;
        int maxLength = 0;
        Integer number = -1;

        Pattern pattern = Pattern.compile("([0-9]+)");


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {

                boolean matches = line.matches(String.valueOf(pattern));

                Integer i = Integer.parseInt(line);

                if (!matches) {
                    throw new IllegalArgumentException();
                }

                if (i < 0) {
                    throw new IllegalArgumentException();
                }
                mas.add(i);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        List<Integer> sortedMas = new ArrayList<>(mas);
        Collections.sort(sortedMas);

        for (int i = 0; i < sortedMas.size() - 1; i++) {
            if (sortedMas.get(i).equals(sortedMas.get(i + 1))) {
                length++;
            } else {
                length = 1;
            }
            if (length == maxLength && number > sortedMas.get(i)) {
                number = sortedMas.get(i);
            }
            if (length > maxLength) {
                maxLength = length;
                number = sortedMas.get(i);
            }

        }

        File file = new File(outputName);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            for (Integer num : mas) {
                if (!num.equals(number)) {
                    writer.write(num.toString());
                    writer.append('\n');
                }
            }
            for (int i = 0; i < maxLength; i++) {
                writer.write(number.toString());
                writer.append('\n');
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
    //Трудоемкость:O(n)
    //Ресурсоёмкость:O(n)

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


    //Трудоемкость:(n*log(n))
    //Ресурсоёмкость:O(n)


}
