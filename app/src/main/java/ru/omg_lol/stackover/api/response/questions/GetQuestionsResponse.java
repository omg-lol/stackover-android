package ru.omg_lol.stackover.api.response.questions;

import java.util.ArrayList;

import ru.omg_lol.stackover.api.response.ApiResponse;
import ru.omg_lol.stackover.model.OwnerModel;
import ru.omg_lol.stackover.model.QuestionModel;

public class GetQuestionsResponse extends ApiResponse {
    public Question[] items;

    public static class Question {
        public String[] tags;
        public Owner owner;
        public boolean is_answered;
        public int view_count;
        public int answer_count;
        public int score;
        public int creation_date;
        public int question_id;
        public String title;

        public QuestionModel parse() {
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
    public static class Owner {
        public int reputation;
        public int user_id;
        public int accept_rate;
        public String user_type;
        public String display_name;
        public String profile_image;

        public OwnerModel parse() {
            return new OwnerModel(user_id, user_type, display_name, reputation, accept_rate, profile_image);
        }
    }
}
