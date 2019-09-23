package com.test.app.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FrameTest {

    private static final Integer FRAME_NUMBER = 1;
    private static final Integer LAST_FRAME_NUMBER = 10;
    private static final Integer NOT_VALID_FRAME_NUMBER = 11;
    private static final String VALID_CHANCE = "8";
    private static final String VALID_CHANCE_1 = "1";
    private static final String VALID_CHANCE_2 = "2";
    private static final String VALID_CHANCE_STRIKE = "10";
    private static final String NOT_VALID_CHANCE = "A";
    private static final String NEGATIVE_CHANCE = "-4";
    private static final String TOO_HIGH_CHANCE = "12";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void GIVEN_valid_chance_WHEN_adding_chance_to_frame_THEN_chance_is_added() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);

        // WHEN
        frame.addChance(VALID_CHANCE);

        // THEN
        Assert.assertThat(frame.getRolls().size(), Matchers.equalTo(1));
    }

    @Test
    public void GIVEN_not_valid_chance_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = String.format(
                "Chance value %s not valid in frame %d.", NOT_VALID_CHANCE, FRAME_NUMBER);
        Frame frame = new Frame(FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(NOT_VALID_CHANCE);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_not_valid_frame_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = "The frame is more than the max number of frames allowed.";
        Frame frame = new Frame(NOT_VALID_FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(VALID_CHANCE);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_negative_chance_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = String.format(
                "Cannot add chance %s in frame %d because it's negative.", NEGATIVE_CHANCE, FRAME_NUMBER);
        Frame frame = new Frame(FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(NEGATIVE_CHANCE);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_too_high_chance_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message =String.format(
                "Cannot add chance %s in frame %d because it's higher than the max number " +
                        "of possible pin hits in a roll.", TOO_HIGH_CHANCE, FRAME_NUMBER);
        Frame frame = new Frame(FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(TOO_HIGH_CHANCE);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_two_chances_sum_too_high_WHEN_adding_second_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = String.format(
                "Cannot add chance %s in frame %d because total of frame is higher " +
                        "than the max number of possible pin hits in a frame.", VALID_CHANCE, FRAME_NUMBER);

        Frame frame = new Frame(FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(VALID_CHANCE);
        frame.addChance(VALID_CHANCE);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_three_chances_for_not_last_frame_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = String.format("Cannot add a new chance in frame %d because it's complete.", FRAME_NUMBER);
        Frame frame = new Frame(FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(VALID_CHANCE_1);
        frame.addChance(VALID_CHANCE_2);
        frame.addChance(VALID_CHANCE_2);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_three_chances_no_strike_no_spare_for_last_frame_WHEN_adding_chance_to_frame_THEN_exception_is_thrown() throws Exception {
        // GIVEN
        String message = String.format("Cannot add a new chance in frame %d because it's complete.", LAST_FRAME_NUMBER);
        Frame frame = new Frame(LAST_FRAME_NUMBER);
        expectedException.expect(Exception.class);
        expectedException.expectMessage(message);

        // WHEN
        frame.addChance(VALID_CHANCE_1);
        frame.addChance(VALID_CHANCE_2);
        frame.addChance(VALID_CHANCE_2);

        // THEN
        // Exception is thrown
    }

    @Test
    public void GIVEN_three_chances_spare_for_last_frame_WHEN_adding_chance_to_frame_THEN_chances_are_added() throws Exception {
        // GIVEN
        Frame frame = new Frame(LAST_FRAME_NUMBER);

        // WHEN
        frame.addChance(VALID_CHANCE);
        frame.addChance(VALID_CHANCE_2);
        frame.addChance(VALID_CHANCE_2);

        // THEN
        Assert.assertThat(frame.getRolls().size(), Matchers.equalTo(3));
    }

    @Test
    public void GIVEN_three_chances_strike_for_last_frame_WHEN_adding_chance_to_frame_THEN_chances_are_added() throws Exception {
        // GIVEN
        Frame frame = new Frame(LAST_FRAME_NUMBER);

        // WHEN
        frame.addChance(VALID_CHANCE_STRIKE);
        frame.addChance(VALID_CHANCE_1);
        frame.addChance(VALID_CHANCE_2);

        // THEN
        Assert.assertThat(frame.getRolls().size(), Matchers.equalTo(3));
    }

    @Test
    public void GIVEN_strike_WHEN_adding_chance_to_frame_THEN_frame_is_strike() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);

        // WHEN
        frame.addChance(VALID_CHANCE_STRIKE);

        // THEN
        Assert.assertThat(frame.isStrike(), Matchers.equalTo(true));
    }

    @Test
    public void GIVEN_spare_WHEN_adding_chance_to_frame_THEN_frame_is_spare() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);

        // WHEN
        frame.addChance(VALID_CHANCE);
        frame.addChance(VALID_CHANCE_2);

        // THEN
        Assert.assertThat(frame.isSpare(), Matchers.equalTo(true));
    }

    @Test
    public void GIVEN_not_last_frame_with_strike_WHEN_getting_is_frame_complete_THEN_return_true() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);
        frame.addChance(VALID_CHANCE_STRIKE);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(true));
    }

    @Test
    public void GIVEN_not_last_frame_with_spare_WHEN_getting_is_frame_complete_THEN_return_true() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);
        frame.addChance(VALID_CHANCE);
        frame.addChance(VALID_CHANCE_2);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(true));
    }

    @Test
    public void GIVEN_not_last_frame_with_one_chance_WHEN_getting_is_frame_complete_THEN_return_false() throws Exception {
        // GIVEN
        Frame frame = new Frame(FRAME_NUMBER);
        frame.addChance(VALID_CHANCE);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(false));
    }

    @Test
    public void GIVEN_last_frame_with_one_strike_WHEN_getting_is_frame_complete_THEN_return_false() throws Exception {
        // GIVEN
        Frame frame = new Frame(LAST_FRAME_NUMBER);
        frame.addChance(VALID_CHANCE_STRIKE);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(false));
    }

    @Test
    public void GIVEN_last_frame_with_spare_WHEN_getting_is_frame_complete_THEN_return_false() throws Exception {
        // GIVEN
        Frame frame = new Frame(LAST_FRAME_NUMBER);
        frame.addChance(VALID_CHANCE);
        frame.addChance(VALID_CHANCE_2);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(false));
    }

    @Test
    public void GIVEN_last_frame_with_two_normal_chances_WHEN_getting_is_frame_complete_THEN_return_true() throws Exception {
        // GIVEN
        Frame frame = new Frame(LAST_FRAME_NUMBER);
        frame.addChance(VALID_CHANCE_1);
        frame.addChance(VALID_CHANCE_2);

        // WHEN
        boolean isFrameComplete = frame.isFrameComplete();

        // THEN
        Assert.assertThat(isFrameComplete, Matchers.equalTo(true));
    }

}
