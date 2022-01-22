/**
 * WordTable holds an array of 26 WordTree objects and methods to interact with those WordTree objects.
 * Course: COMP 2100
 * Assignment: Project 3
 *
 * @author Tony Nguyen, Jacob McIntosh
 * @version 1.0, 11/4/2021
 */

import java.io.PrintWriter;

public class WordTable {
    private WordTree[] table;

    public WordTable() {
        table = new WordTree[26];
        for (int i = 0; i < table.length; i++) {
            table[i] = new WordTree();
        }
    }

    /**
     * Adds word to the appropriate WordTree, with the given score.
     *
     * @param word
     * @param score
     */
    public void add(String word, int score) {
        table[word.charAt(0) - 'a'].add(word, score);
    }

    /**
     * Gets the average score of a word from the appropriate WordTree
     *
     * @param word
     * @return the average score of the word
     */
    public double getScore(String word) {
        return table[word.charAt(0) - 'a'].getScore(word);
    }

    /**
     * Asks the appropriate WordTree if it contains word.
     *
     * @param word
     * @return true or false
     */
    public boolean contains(String word) {
        return table[word.charAt(0) - 'a'].contains(word);
    }

    /**
     * Finds the height of a single tree.
     *
     * @param word
     * @return the height of the tree
     */
    public int checkHeight(String word) {
        return table[word.charAt(0) - 'a'].checkHeight();
    }

    /**
     * Loops through all 26 WordTree objects and prints out their height.
     */
    public void printAllHeights() {
        for (int i = 0; i < table.length; i++) {
            int letter = 'a' + i;
            char tree = (char) letter;
            System.out.println("Table " + tree + " height: " + table[i].checkHeight());
        }
    }

    /**
     * Loops through all 26 WordTree objects and print out their data
     *
     * @param out
     */
    public void print(PrintWriter out) {
        for (int i = 0; i < table.length; i++) {
            table[i].print(out);
        }
    }

}
