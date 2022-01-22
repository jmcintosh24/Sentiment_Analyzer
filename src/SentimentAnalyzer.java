/**
 * SentimentAnalyzer finds the sentiment of an entered phrase by comparing each word to words commonly found in
 * movie reviews.
 * Course: COMP 2100
 * Assignment: Project 3
 *
 * @author Tony Nguyen, Jacob McIntosh
 * @version 1.0, 11/4/2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SentimentAnalyzer {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        WordTable stopTable = readAndParseStop(input);
        WordTable reviewTable = readAndParseReviews(input, stopTable);
        findPhraseScore(reviewTable, stopTable, input);
    }

    /*
    Prompts the user to enter a phrase. Once entered, the sentiment score of the phrase is found by averaging the
    scores of all words that are not found in the stopTable. If the word is found in the reviewTable it will
    be assigned the corresponding score. If it is not found in reviewTable the score is defaulted to 2.
     */
    private static void findPhraseScore(WordTable reviewTable, WordTable stopTable, Scanner input) {
        String choice = "";
        while (!choice.equals("no")) {
            System.out.print("Would you like to analyze a phrase? (yes/no) ");
            choice = input.nextLine();
            if (choice.equals("yes")) {
                System.out.print("Enter phrase: ");
                String phrase = input.nextLine().toLowerCase();
                double phraseScore = 0.0;
                String phraseWord = "";
                int realWords = 0;
                for (int i = 0; i < phrase.length(); i++) {
                    char c = phrase.charAt(i);
                    if (Character.isAlphabetic(c) || c == '\'' || c == '-') {
                        phraseWord += c;
                    } else if (phraseWord.length() > 0) {
                        phraseWord = cleanWord(phraseWord);
                        if (reviewTable.contains(phraseWord)) {
                            phraseScore += reviewTable.getScore(phraseWord);
                            realWords++;
                        } else if (!(stopTable.contains(phraseWord))) {
                            phraseScore += 2.0;
                            realWords++;
                        }
                        phraseWord = "";
                    }

                }
                if (phraseWord.length() > 0) {
                    phraseWord = cleanWord(phraseWord);
                    if (reviewTable.contains(phraseWord)) {
                        phraseScore += reviewTable.getScore(phraseWord);
                        realWords++;
                    } else if (!(stopTable.contains(phraseWord))) {
                        realWords++;
                        phraseScore += 2.0;
                    }
                }

                double finalScore = phraseScore / realWords;
                analyze(finalScore, realWords);
            }
        }
    }

    /*
    Cleans up a string by removing all apostrophes and hyphens.
     */
    private static String cleanWord(String word) {
        while (word.length() > 0) {
            if (word.charAt(0) == '\'' || word.charAt(0) == '-') {
                word = word.substring(1);
            } else if (word.charAt(word.length() - 1) == '\'' || word.charAt(word.length() - 1) == '-') {
                word = word.substring(0, word.length() - 1);
            } else {
                return word;
            }
        }
        return word;
    }

    /*
    Prompts the user for the stop word file until a valid file is entered. Afterwards, it parses the stop file into
    a WordTable called stopTable.
     */
    private static WordTable readAndParseStop(Scanner input) {
        Scanner stopScanner = null;
        boolean stopNeeded = true;

        //Prompt the user for the stop words file
        while (stopNeeded) {
            System.out.print("Enter a stop word file: ");
            String stopName = input.nextLine();
            try {
                File stop = new File(stopName);
                stopScanner = new Scanner(stop);
                stopNeeded = false;
            } catch (Exception e) {
                System.out.println("That file does not exist!");
            }
        }

        //Parses the stop file into a WordTable
        WordTable stopTable = new WordTable();
        while (stopScanner.hasNextLine()) {
            stopTable.add(stopScanner.nextLine(), 0);
        }

        return stopTable;
    }

    /*
    Prompts the user for the reviews file until a valid file is entered. Afterwards, it parses the reviews file into
    a WordTable called reviewTable.
     */
    private static WordTable readAndParseReviews(Scanner input, WordTable stopTable) {
        Scanner reviewScanner = null;
        boolean reviewNeeded = true;
        String reviewName = "";

        //Prompt the user for the movie reviews file
        while (reviewNeeded) {
            System.out.print("Enter a review file: ");
            reviewName = input.nextLine();
            try {
                File review = new File(reviewName);
                reviewScanner = new Scanner(review);
                reviewNeeded = false;
            } catch (Exception e) {
                System.out.println("That file does not exist!");
            }
        }

        //Parses the review file into a WordTable
        WordTable reviewTable = new WordTable();
        String line = "";
        String word = "";
        int score;
        while (reviewScanner.hasNextLine()) {
            score = reviewScanner.nextInt();
            line = reviewScanner.nextLine().toLowerCase();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (Character.isAlphabetic(c) || c == '\'' || c == '-') {
                    word += c;
                } else if (word.length() > 0) {
                    word = cleanWord(word);
                    if (word.length() > 0 && !(stopTable.contains(word))) {
                        reviewTable.add(word, score);
                    }
                    word = "";
                }
            }
        }

        //Prints the word statistics of the reviewTable
        printStats(reviewTable, reviewName);

        return reviewTable;
    }

    /*
    Writes the word statistics of the given WordTable to a text file. The word statistics consist of the word, it's
    score and it's count.
     */
    private static void printStats(WordTable reviewTable, String reviewName) {
        //Writes the word statistics file
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream(reviewName + ".words.txt"));
            reviewTable.print(out);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (out != null) out.close();
        }
        System.out.println("Word statistics written to file " + reviewName + ".words\n");
    }

    /*
    Determines whether or not the given score has positive, negative, neutral or no sentiment.
     */
    private static void analyze(double finalScore, int realWords) {
        if (realWords == 0)
            System.out.println("Your phrase contained no words that affect sentiment.");
        else {
            System.out.format("Score: %.3f%n", finalScore);
            if (finalScore > 2.0)
                System.out.println("Your phrase was positive.");
            else if (finalScore < 2.0)
                System.out.println("Your phrase was negative");
            else
                System.out.println("Your phrase was perfectly neutral.");
        }
    }
}
