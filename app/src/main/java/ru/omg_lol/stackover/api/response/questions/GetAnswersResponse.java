package ru.omg_lol.stackover.api.response.questions;

import java.util.ArrayList;

import ru.omg_lol.stackover.api.response.ApiResponse;
import ru.omg_lol.stackover.model.AnswerModel;
import ru.omg_lol.stackover.model.OwnerModel;
import ru.omg_lol.stackover.model.QuestionModel;


public class GetAnswersResponse extends ApiResponse {
    public Question[] items;

    public static class Question {
        String[] tags;
        Owner owner;
        Answer[] answers;
        boolean is_answered;
        int view_count;
        int answer_count;
        int score;
        int creation_date;
        int question_id;
        String title;

        QuestionModel parse() {
            QuestionModel questionModel = new QuestionModel();
            questionModel.setTags(tags);
            questionModel.setOwner(owner.parse());
            questionModel.setIsAnswered(is_answered);
            questionModel.setAnswerCount(answer_count);
            questionModel.setViewCount(view_count);
            questionModel.setCreationTimestamp(creation_date);
            questionModel.setScore(score);
            questionModel.setId(question_id);
            questionModel.setTitle(title);
            questionModel.setAnswers(Answer.parseArray(answers));
            return questionModel;
        }

        public static ArrayList<QuestionModel> parseArray(Question[] questions) {
            ArrayList<QuestionModel> questionList = new ArrayList<>();

            for (Question question : questions) {
                questionList.add(question.parse());
            }

            return questionList;
        }
    }
    private static class Owner {
        int reputation;
        int user_id;
        int accept_rate;
        String user_type;
        String display_name;
        String profile_image;

        OwnerModel parse() {
            return new OwnerModel(user_id, user_type, display_name, reputation, accept_rate, profile_image);
        }
    }
    private static class Answer {
        int score;
        Owner owner;
        int answer_id;
        int creation_date;
        String body;

        AnswerModel parse() {
            AnswerModel answerModel = new AnswerModel();
            answerModel.setOwner(owner.parse());
            answerModel.setCreationTimestamp(creation_date);
            answerModel.setScore(score);
            answerModel.setId(answer_id);
            answerModel.setBody(body);
            return answerModel;
        }

        public static ArrayList<AnswerModel> parseArray(Answer[] answers) {
            ArrayList<AnswerModel> answerList = new ArrayList<>();

            for (Answer answer : answers) {
                answerList.add(answer.parse());
            }

            return answerList;
        }
    }
}
