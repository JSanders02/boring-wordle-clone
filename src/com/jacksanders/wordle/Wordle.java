package com.jacksanders.wordle;// import necessary packages

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Wordle {
    static String[] allowedGuesses;
    static String[] answerList = readFile("answers.txt");
    static String answer;

    public static int randomNum(int lower, int upper) {
        return lower + (int) Math.floor(Math.random() * (upper - lower));
    }

    public static <T extends Comparable<T>> int binarySearch(T[] data, T toFind) {
        int leftPointer = 0;
        int rightPointer = data.length - 1;
        int searchPoint = data.length >> 1; // Bit shift one to the right (div2)
        T found = data[searchPoint];
        while (!found.equals(toFind) && rightPointer != leftPointer) {
            if (toFind.compareTo(found) > 0) {
                leftPointer = searchPoint + 1;
            } else {
                rightPointer = searchPoint - 1;
            }

            searchPoint = leftPointer + ((rightPointer - leftPointer) >> 1);
            found = data[searchPoint];

            System.out.println(found);
        }

        if (!found.equals(toFind)) { return -1; }
        return searchPoint;
    }

    public static String[] readFile(String filename) {
        ArrayList<String> outList = new ArrayList<String>();
        FileReader file = null;
        try {
            file = new FileReader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {
            outList.add(sc.next());
        }

        return outList.toArray(new String[0]);
    }

    public static String chooseAnswer() {
        return answerList[randomNum(0, answerList.length)];
    }

    public static void main(String[] args) {
        System.out.println(binarySearch(answerList, "aback"));
        System.out.println("\n");
        System.out.println(binarySearch(answerList, "zonal"));
        System.out.println("\n");
        System.out.println(binarySearch(answerList, "sling"));
        System.out.println("\n");
        System.out.println(binarySearch(answerList, "broth"));
    }
}