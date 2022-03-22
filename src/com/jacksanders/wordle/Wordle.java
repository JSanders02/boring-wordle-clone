package com.jacksanders.wordle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Wordle {
    static String[] allowedGuesses = readFile("guesslist.txt");
    static String[] answerList = readFile("answers.txt");
    static String answer = chooseAnswer();

    public static int randomNum(int lower, int upper) {
        return lower + (int) Math.floor(Math.random() * (upper - lower));
    }

    public static <T extends Comparable<T>> int binarySearch(T[] data, T toFind) {
        int leftPointer = 0;
        int rightPointer = data.length - 1;
        int searchPoint = data.length >> 1; // Bit shift one to the right (div2)
        T found = data[searchPoint];
        while (!found.equals(toFind) && rightPointer - leftPointer > 1) {
            if (toFind.compareTo(found) > 0) {
                leftPointer = searchPoint;
            } else {
                rightPointer = searchPoint;
            }

            searchPoint = leftPointer + ((rightPointer - leftPointer) >> 1);
            found = data[searchPoint];
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

    public static boolean validGuess(String guess) {
        if (!guess.matches("[a-z]{5}")) { return false; }
        return binarySearch(answerList, guess) != -1 || binarySearch(allowedGuesses, guess) != -1;
    }

    public static String checkGuess(String guess) {
        StringBuilder outString = new StringBuilder();
        boolean[] usedIndices = new boolean[5];
        for (int i=0; i<5; i++) {
            if (guess.charAt(i) == answer.charAt(i)) {
                outString.append("G");
                usedIndices[i] = true;
            }
            else if (answer.indexOf(guess.charAt(i)) != -1) {
                for (int j=0; j<5; j++) {
                    if (answer.charAt(j) == guess.charAt(i) && !usedIndices[j]) {
                        if (answer.charAt(j) != guess.charAt(j)) { outString.append("Y"); }
                        else { outString.append("-"); }
                        break;
                    }
                }
            }
            else { outString.append("-"); }
        }

        return outString.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean correct = false;
        int roundCount = 0;
        while (!correct && roundCount < 6) {
            System.out.println("Enter a guess: ");
            String guess = sc.nextLine().toLowerCase();
            if (validGuess(guess)) {
                System.out.println(guess);
                System.out.println("\n" + checkGuess(guess));
                correct = guess.equals(answer);
                roundCount++;
            } else {
                System.out.println("Invalid guess!");
            }
            System.out.println("\n");
        }
    }
}