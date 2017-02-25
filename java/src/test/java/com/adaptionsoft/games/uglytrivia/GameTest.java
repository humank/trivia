package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by kim on 2017/2/24.
 */
public class GameTest {

    //TODO-user-intent : the game should be over if a player rolls the dice and answers each question correctly for 6 times

    //TODO-user-intent : the game should be over if a player rolls the dice for 7 times and answer the question wrongly for 1 time followed by an odd rolling number but then correctly for 6 times.

    //TODO-user-intent : the game should be over if a player rolls the diec for 8 times and answers the question wrongly for 1 time followed by an even rolling number but then correctly for 7 times.

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

}