package ru.omg_lol.stackover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.activity.adapter.QuestionAdapter;
import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.questions.GetQuestionsCommand;
import ru.omg_lol.stackover.api.response.questions.GetQuestionsResponse;
import ru.omg_lol.stackover.database.DBHelper;
import ru.omg_lol.stackover.model.QuestionModel;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.question_list_view)
    ListView mQuestionListView;
    @Bind(R.id.search_edit_text)
    EditText mSearchEditText;
    @Bind(R.id.background_image_view)
    ImageView mBackgroundImageView;
    @Bind(R.id.favorites_count_text_view)
    TextView mFavoritesTextView;

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

        mQuestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, QuestionDetailActivity.class);
                intent.putExtra("question_id", (int) l);
                startActivity(intent);
            }
        });
        mFavoritesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFavoritesTextView.setText(String.format(Locale.ROOT, "%d", DBHelper.getQuestionsCount()));
    }
}
