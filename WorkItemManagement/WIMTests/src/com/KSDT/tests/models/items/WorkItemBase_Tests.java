package com.KSDT.tests.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.common.Pair;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import com.KSDT.models.items.FeedbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class WorkItemBase_Tests {
    private WorkItem test_item;
    private Person test_person;

    @Before
    public void before() {
        test_person=new PersonImpl("pesho");
        test_item=new FeedbackImpl("feedbackblabla", StatusType.FEEDBACK_SCHEDULED,"random_description",10);

    }

    @Test
    public void getWorkItems_Should_ReturnShallowCopy() {
        //Arrange, Act
        List<Pair<Person,String>> supposedShallowCopy = test_item.getComments();
       test_item.addComment(test_person,"I like this!");

        // Assert
        Assert.assertEquals(0, supposedShallowCopy.size());
    }

    @Test
    public void changeStatus_Should_BeSuccessfulWhenStatusTypeIsCorrect(){
        //Arrange, Act
        test_item.changeStatus(test_person,StatusType.FEEDBACK_SCHEDULED);

        //Assert
        Assert.assertEquals(StatusType.FEEDBACK_SCHEDULED,test_item.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void changeStatus_Should_ThrowExceptionWhenStatusTypeIsIncorrect(){
        //Arrange, Act
        test_item.changeStatus(test_person,StatusType.STORY_DONE);

        //Assert
        Assert.assertEquals(null,test_item.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addToHistory_Should_ThrowExceptionWhenChangeIsEmptyString(){
        //Arrange, Act, Assert
        test_item.addToHistory(test_person,"");
    }

    @Test
    public void addToHistory_Should_BeSuccessfulWhenChangeIsCorrectString(){
        //Arrange, Act, Assert
        test_item.addToHistory(test_person,"Feedback has changed!");
    }

}
