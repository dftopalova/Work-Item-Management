package com.KSDT.tests.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.listing.ShowAllPeopleCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.contracts.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.KSDT.commands.CommandConstants.NO_REGISTERED_PERSONS_MESSAGE;

public class ShowAllPeopleCommand_Tests {
     WorkItemRepository repository;
    Command testCommand;
    List<String> testList;
    Person testPerson;
    Person testPerson1;

    @Before
    public void before(){
        testList = new ArrayList();
        repository=new WorkItemRepositoryImpl();
        testCommand = new ShowAllPeopleCommand(repository);
        testPerson = new PersonImpl("testName1");
        testPerson1 = new PersonImpl("testName2");
    }

    @Test
    public void executeShouldReturnErrorMessage() {
        //Arrange & Act
        String testResult = testCommand.execute(testList);

        //Assert
        Assert.assertEquals(NO_REGISTERED_PERSONS_MESSAGE, testResult );
    }

    @Test
    public void executeShouldReturnPersonsNames() {
        //Arrange
        repository.addPerson("testName1", testPerson);
        repository.addPerson("testName2", testPerson1);
        String testResult = testCommand.execute(testList);

        StringBuilder strBuild = new StringBuilder();
        strBuild.append("List of names of all people:" +
                System.lineSeparator() + "testName1 testName2 ");

        //Act & Assert
        Assert.assertEquals(strBuild.toString(), testResult );
    }

}
