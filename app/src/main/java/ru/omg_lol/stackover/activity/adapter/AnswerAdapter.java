package ru.omg_lol.stackover.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.MaskTransformation;
import ru.omg_lol.stackover.R;
import ru.omg_lol.stackover.model.AnswerModel;


public class AnswerAdapter extends BaseAdapter {
    private ArrayList<AnswerModel> mItems;
    private Context mContext;

    public AnswerAdapter(Context context) {
        super();
        mContext = context;
    }

    public void setData(ArrayList<AnswerModel> list) {
        mItems = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public AnswerModel getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.answer_item, parent, false);
        }
        TextView scoreCountTextView = (TextView) view.findViewById(R.id.score_text_view);
        TextView answerCountTextView = (TextView) view.findViewById(R.id.answer_text_view);
        TextView askedAtTextView = (TextView) view.findViewById(R.id.asked_at_text_view);
        ImageView userPicImageView = (ImageView) view.findViewById(R.id.user_pic_image_view);

        AnswerModel answerModel = getItem(position);

        Picasso.with(mContext)
                .load(answerModel.getOwner().getProfileImage())
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .resizeDimen(R.dimen.list_dimen, R.dimen.list_dimen)
                .transform(new MaskTransformation(mContext, R.drawable.hex))
                .into(userPicImageView);
        scoreCountTextView.setText(String.format(Locale.ROOT, " votes \n %d", answerModel.getScore()));
        answerCountTextView.setText(answerModel.getBody());
        askedAtTextView.setText("answered " + answerModel.getCreationDate() + " by " + answerModel.getOwner().getUserName());

        return view;
    }
}
