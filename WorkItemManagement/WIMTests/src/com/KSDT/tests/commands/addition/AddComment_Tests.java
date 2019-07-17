package com.KSDT.tests.commands.addition;

import com.KSDT.commands.addition.AddCommentCommand;
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

public class AddComment_Tests {
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
        testCommand = new AddCommentCommand(repository, factory);
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
    public void execute_Should_ThrowExceptionWhenWorkItemNotInBoard() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("nameasd");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addPerson("nameasd", testPerson);
        repository.addBoard(testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testTeam.addBoard("testBoard", testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("nameasd");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addPerson("nameasd", testPerson);
        testTeam.addPerson("nameasd", testPerson);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("nameasd");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addPerson("nameasd", testPerson);
        repository.addBoard(testBoard);
        testTeam.addPerson("nameasd", testPerson);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPersonNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard1");
        testList.add("testBug123");
        testList.add("nameasd");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard1);
        repository.addPerson("nameasd", testPerson);
        testTeam.addBoard("testBoard", testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPersonDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("nameasd123");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard1);
        repository.addPerson("nameasd1", testPerson1);
        testTeam.addBoard("testBoard", testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_AddCommentWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("nameasd");
        testList.add("What the hell?");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        repository.addPerson("nameasd", testPerson);
        repository.addBug(testBug);
        testTeam.addBoard("testBoard", testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testBoard.addWorkItem("testBug123", testBug);
        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testBug.getComments().size());
    }

}
