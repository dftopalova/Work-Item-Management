package com.KSDT.tests.models;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PersonImpl_Tests {
    private WorkItem testItem;

    @Before
    public void before(){
        testItem=new StoryImpl("test_story",
                StatusType.STORY_DONE,
                "One interesting story",
                PriorityType.MEDIUM, SizeType.MEDIUM);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowExceptionWhenNameIsEmpty() {
        //Arrange, Act, Assert
        Person test_person =new PersonImpl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_Should_ThrowExceptionWhenNameLengthIsLessThanMinimum() {
        //Arrange, Act, Assert
        Person test_person=new PersonImpl("d");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_ThrowExceptionWhenNameLengthIsBiggerThanMaximum() {
        //Arrange, Act, Assert
        Person test_person=new PersonImpl("asdfghjklqwertyui");
    }

    @Test
    public void constructor_should_BeSuccessfullWhenNameLengthIsCorrect() {
        //Arrange, Act
        Person test_person=new PersonImpl("Valentin");

        //Assert
        Assert.assertEquals(test_person.getName(), "Valentin");
    }

    @Test
    public void getWorkItems_Should_ReturnShallowCopy() {
        // Arrange
        Person test_person=new PersonImpl("Valentin");

        // Act
        List<WorkItem> supposedShallowCopy = test_person.getWorkItems();
        test_person.addWorkItem(testItem);

        // Assert
        Assert.assertEquals(0, supposedShallowCopy.size());
    }

    @Test
    public void removeWorkItem_Should_RemoveItemWhenPassedPresentItem() {
        // Arrange
        Person test_person=new PersonImpl("Valentin");
        test_person.addWorkItem(testItem);
        // Act
        test_person.removeWorkItem(testItem);

        // Assert
        Assert.assertEquals(0, test_person.getWorkItems().size());
    }

    @Test
    public void getHistory_Should_ReturnShallowCopy() {
        // Arrange
        Person test_person=new PersonImpl("Valentin");

        // Act
        List<String> supposedShallowCopy = test_person.getHistory();
        test_person.addToPersonHistory("Valentin changed size of story test_story.");

        // Assert
        Assert.assertEquals(0, supposedShallowCopy.size());
    }
}
