package ru.neotologia.story;

import java.io.PrintWriter;
import java.util.LinkedList;

public class Story {
    private static final LinkedList<String> story = new LinkedList<>();

    public int addStoryEl(String el) {
        if (story.size() >= 100) {
            story.removeFirst();
            story.add(el);
        } else {
            story.add(el);
        }
        return story.size();
    }

    public void printStory(PrintWriter writer) {
        if (!story.isEmpty()) {
            for (String vr : story) {
                System.out.println(vr);
                writer.write(vr + "\n");
            }
        }
    }

    public static boolean isEmptyStory() {
        return !story.isEmpty();
    }

}
