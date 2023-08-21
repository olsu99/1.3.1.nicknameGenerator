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
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        }).start();

        // слово из одной и той же буквы
        new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        }).start();

        // буквы в слове  по возрастанию
        new Thread(() -> {
            for (String text : texts) {
                if (isSorted(text)) {
                    incrementCounter(text.length());
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

    // слово - палиндром
    public static boolean isPalindrome(String text) {
        StringBuilder reverseTexts = new StringBuilder();
        for (int i = text.length() - 1; i >= 0; i--) {
            reverseTexts.append(text.charAt(i));
        }
        if (text.equals(reverseTexts.toString())) {
            return true;
        }
        return false;
    }

    // слово из одной и той же буквы
    public static boolean isSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    // буквы в слове  по возрастанию
    public static boolean isSorted(String text) {
        char[] textChars = text.toCharArray();
        Arrays.sort(textChars);
        String textSorted = new String(textChars);
        if (text.equals(textSorted)) {
            return true;
        } else return false;
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            length3.getAndIncrement();
        } else if (textLength == 4) {
            length4.getAndIncrement();
        } else {
            length5.getAndIncrement();
        }
    }
}

