package ru.omg_lol.stackover.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AnswerModel {
    private int mId;
    private int mCreationTimestamp;
    private int mScore;
    private OwnerModel mOwner;
    private String mBody;

    public OwnerModel getOwner() {
        return mOwner;
    }
    public void setOwner(OwnerModel newValue) {
        mOwner = newValue;
    }

    public int getScore() {
        return mScore;
    }
    public void setScore(int newValue) {
        mScore = newValue;
    }

    public int getId() {
        return mId;
    }
    public void setId(int newValue) {
        mId = newValue;
    }

    public String getBody() {
        return mBody;
    }
    public void setBody(String newValue) {
        mBody = newValue;
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
}
