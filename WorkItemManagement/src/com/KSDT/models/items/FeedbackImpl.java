package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.enums.StatusType;

public class FeedbackImpl extends WorkItemBase implements Feedback {


    private int rating;

    public FeedbackImpl(String title, StatusType status, String description, int rating) {
        super(title, status, description);
        setRating(rating);
    }

    private void setRating(int rating) {
        ValidationHelper.positiveCheck(rating);
        this.rating = rating;
    }


    @Override
    public String getType() {
        return "FEED_";
    }

    @Override
    public int getRating() {
        return rating;
    }
}
