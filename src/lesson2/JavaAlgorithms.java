package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента вре+
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        List<Integer> res = new ArrayList<>();
        int min = 0;
        int max = 0;
        int check = 0;

        Pair<Integer, Integer> pair = new Pair<>(0, 0);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName)))) {
            String line;

            while ((line = reader.readLine()) != null) {
                res.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < res.size() - 1; i++) {
            if (res.get(i) < res.get(i + 1)) {
                if (check == 0 || check > res.get(i)) {
                    check = res.get(i);

                    for (int j = i + 1; j < res.size(); j++) {
                        if (res.get(i) < res.get(j)) {
                            min = res.get(j) - res.get(i);
                        }
                        if (min > max) {
                            max = min;
                            pair = new Pair<>(i + 1, j + 1);
                        }
                        min = 0;
                    }
                }
            }

        }
        return pair;
    }
    //Трудоемкость:O(nm)
    //Ресурсоёмкость:O(n)


    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        int res = 0;
        for (int i = 1; i <= menNumber; i++)
            res = (res + choiceInterval) % i;
        return res + 1;
    }

    //Трудоемкость:O(n)
    //Ресурсоёмкость:O(1)


    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String firs, String second) {
        char[] firstWord = firs.toCharArray();
        char[] secondWord = second.toCharArray();
        int[][] matrix = new int[firstWord.length][secondWord.length];
        int min = 0;
        int max = 0;
        String res = "";

        for (int i = 0; i < firstWord.length; i++) {
            for (int j = 0; j < secondWord.length; j++) {
                if (firstWord[i] == secondWord[j]) {
                    if (i == 0 || j == 0) {
                        matrix[i][j] = 1;
                    } else {
                        matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    }
                }
            }
        }
        for (int i = 0; i < firstWord.length; i++) {
            for (int j = 0; j < secondWord.length; j++) {
                if (matrix[i][j] > min) {
                    min = matrix[i][j];
                    max = i;
                }
            }
        }
        min -= 1;

        for (int i = max - min; i <= max; i++) {
            res += (Character) firstWord[i];
        }

        return res;
    }

    //Трудоемкость:O(nm)
    //Ресурсоёмкость:O(nm)

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;
        byte[] number = new byte[limit + 1];
        int result = 0;

        for (int i = 2; i <= limit; i++) {
            if (number[i] == 0) {
                result++;
                for (int j = 2; i * j <= limit; j++) {
                    number[i * j] = 1;
                }
            }
        }
        return result;
    }
    //Трудоемкость:O(nm)
    //Ресурсоёмкость:O(n)


    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
