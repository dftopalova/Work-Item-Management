package com.KSDT.tests.commands.change;

import com.KSDT.commands.change.ChangeBugSeverityCommand;
import com.KSDT.commands.contracts.Command;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeBugSeverity_Tests {
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
        testCommand = new ChangeBugSeverityCommand(repository, factory);
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
        testList.add("asdasd");

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
        testList.add("major");
        testList.add("nameasd");

        //Act & Assert
        testCommand.execute(testList);
    }
    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("major");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntContainWorkItem() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("major");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

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
        testList.add("major");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenSeverityIsTheSame() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("critical");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        repository.addTeam("testTeam", testTeam);
        repository.addBug(testBug);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);
        testTeam.addPerson("nameasd", testPerson);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_ChangeBugSeverityWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testBug123");
        testList.add("major");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        repository.addTeam("testTeam", testTeam);
        repository.addBug(testBug);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testBug123", testBug);
        testTeam.addPerson("nameasd", testPerson);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals("major", testBug.getSeverity().toString().toLowerCase());
    }

}
