import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static Map<String, Integer> alphabetToNumber = new HashMap<>();
    static Map<Integer, String> numberToAlphabet = new HashMap<>();

    static {
        alphabetToNumber.put("I", 1);
        alphabetToNumber.put("V", 5);
        alphabetToNumber.put("X", 10);
        alphabetToNumber.put("L", 50);
        alphabetToNumber.put("C", 100);

        numberToAlphabet.put(1, "I");
        numberToAlphabet.put(5, "V");
        numberToAlphabet.put(10, "X");
        numberToAlphabet.put(50, "L");
        numberToAlphabet.put(100, "C");
    }

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println(calc(reader.readLine()));
        }
    }

    public static String calc(String input) throws Exception {
        String letters = input.replaceAll("[\\d\\s\\+\\*IXV/-]", "");
        if(letters.length()!=0) throw new Exception("формат математической операции не удовлетворяет заданию");
        long operator = input.chars().filter(c -> c == '+' || c == '-' || c == '*' || c == '/').count();
        if (operator == 1){
            Matcher matcherRim = Pattern.compile("[IVX]").matcher(input);
            Matcher matcherNumber = Pattern.compile("\\d").matcher(input);
            if (matcherNumber.find() & matcherRim.find()) {
                throw new Exception("используются одновременно разные системы счисления");
            } else {
                input = input.replaceAll("\\s", "");
                if (input.length() < 3) throw new Exception("строка не является математической операцией");
                String operand;
                String[] numbers;

                if (matcherNumber.find()) {
                    operand = input.replaceAll("\\d", "").trim();
                    if (operand.length() > 1)
                        throw new Exception("формат математической операции не удовлетворяет заданию");
                    switch (operand) {
                        case "+":
                            numbers = input.split(Pattern.compile("\\+").toString());
                            if (numbers.length < 2)
                                throw new Exception("формат математической операции не удовлетворяет заданию");
                            if (Integer.parseInt(numbers[0]) > 0 & Integer.parseInt(numbers[0]) <= 10 & Integer.parseInt(numbers[1]) > 0 & Integer.parseInt(numbers[1]) <= 10)
                                return String.valueOf(Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]));
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "-":
                            numbers = input.split("-");
                            if (Integer.parseInt(numbers[0]) > 0 & Integer.parseInt(numbers[0]) <= 10 & Integer.parseInt(numbers[1]) > 0 & Integer.parseInt(numbers[1]) <= 10)
                                return String.valueOf(Integer.parseInt(numbers[0]) - Integer.parseInt(numbers[1]));
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "*":
                            numbers = input.split(Pattern.compile("\\*").toString());
                            if (Integer.parseInt(numbers[0]) > 0 & Integer.parseInt(numbers[0]) <= 10 & Integer.parseInt(numbers[1]) > 0 & Integer.parseInt(numbers[1]) <= 10)
                                return String.valueOf((Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1])));
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "/":
                            numbers = input.split("/");
                            if (Integer.parseInt(numbers[0]) > 0 & Integer.parseInt(numbers[0]) <= 10 & Integer.parseInt(numbers[1]) > 0 & Integer.parseInt(numbers[1]) <= 10)
                                return String.valueOf(Integer.parseInt(numbers[0]) / Integer.parseInt(numbers[1]));
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                    }
                }
                if (matcherRim.find()) {
                    operand = input.replaceAll("[IXV]", "").trim();
                    int numberA;
                    int numberB;
                    if (operand.length() > 1)
                        throw new Exception("формат математической операции не удовлетворяет заданию");
                    switch (operand) {
                        case "+":
                            numbers = input.split(Pattern.compile("\\+").toString());
                            numberA = rimToNumber(numbers[0]);
                            numberB = rimToNumber(numbers[1]);
                            if (numberA <= 10 & numberB <= 10) return numberToRim(numberA + numberB);
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "-":
                            numbers = input.split("-");
                            numberA = rimToNumber(numbers[0]);
                            numberB = rimToNumber(numbers[1]);
                            if (numberA <= 10 & numberB <= 10) {
                                int itog = numberA - numberB;
                                if (itog <= 0) throw new Exception("в римской системе нет отрицательных чисел");
                                else return numberToRim(itog);
                            } else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "*":
                            numbers = input.split(Pattern.compile("\\*").toString());
                            numberA = rimToNumber(numbers[0]);
                            numberB = rimToNumber(numbers[1]);
                            if (numberA <= 10 & numberB <= 10) return numberToRim(numberA * numberB);
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                        case "/":
                            numbers = input.split("/");
                            numberA = rimToNumber(numbers[0]);
                            numberB = rimToNumber(numbers[1]);
                            if (numberA <= 10 & numberB <= 10) return numberToRim(numberA / numberB);
                            else throw new Exception("формат математической операции не удовлетворяет заданию");
                    }
                }
            }
        }
        else throw new Exception("формат математической операции не удовлетворяет заданию");
        return null;
    }

    static int rimToNumber(String rim) {
        int numberResult = 0;
        String[] numbers = rim.split("");
        for (int a = 0; a < numbers.length; a++) {
            if (a != numbers.length - 1) {
                int num = alphabetToNumber.get(numbers[a]);
                int numNext = alphabetToNumber.get(numbers[a + 1]);
                if (num < numNext) numberResult = numberResult - num;
                else numberResult = numberResult + num;
            } else numberResult = numberResult + alphabetToNumber.get(numbers[a]);
        }
        return numberResult;
    }

    static String numberToRim(int number) {
        StringBuilder rimResult = new StringBuilder();
        int divider = 100;
        while (number > 0) {
            int a = number - number % divider;
            if (a >= 5 * divider) {
                if ((a - 5 * divider) <= (3 * divider)) {
                    rimResult.append(numberToAlphabet.get(5 * divider));
                    a = a - 5 * divider;
                    while (a > 0) {
                        rimResult.append(numberToAlphabet.get(divider));
                        a = a - divider;
                    }
                } else {
                    while (a != divider * 10) {
                        rimResult.append(numberToAlphabet.get(divider));
                        a = a + divider;
                    }
                    rimResult.append(numberToAlphabet.get(a));
                }
            }
            if (a < 5 * divider) {
                if ((5 * divider - a) >= 2 * divider) {
                    while (a > 0) {
                        rimResult.append(numberToAlphabet.get(divider));
                        a = a - divider;
                    }
                } else {
                    while (a < 5 * divider) {
                        rimResult.append(numberToAlphabet.get(divider));
                        a = a + divider;
                    }
                    rimResult.append(numberToAlphabet.get(5 * divider));
                }
            }
            number = number % divider;
            divider = divider / 10;
        }
        return rimResult.toString();
    }
}

