package com.KSDT.models.items;

import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.enums.StatusType;

public class FeedbackImpl extends WorkItemBase implements Feedback {
    private static final String DESCRIPTION_LENGTH_EXCEPTION = "Description must be between 10 and 500 symbols!";
    private static final int DESCRIPTION_LENGTH_MAX = 500;
    private static final int DESCRIPTION_LENGTH_MIN = 10;

    private String description;
    private int rating;

    public FeedbackImpl(String title, StatusType status, String description, int rating) {
        super(title, status);
        setDescription(description);
        setRating(rating);
    }

    private void setRating(int rating) {
        ValidationHelper.nullCheck(rating);
        ValidationHelper.positiveCheck(rating);
        this.rating = rating;
    }

    private void setDescription(String description) {
        ValidationHelper.nullCheck(description);
        ValidationHelper.lengthCheck(description, DESCRIPTION_LENGTH_MIN, DESCRIPTION_LENGTH_MAX, DESCRIPTION_LENGTH_EXCEPTION);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getRating() {
        return 0;
    }
}
