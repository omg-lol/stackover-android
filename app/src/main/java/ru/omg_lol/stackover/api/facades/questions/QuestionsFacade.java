package ru.omg_lol.stackover.api.facades.questions;

import ru.omg_lol.stackover.api.facades.common.ApiException;
import ru.omg_lol.stackover.api.facades.common.Facade;
import ru.omg_lol.stackover.api.response.questions.GetQuestionsResponse;

public class QuestionsFacade extends Facade {
    private static QuestionsFacade sInstance;

    public static GetQuestionsResponse getQuestions(String key) throws ApiException {
        return getInstance().get(GetQuestionsResponse.class, "search?order=desc&sort=activity&intitle=" + key + "&site=stackoverflow");
    }

    protected static QuestionsFacade getInstance() {
        if (sInstance == null) {
            sInstance = new QuestionsFacade();
        }

        return sInstance;
    }
}
