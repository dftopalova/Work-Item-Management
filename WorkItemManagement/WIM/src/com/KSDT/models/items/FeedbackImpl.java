package com.KSDT.models.items;

import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.ValidationHelper;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Person;
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
    public int getRating() {
        return rating;
    }

    @Override
    public void changeRating(Person person, int newRating) {
        ValidationHelper.positiveCheck(newRating);
        String change = HistoryHelper.collectChange(getRating(), newRating, "rating");

        addToHistory(person, change);
        person.addToPersonHistory(String.format("Person %s changed rating to work item %s.", person.getName(), this.getTitle()));
        setRating(newRating);
    }

    @Override
    public String additionalInfo() {
        return String.format("Rating: %d" +
                        System.lineSeparator() +
                        "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator(), getRating());
    }
}
