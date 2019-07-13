package com.KSDT.tests.commands;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateTeamCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.PersonImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateTeam_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateTeamCommand(repository, factory);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_ShouldThrowExceptionWhenPassedLessArguments() {
        // Arrange
        testCommand = new CreateTeamCommand(repository, factory);
        List<String> testList = new ArrayList<>();

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_ShouldThrowExceptionWhenPassedMoreArguments() {
        // Arrange
        testCommand = new CreateTeamCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_ShouldCreateTeamWHenInputIsValid() {
        // Arrange
        testCommand = new CreateTeamCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, repository.getTeams().size());
    }
}
