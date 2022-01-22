/**
 * Each WordTree contains words starting with the same letter. This class houses methods to retrieve information about
 * those words.
 * Course: COMP 2100
 * Assignment: Project 3
 *
 * @author Tony Nguyen, Jacob McIntosh
 * @version 1.0, 11/4/2021
 */

import java.io.PrintWriter;

public class WordTree {
    private static class Node {
        public String word;
        public int score;
        public int count;
        public boolean black;
        public Node left;
        public Node right;
    }

    private Node root = null;

    /*
      This method would rotate the node to the left, for the red black tree
     */
    private static Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.black = h.black;
        h.black = false;
        return x;
    }

    /*
      This method would rotate the node to the right, for the red black tree
     */
    private static Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.black = h.black;
        h.black = false;
        return x;
    }

    /*
      This method would flip the colors of the nodes to black
     */
    private static void flipColors(Node h) {
        h.black = false;
        h.left.black = true;
        h.right.black = true;
    }

    /*
      Check if the node is null returns false else change the node into red
     */
    private static boolean isRed(Node x) {
        if (x == null)
            return false;
        return !x.black;
    }

    /**
     * Will add the word and score into the tree and change it to black
     *
     * @param word
     * @param score
     */
    public void add(String word, int score) {
        root = add(root, word, score);
        root.black = true;
    }

    /*
      Adds a String to a WordTree with a given score. If word is already contained in the WordTree, this method should
      add the supplied score to the score of the given Node and increment its count. Otherwise, this method should
      insert a new Node at the appropriate place in the WordTree, with the supplied score and a count of 1, rebalancing
      and recoloring the tree if necessary to maintain the red-black properties.
     */
    private static Node add(Node node, String word, int score) {
        if (node == null) {
            node = new Node();
            node.word = word;
            node.score = score;
            node.count = 1;
            node.black = false;
        } else if (node.word.compareTo(word) > 0)
            node.left = add(node.left, word, score);
        else if (node.word.compareTo(word) < 0)
            node.right = add(node.right, word, score);
        else {
            node.score += score;
            node.count += 1;
        }
        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);
        return node;
    }

    /**
     * Checks the height from the root.
     *
     * @return
     */
    public int checkHeight() {
        return checkHeight(root);
    }

    /*
      Would check the height of red black tree, and if commented out the rbt code then the height of the binary tree
      Also would check from left side and right side.
     */
    private static int checkHeight(Node node) {
        if (node == null)
            return -1;
        int leftHeight = checkHeight(node.left);
        int rightHeight = checkHeight(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Check if contains a word from the root.
     *
     * @param word
     * @return
     */
    public boolean contains(String word) {
        return contains(root, word);
    }

    /*
      Returns true if word has already been added to the WordTree and false otherwise.
     */
    private static boolean contains(Node node, String word) {
        if (node == null)
            return false;
        else if (node.word.compareTo(word) > 0)
            return contains(node.left, word);
        else if (node.word.compareTo(word) < 0)
            return contains(node.right, word);
        else
            return true;

    }

    /**
     * Gets the score at the root
     *
     * @param word
     * @return
     */
    public double getScore(String word) {
        return getScore(root, word);
    }

    /*
      Searches for word in the tree. If found, it will return the floating-point quotient of the corresponding Node's
      score and count. If the word is not found, it will return 2.0, a neutral score.
     */
    private static double getScore(Node node, String word) {
        if (node == null)
            return 2.0;
        else if (node.word.compareTo(word) > 0)
            return getScore(node.left, word);
        else if (node.word.compareTo(word) < 0)
            return getScore(node.right, word);
        else
            return ((double) node.score) / node.count;
    }

    /**
     * Prints the data from the root.
     *
     * @param out
     */
    public void print(PrintWriter out) {
        print(root, out);
    }

    /*
      This method prints out each word in the WordTree in alphabetical order, followed by a tab, followed by its total
      score, followed by a tab, followed by its count, followed by a newline. It makes sense to adapt an inorder
      traversal to this task.
     */
    private static void print(Node node, PrintWriter out) {
        if (node != null) {
            print(node.left, out);
            out.println(node.word + "\t" + node.score + "\t" + node.count);
            print(node.right, out);
        }
    }
}
