package ru.omg_lol.stackover.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.activity.adapter.QuestionAdapter;
import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.questions.GetQuestionsCommand;
import ru.omg_lol.stackover.api.response.questions.GetQuestionsResponse;
import ru.omg_lol.stackover.model.QuestionModel;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.question_list_view)
    ListView mQuestionListView;
    @Bind(R.id.search_edit_text)
    EditText mSearchEditText;
    @Bind(R.id.background_image_view)
    ImageView mBackgroundImageView;

    private QuestionAdapter mQuestionAdapter;
    private ArrayList<QuestionModel> mQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mQuestionAdapter = new QuestionAdapter(this);
        mQuestionAdapter.setData(new ArrayList<QuestionModel>());
        mQuestionListView.setAdapter(mQuestionAdapter);

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence textToSearch, int start, int before, int count) {
                if (!textToSearch.toString().equals("")) {
                    new GetQuestionsCommand(textToSearch.toString()).setListener(new Command.Listener() {
                        @Override
                        public void onCommandExecuted(int resultCode, Object data) {
                            GetQuestionsResponse response = (GetQuestionsResponse) data;
                            mQuestions = GetQuestionsResponse.Question.parseArray(response.items);
                            mQuestionAdapter.setData(mQuestions);
                            mBackgroundImageView.setVisibility(View.INVISIBLE);
                        }
                    }).run();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
