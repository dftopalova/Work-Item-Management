package com.KSDT.tests.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.FeedbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FeedbackImpl_Tests {
    private Feedback test_item;
    private Person test_person;

    @Before
    public void before() {
        test_person=new PersonImpl("pesho");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowExceptionWhenPassedInvalidRating(){
        test_item= new FeedbackImpl("feedbackblabla", StatusType.FEEDBACK_SCHEDULED,"random_description",-1);
    }

    @Test
    public void changeRating_Should_BeSuccessfulWhenPassedCorrectNewRating(){
        //Arrange,Act
        test_item= new FeedbackImpl("feedbackblabla", StatusType.FEEDBACK_SCHEDULED,"random_description",5);
        test_item.changeRating(test_person,15);

        //Assert
        Assert.assertEquals(15,test_item.getRating());
        Assert.assertEquals("[Person pesho changed rating to work item feedbackblabla.]",test_person.getHistory().toString());
    }
}
