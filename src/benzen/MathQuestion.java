package benzen;

import java.util.Random;

public class MathQuestion {
    private String question;
    private int correctAnswer;

    public MathQuestion() {
        generateQuestion();
    }

    // addition or multiply question
    private void generateQuestion() {
        Random random = new Random();
        int num1 = random.nextInt(10) + 1;  // random number between 1 and 10
        int num2 = random.nextInt(10) + 1;  // random number between 1 and 10
        int operation = random.nextInt(2);  // randomly pick between types of questions

        if (operation == 0) {
            // addition
            this.question = "What is " + num1 + " + " + num2 + "?";
            this.correctAnswer = num1 + num2;
        } else {
            // multiply
            this.question = "What is " + num1 + " * " + num2 + "?";
            this.correctAnswer = num1 * num2;
        }
    }

    // check if the player's answer is correct
    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }

    public String getQuestion() {
        return question;
    }
}