package com.KSDT.tests.models.items;

import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.AbstractBasicItem;
import com.KSDT.models.items.BugImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractBasicItem_Tests {
    private Person test_person;
    private AbstractBasicItem test_item;

    @Before
    public void before(){
        test_person=new PersonImpl("pesho");
        test_item =new BugImpl("test_bug123456", StatusType.BUG_ACTIVE,"bug description",
                "step1/step2/step3", PriorityType.MEDIUM, SeverityType.CRITICAL);
    }

    @Test
    public void changeSeverity_Should_BeSuccessfulWhenPassedCorrectSeverityType(){
        //Arrange,Act
        test_item.changePriority(test_person,PriorityType.HIGH);

        //Assert
        Assert.assertEquals(PriorityType.HIGH, test_item.getPriority());
        Assert.assertEquals("[Person pesho changed priority to work item test_bug123456.]",test_person.getHistory().toString());
    }
}
