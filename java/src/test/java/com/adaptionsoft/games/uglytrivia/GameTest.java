package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by kim on 2017/2/24.
 */
public class GameTest {

    @Test
    public void the_game_should_be_over_if_a_player_rolls_the_dice_and_answers_each_question_correctly_for_6_times() {

        //Arrange
        Game game = new Game();
        game.add("Chet");
        boolean isGameStillInProgress = true;

        //Action
        for (int i = 0; i < 6; i++) {
            game.roll(i);
            isGameStillInProgress = game.wasCorrectlyAnswered();
        }

        //Assert
        assertFalse(isGameStillInProgress);
    }

    @Test
    public void the_game_should_be_over_if_a_player_rolls_the_dice_for_7_times_and_answers_the_question_wrongly_for_1_time_followed_by_an_odd_rolling_number_but_then_correctly_for_6_times() {

        //Arrange
        Game game = new Game();
        game.add("Chet");
        boolean isGameStillInProgress = true;

        //Act
        game.roll(1);
        game.wrongAnswer();
        game.roll(1);
        game.wasCorrectlyAnswered();
        for(int i=0; i<5;i++){
            game.roll(1);
            isGameStillInProgress = game.wasCorrectlyAnswered();
        }

        //Assert
        assertFalse(isGameStillInProgress);
    }

    @Test
    public void the_game_should_be_over_if_a_player_rolls_the_dice_for_8_times_and_answers_the_question_wrongly_for_1_time_followed_by_an_even_rolling_number_but_then_correctly_for_7_times_with_odd_rolling_numbers(){

        Game game = new Game();
        game.add("Chet");
        boolean isGameStillInProgress = true;

        game.roll(1);
        game.wrongAnswer();
        game.roll(2);
        game.wasCorrectlyAnswered();
        for(int i=0;i<6;i++){
            game.roll(1);
            isGameStillInProgress = game.wasCorrectlyAnswered();
        }

        assertFalse(isGameStillInProgress);
    }

    @Test
    public void add_two_pop_questions_and_could_remove_the_first_one(){

        QuestionMaker questionMaker = new QuestionMaker();
        questionMaker.addPopQuestion("Pop Question 1");
        questionMaker.addPopQuestion("Pop Question 2");
        assertEquals("Pop Question 1", questionMaker.removeFirstPopQuestion());
    }

    @Test
    public void add_two_science_questions_and_could_remove_the_first_one(){
        QuestionMaker questionMaker = new QuestionMaker();
        questionMaker.addScienceQuestion("Science Question 1");
        questionMaker.addScienceQuestion("Science Question 2");
        assertEquals("Science Question 1", questionMaker.removeFirstScienceQuestion());
    }

    @Test
    public void add_two_sport_questions_and_could_remove_the_first_one(){
        QuestionMaker questionMaker = new QuestionMaker();
        questionMaker.addSportsQuestion("Sport Question 1");
        questionMaker.addSportsQuestion("Sport Question 2");
        assertEquals("Sport Question 1", questionMaker.removeFirstSportsQuestion());
    }

    @Test
    public void add_two_rock_questions_and_could_remove_the_first_one(){
        QuestionMaker questionMaker = new QuestionMaker();
        questionMaker.addRockQuestion("Rock Question 1");
        questionMaker.addRockQuestion("Rock Question 2");
        assertEquals("Rock Question 1", questionMaker.removeFirstRockQuestion());
    }

}