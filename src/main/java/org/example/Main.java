package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int quantity = 100_000;
    public static AtomicInteger length3 = new AtomicInteger(0);
    public static AtomicInteger length4 = new AtomicInteger(0);
    public static AtomicInteger length5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[quantity];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // слово - палиндром
        new Thread(() -> {
            for (String text : texts) {
                StringBuilder reverseTexsts = new StringBuilder();
                for (int i = text.length() - 1; i >= 0; i--) {
                    reverseTexsts.append(text.charAt(i));
                }
                if (text.equals(reverseTexsts.toString())) {
                    if (text.length() == 3)
                        length3.incrementAndGet();
                    if (text.length() == 4)
                        length4.incrementAndGet();
                    if (text.length() == 5)
                        length5.incrementAndGet();
                }
            }
        }).start();

        // слово из одной и той же буквы
        new Thread(() -> {
            for (String text : texts) {
                boolean isEquals = true;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) != text.charAt(0)) {
                        isEquals = false;
                    }
                }
                if (isEquals) {
                    if (text.length() == 3)
                        length3.incrementAndGet();
                    if (text.length() == 4)
                        length4.incrementAndGet();
                    if (text.length() == 5)
                        length5.incrementAndGet();
                }
            }
        }).start();

        // буквы в слове  по возрастанию
        new Thread(() -> {
            for (String text : texts) {
                boolean isSorted = false;
                char[] textChars = text.toCharArray();
                Arrays.sort(textChars);
                String textSorted = new String(textChars);
                if (text.equals(textSorted)) {
                    isSorted = true;
                }

                if (isSorted) {
                    if (text.length() == 3)
                        length3.incrementAndGet();
                    if (text.length() == 4)
                        length4.incrementAndGet();
                    if (text.length() == 5)
                        length5.incrementAndGet();
                }
            }
        }).start();

        System.out.println("Красивых слов с длиной 3: " + length3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + length4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + length5 + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}