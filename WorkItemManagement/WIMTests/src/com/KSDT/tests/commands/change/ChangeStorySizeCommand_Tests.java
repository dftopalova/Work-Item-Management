package com.KSDT.tests.commands.change;

import com.KSDT.commands.change.ChangeBugSeverityCommand;
import com.KSDT.commands.change.ChangeStorySizeCommand;
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
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeStorySizeCommand_Tests {


    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Story testStory;
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
        testCommand = new ChangeStorySizeCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testTeam1 = new TeamImpl("testTeam1");
        testPerson = new PersonImpl("nameasd");
        testPerson1 = new PersonImpl("nameasd1");
        testBoard = new BoardImpl("testBoard", testTeam);
        testBoard1 = new BoardImpl("testBoard1", testTeam1);
        testStory = new StoryImpl("testStory1", StatusType.STORY_INPROGRESS, "asd asd asd", PriorityType.HIGH, SizeType.MEDIUM);    }

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
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardNotInTeam() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntContainWorkItem() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
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
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testFeedback", testStory);
        repository.addBoard(testBoard);
        testBoard.addWorkItem("testStory", testStory);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenSizeIsTheSame() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory");
        testList.add("medium");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testBoard.addWorkItem("testFeedback", testStory);
        repository.addBoard(testBoard);
        repository.addStory(testStory);
        testBoard.addWorkItem("testStory", testStory);

        //Act & Assert
        testCommand.execute(testList);
    }


    @Test
    public void execute_Should_ChangeStorySizeWhenInputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory");
        testList.add("large");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);
        testTeam.addPerson("nameasd", testPerson);
        testBoard.addWorkItem("testFeedback", testStory);
        repository.addBoard(testBoard);
        repository.addStory(testStory);
        testBoard.addWorkItem("testStory", testStory);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals("large", testStory.getSize().toString().toLowerCase());
    }
}
