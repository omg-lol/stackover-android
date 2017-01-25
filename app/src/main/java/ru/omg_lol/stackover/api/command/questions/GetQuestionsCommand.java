package ru.omg_lol.stackover.api.command.questions;

import android.content.Context;

import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.CommandResult;
import ru.omg_lol.stackover.api.facades.common.ApiException;
import ru.omg_lol.stackover.api.facades.questions.QuestionsFacade;
import ru.omg_lol.stackover.api.response.questions.GetQuestionsResponse;


public class GetQuestionsCommand extends Command {

    private String mKey;

    public GetQuestionsCommand(String key) {
        mKey = key;
    }

    @Override
    protected CommandResult doExecute(Context context) {
        GetQuestionsResponse response;

        try {
            response = QuestionsFacade.getQuestions(mKey);
        } catch (ApiException e) {
            return handleException(e);
        }

        return CommandResult.createSuccessResult(RESULT_VALUE_SUCCESS, response);
    }
}
