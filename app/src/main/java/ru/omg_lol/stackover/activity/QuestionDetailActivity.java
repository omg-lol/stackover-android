package ru.omg_lol.stackover.activity;

import android.os.Bundle;

import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.questions.GetAnswersCommand;


public class QuestionDetailActivity extends BaseActivity {
    private int mQuestionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestionId = getIntent().getIntExtra("question_id", 0);

        new GetAnswersCommand(mQuestionId).setListener(new Command.Listener() {
            @Override
            public void onCommandExecuted(int resultCode, Object data) {

            }
        }).run();
    }
}
