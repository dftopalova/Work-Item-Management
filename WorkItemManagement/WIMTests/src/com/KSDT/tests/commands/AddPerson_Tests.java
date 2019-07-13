package com.KSDT.tests.commands;

import com.KSDT.commands.addition.AddPersonCommand;
import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateTeamCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AddPerson_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Team testTeam;
    private Person testPerson;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateTeamCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testPerson = new PersonImpl("nameasd");
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedLessArguments() {
        // Arrange
        testCommand = new AddPersonCommand(repository, factory);
        List<String> testList = new ArrayList<>();

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedMoreArguments() {
        // Arrange
        testCommand = new AddPersonCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenTeamDoesNotExist() {
        // Arrange
        testCommand = new AddPersonCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("nameasd");
        testList.add("testTeam");
        repository.addPerson("nameasd", testPerson);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPersonDoesNotExist() {
        // Arrange
        testCommand = new AddPersonCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("nameasd");
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_addPersonToTeam() {
        // Arrange
        testCommand = new AddPersonCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("nameasd");
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);
        repository.addPerson("nameasd", testPerson);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, repository.getTeams().get("testTeam").getMembersList().size());
    }
}
