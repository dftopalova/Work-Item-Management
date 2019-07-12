package com.KSDT.models.contracts;

public interface Feedback extends WorkItem {

    int getRating();

    void changeRating(Person person, int newRating);

}
