package ru.omg_lol.stackover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.activity.adapter.AnswerAdapter;
import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.api.command.Command;
import ru.omg_lol.stackover.api.command.questions.GetAnswersCommand;
import ru.omg_lol.stackover.api.response.questions.GetAnswersResponse;
import ru.omg_lol.stackover.database.DBHelper;
import ru.omg_lol.stackover.model.QuestionModel;

public class QuestionDetailActivity extends BaseActivity {

    @Bind(R.id.question_title_text_view)
    TextView mQuestionTextView;
    @Bind(R.id.votes_count_text_view)
    TextView mVotesCountTextView;
    @Bind(R.id.answers_count_text_view)
    TextView mAnswersCountTextView;
    @Bind(R.id.asked_at_text_view)
    TextView mAskedTextView;
    @Bind(R.id.tags_text_view)
    TextView mTagsTextView;

    private ListView mAnswersListView;
    private ImageButton mAddToFavoritesButton;
    private int mQuestionId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        final AppBarLayout barLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setElevation(0);
            actionBar.setTitle("Question");
        }

        Intent intent = getIntent();

        if (intent.hasExtra("question_id")) {
            mQuestionId = getIntent().getIntExtra("question_id", 0);
        }
        mAnswersListView = (ListView) findViewById(R.id.answers_list_view);
        View v = getLayoutInflater().inflate(R.layout.question_layout, mAnswersListView, false);
        ButterKnife.bind(this, v);
        mAnswersListView.addHeaderView(v);

        mAddToFavoritesButton = (ImageButton) findViewById(R.id.favorites_image_button);

        if (intent.getBooleanExtra("from_database", false)) {
            mAddToFavoritesButton.setVisibility(View.GONE);
        }

        new GetAnswersCommand(mQuestionId).setListener(new Command.Listener() {
            @Override
            public void onCommandExecuted(int resultCode, Object data) {
                GetAnswersResponse response = (GetAnswersResponse) data;
                QuestionModel question = GetAnswersResponse.Question.parseArray(response.items).get(0);
                fillUI(question);
                barLayout.bringToFront();
            }
        }).run();
    }

    private void fillUI(final QuestionModel question) {
        mVotesCountTextView.setText(String.format(Locale.ROOT, "%d", question.getScore()));
        mAnswersCountTextView.setText(String.format(Locale.ROOT, "%d", question.getAnswerCount()));
        mQuestionTextView.setText(String.format(Locale.ROOT, "%s", question.getTitle()));
        mTagsTextView.setText(Arrays.toString(question.getTags()).replaceAll("\\[|\\]", ""));
        mAskedTextView.setText("asked " + question.getCreationDate() + " by " + question.getOwner().getUserName());

        AnswerAdapter adapter = new AnswerAdapter(this);
        adapter.setData(question.getAnswers());
        mAnswersListView.setAdapter(adapter);
        mAddToFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper.insertIntoQuestionsAndUsers(question);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                mAddToFavoritesButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
