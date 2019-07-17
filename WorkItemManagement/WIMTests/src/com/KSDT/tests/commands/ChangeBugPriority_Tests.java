package com.KSDT.tests.commands;

import com.KSDT.commands.change.ChangePriorityCommand;
import com.KSDT.commands.contracts.Command;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeBugPriority_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Bug testBug;
    private Team testTeam;
    private Team testTeam1;
    private Person testPerson;
    private Person testPerson1;
    private Board testBoard;
    private Board testBoard1;


    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new ChangePriorityCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testTeam1 = new TeamImpl("testTeam1");
        testPerson = new PersonImpl("nameasd");
        testPerson1 = new PersonImpl("nameasd1");
        testBoard = new BoardImpl("testBoard", testTeam);
        testBoard1 = new BoardImpl("testBoard1", testTeam1);
        testBug = new BugImpl("testBug123", StatusType.BUG_ACTIVE, "asd asd asd", "asd/asd/asd/asd", PriorityType.HIGH, SeverityType.CRITICAL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedLessArguments() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedMoreArguments() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenFeedbackIsGiven() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("feedback");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("medium");
        testList.add("nameasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("bug");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("medium");
        testList.add("nameasd");

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntContainWorkItem() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("0");
        testList.add("testBug123");
        testList.add("medium");
        testList.add("nameasd");
        repository.addBoard(testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenMemberNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("0");
        testList.add("testBug123");
        testList.add("medium");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);
        repository.addBoard(testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPriorityIsTheSame() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("0");
        testList.add("testBug123");
        testList.add("high");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);
        repository.addBoard(testBoard);
        testTeam.addPerson("nameasd", testPerson);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_ChangePriorityWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("0");
        testList.add("testBug123");
        testList.add("medium");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);
        repository.addBoard(testBoard);
        testTeam.addPerson("nameasd", testPerson);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals("MEDIUM", testBug.getPriority().toString().toUpperCase());
    }

}
