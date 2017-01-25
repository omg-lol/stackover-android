package ru.omg_lol.stackover.api.command.questions;

import android.content.Context;

import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.CommandResult;
import ru.omg_lol.stackover.api.facade.common.ApiException;
import ru.omg_lol.stackover.api.facade.questions.QuestionsFacade;
import ru.omg_lol.stackover.api.response.questions.GetAnswersResponse;


public class GetAnswersCommand extends Command {
    private int mId;

    public GetAnswersCommand(int id) {
        mId = id;
    }

    @Override
    protected CommandResult doExecute(Context context) {
        GetAnswersResponse response;

        try {
            response = QuestionsFacade.getQuestion(mId);
        } catch (ApiException e) {
            return handleException(e);
        }

        return CommandResult.createSuccessResult(RESULT_VALUE_SUCCESS, response);
    }
}

