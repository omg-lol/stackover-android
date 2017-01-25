package ru.omg_lol.stackover.api.facade.questions;

import ru.omg_lol.stackover.api.facade.common.ApiException;
import ru.omg_lol.stackover.api.facade.common.Facade;
import ru.omg_lol.stackover.api.response.questions.GetAnswersResponse;
import ru.omg_lol.stackover.api.response.questions.GetQuestionsResponse;

public class QuestionsFacade extends Facade {
    private static QuestionsFacade sInstance;

    public static GetQuestionsResponse getQuestions(String key) throws ApiException {
        return getInstance().get(GetQuestionsResponse.class, "search?order=desc&sort=activity&intitle=" + key + "&site=stackoverflow");
    }

    public static GetAnswersResponse getQuestion(int id) throws ApiException {
        return getInstance().get(GetAnswersResponse.class, "questions/" + id + "?order=desc&sort=activity&site=stackoverflow&filter=!-*f(6rc.(Xr5");
    }

    protected static QuestionsFacade getInstance() {
        if (sInstance == null) {
            sInstance = new QuestionsFacade();
        }

        return sInstance;
    }
}
