package com.KSDT.tests.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StoryImpl_Tests {
    private Story test_item;
    private Person test_person;

    @Before
    public void before() {
        test_person=new PersonImpl("pesho");
        test_item= new StoryImpl("story123456", StatusType.STORY_DONE,"random description", PriorityType.MEDIUM, SizeType.SMALL);
    }

    @Test
    public void changeSize_Should_BeSuccessfulWhenPassedCorrectSizeType(){
        //Arrange,Act
        test_item.changeSize(test_person,SizeType.LARGE);

        //Assert
        Assert.assertEquals(SizeType.LARGE,test_item.getSize());
        Assert.assertEquals("[Person pesho changed size to work item story123456.]",test_person.getHistory().toString());
    }
}
