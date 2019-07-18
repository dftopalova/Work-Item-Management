package com.KSDT.tests.commands.change;

import com.KSDT.commands.change.ChangePriorityCommand;
import com.KSDT.commands.change.ChangeStatusCommand;
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

public class ChangeStatusCommand_Tests {


    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private WorkItem testWorkItem;
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
        testCommand = new ChangeStatusCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testTeam1 = new TeamImpl("testTeam1");
        testPerson = new PersonImpl("nameasd");
        testPerson1 = new PersonImpl("nameasd1");
        testBoard = new BoardImpl("testBoard", testTeam);
        testBoard1 = new BoardImpl("testBoard1", testTeam1);
        testWorkItem = new BugImpl("testBug123", StatusType.BUG_ACTIVE, "asd asd asd", "asd/asd/asd/asd", PriorityType.HIGH, SeverityType.CRITICAL);
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
        testList.add("asdasd");

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenWorkItemNotInBoard() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("fixed");
        testList.add("nameasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenStatusIsNotForWorkItemType() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("cat");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenTeamDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("fixed");
        testList.add("nameasd");

        repository.addBoard(testBoard);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("fixed");
        testList.add("nameasd");

        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenMemberNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("fixed");
        testList.add("nameasd");

        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenStatusIsTheSame() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("active");
        testList.add("nameasd");

        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_ChangeStatusWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("fixed");
        testList.add("nameasd");

        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testBoard.addWorkItem("testBug123", testWorkItem);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals("BUG_FIXED", testWorkItem.getStatus().name());

    }


}
