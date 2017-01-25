package ru.omg_lol.stackover.model;

public class OwnerModel {

    private int mUserId;
    private String mUserType;
    private String mDisplayName;
    private int mReputation;
    private int mAcceptRate;
    private String mProfileImage;

    public int getUserId() {
        return mUserId;
    }
    public String getUserType() {
        return mUserType;
    }
    public String getUserName() {
        return mDisplayName;
    }
    public int getReputation() {
        return mReputation;
    }
    public int getAcceptRate() {
        return mAcceptRate;
    }
    public String getProfileImage() {
        return mProfileImage;
    }

    public void setUserId(int newValue) {
        mUserId = newValue;
    }
    public void setUserType(String newValue) {
        mUserType = newValue;
    }
    public void setUserName(String newValue) {
        mDisplayName = newValue;
    }
    public void setReputation(int newValue) {
        mReputation = newValue;
    }
    public void setAcceptRate(int newValue) {
        mAcceptRate = newValue;
    }
    public void setProfileImage(String newValue) {
        mProfileImage = newValue;
    }
}
