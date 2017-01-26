package ru.omg_lol.stackover.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.model.QuestionModel;

public class QuestionAdapter extends BaseAdapter {
    private ArrayList<QuestionModel> mItems;
    private Context mContext;

    public QuestionAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setData(ArrayList<QuestionModel> list) {
        mItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public QuestionModel getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.question_item, parent, false);
        }
        TextView scoreCountTextView = (TextView) view.findViewById(R.id.votes_count_text_view);
        TextView answersCountTextView = (TextView) view.findViewById(R.id.answers_count_text_view);
        TextView questionTitleTextView = (TextView) view.findViewById(R.id.question_title_text_view);
        TextView tagsTextView = (TextView) view.findViewById(R.id.tags_text_view);
        TextView askedAtTextView = (TextView) view.findViewById(R.id.asked_at_text_view);

        QuestionModel question = getItem(position);

        scoreCountTextView.setText(String.format(Locale.ROOT, "%d", question.getScore()));
        answersCountTextView.setText(String.format(Locale.ROOT, "%d", question.getAnswerCount()));
        questionTitleTextView.setText(String.format(Locale.ROOT, "%s", question.getTitle()));
        tagsTextView.setText(Arrays.toString(question.getTags()).replaceAll("\\[|\\]", ""));
        askedAtTextView.setText("asked " + question.getCreationDate() + " by " + question.getOwner().getUserName());

        return view;
    }
}
