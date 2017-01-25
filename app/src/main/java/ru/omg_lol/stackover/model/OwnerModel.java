package ru.omg_lol.stackover.model;

public class OwnerModel {

    private int mUserId;
    private String mUserType;
    private String mDisplayName;
    private int mReputation;
    private int mAcceptRate;
    private String mProfileImage;

    public OwnerModel(int userId, String userType, String displayName, int reputation, int acceptRate, String profileImage) {
        mUserId = userId;
        mUserType = userType;
        mDisplayName = displayName;
        mReputation = reputation;
        mAcceptRate = acceptRate;
        mProfileImage = profileImage;
    }

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
}
