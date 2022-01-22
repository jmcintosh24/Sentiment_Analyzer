# Sentiment_Analyzer
Reads data from sentences from hundreds of movie reviews included in reviews.txt. Assigns a value to each word depending on the review score given from its respective review. Words that appear
in more than one review are given an average score. Neutral words included in stop.txt are given a score of 2. The sentiment of each word is outputted to a word statistics file after
the analysis and the user's sentences can then be evaluated. (0-2) is considered to have negative sentiment, (2-5) positive.

The data is stored in a hash table of length 26, where each element is a tree. Each tree corresponds to a letter in the alphabet and the repsective words are stored in the trees.

This project was worked on with a partner in a Data Structures class.
