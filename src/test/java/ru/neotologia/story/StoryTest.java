package ru.neotologia.story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class StoryTest {
    static Story story = new Story();


    @Test
    void addStoryEl() {
        int result = story.addStoryEl("Привет");
        Assertions.assertEquals(1, result);
    }


    @Test
    void isEmptyStory() {
        story.addStoryEl("Привет");
        boolean result = Story.isEmptyStory();
        assertTrue(result);
    }
}