package ru.omg_lol.stackover.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.activity.adapter.QuestionAdapter;
import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.database.DBHelper;


public class FavoritesActivity extends BaseActivity {
    @Bind(R.id.question_list_view)
    ListView mQuestionListView;
    @Bind(R.id.empty_favorites)
    TextView mEmptyFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        QuestionAdapter questionAdapter = new QuestionAdapter(this);
        questionAdapter.setData(DBHelper.getItemsFromQuestions());
        mQuestionListView.setAdapter(questionAdapter);
        if (DBHelper.getQuestionsCount() != 0) {
            mEmptyFavorites.setVisibility(View.GONE);
        }

        mQuestionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoritesActivity.this, QuestionDetailActivity.class);
                intent.putExtra("question_id", (int) l);
                intent.putExtra("from_database", true);
                startActivity(intent);
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
