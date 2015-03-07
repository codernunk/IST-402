package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Story.java
 * Jesse Jurden, Mike Richards
 *
 * March 6, 2015
 *
 * This represents a Story model.
 */
public class Story implements Serializable {
    private String title; // The title of the story
    private String text; // The story's text.  This will get parsed to find story terms.

    private ArrayList<StoryWord> words; // All the words that the player can enter

    public Story(){
        words = new ArrayList<StoryWord>();
    }

    public void setTitle(String val){
        title = val;
    }
    public void setText(String val){
        text = val;
    }

    public String getTitle(){
        return title;
    }
    public String getText(){
        return text;
    }

    public ArrayList<StoryWord> getStoryWords() { return words; }

    public void parseStory(){
        int firstBraceIndex = 0;
        int lastBraceIndex = 0;
        String tempStory = text;

        while ((firstBraceIndex = tempStory.indexOf('[')+1) != 0){
            // Get the index of the last brace
            lastBraceIndex = tempStory.indexOf(']');
            // Extract the word type
            String wordType = tempStory.substring(firstBraceIndex,lastBraceIndex);
            // Create a new StoryWord and place that in our ArrayList
            StoryWord storyWord = new StoryWord(wordType,firstBraceIndex,lastBraceIndex);
            words.add(storyWord);
            // Remove the story text at this point
            tempStory = tempStory.substring(lastBraceIndex+1);
        }
    }

    /**
     * Returns the version of the story with the completed values.
     */
    public String getCompletedStory(){
        // Store the Story into a temporary string value
        StringBuilder tempStory = new StringBuilder(text);
        for (int i = 0; i < words.size(); i++){
            // Get the words in order, remove the [] in the story, and replace them with the user's input
            StoryWord sw = words.get(i);
            int firstIndex = tempStory.indexOf("[");
            int lastIndex = tempStory.indexOf("]")+1;
            // I use <b> tags to bold the user's selected word
            tempStory.replace(firstIndex,lastIndex,"<b>"+sw.getValue()+"</b>");
        }

        return tempStory.toString();
    }

    /**
     * Inner class to represent a submodel for a particular entry in the story.
     */
    public class StoryWord implements Serializable{
        private String wordType;
        private String value;
        private int startIndex = 0;
        private int endIndex = 0;

        public StoryWord(String type, int startIndex, int endIndex){
            wordType = type;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public String getWordType() {
            return wordType;
        }

        public void setWordType(String wordType) {
            this.wordType = wordType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

    }
}
