package com.KSDT.models.contracts;

public interface Feedback {
    int getRating();
    void changeRating(Person person, int newRating);

}
