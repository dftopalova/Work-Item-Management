package com.KSDT.tests.commands.creation;

import com.KSDT.commands.addition.AddPersonCommand;
import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateBugInBoard;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateBugInBoard_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Team testTeam;
    private WorkItem testBug;
    private Board testBoard;


    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateBugInBoard(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testBug = new BugImpl("testBug123", StatusType.BUG_ACTIVE, "asd asd asd", "asd/asd/asd/asd", PriorityType.HIGH, SeverityType.CRITICAL);
        testBoard = new BoardImpl("testBoard", testTeam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedLessArguments() {
        // Arrange
        List<String> testList = new ArrayList<>();
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
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenTeamDoesntExist() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("active");
        testList.add("step1/step2/step3");
        testList.add("High");
        testList.add("Critical");
        testList.add("\"Nqkvo bugche deto trqbva da se opravi\"");


        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("active");
        testList.add("step1/step2/step3");
        testList.add("High");
        testList.add("Critical");
        testList.add("\"Nqkvo bugche deto trqbva da se opravi\"");
        repository.addTeam("testTeam", testTeam);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_CreateBugWhenInputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("active");
        testList.add("step1/step2/step3");
        testList.add("High");
        testList.add("Critical");
        testList.add("\"Nqkakvo bugche koeto trqbva da se opravi\"");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testBoard.getWorkItemsList().size());
        Assert.assertEquals(1, repository.getBugMap().size());
    }

}
