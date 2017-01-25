package ru.omg_lol.stackover.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuestionModel {

    private String[] mTags;
    private OwnerModel mOwner;
    private boolean mIsAnswered;
    private int mViewCount;
    private int mAnswerCount;
    private int mScore;
    private int mCreationTimestamp;
    private int mQuestionId;
    private String mTitle;

    public String[] getTags() {
        return mTags;
    }
    public void setTags(String[] newValue) {
        mTags = newValue;
    }

    public OwnerModel getOwner() {
        return mOwner;
    }
    public void setOwner(OwnerModel newValue) {
        mOwner = newValue;
    }

    public boolean getIsAnswered() {
        return mIsAnswered;
    }
    public void setIsAnswered(boolean newValue) {
        mIsAnswered = newValue;
    }

    public int getAnswerCount() {
        return mAnswerCount;
    }
    public void setAnswerCount(int newValue) {
        mAnswerCount = newValue;
    }

    public int getViewCount() {
        return mViewCount;
    }
    public void setViewCount(int newValue) {
        mViewCount = newValue;
    }

    public int getScore() {
        return mScore;
    }
    public void setScore(int newValue) {
        mScore = newValue;
    }

    public String getCreationDate() {
        try{
            DateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);
            Date netDate = (new Date((long) mCreationTimestamp * 1000));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }
    public void setCreationTimestamp(int newValue) {
        mCreationTimestamp = newValue;
    }

    public int getId() {
        return mQuestionId;
    }
    public void setId(int newValue) {
        mQuestionId = newValue;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String newValue) {
        mTitle = newValue;
    }
}
