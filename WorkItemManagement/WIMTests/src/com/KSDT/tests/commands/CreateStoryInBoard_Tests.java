package com.KSDT.tests.commands;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateBugInBoard;
import com.KSDT.commands.creation.CreateStoryInBoard;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateStoryInBoard_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Team testTeam;
    private WorkItem testStory;
    private Board testBoard;


    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateStoryInBoard(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testStory = new StoryImpl("testStory1", StatusType.STORY_INPROGRESS, "asd asd asd", PriorityType.HIGH, SizeType.MEDIUM);
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
        testList.add("testStory1");
        testList.add("InProgress");
        testList.add("Low");
        testList.add("Small");
        testList.add("Story which is very cool");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testStory1");
        testList.add("InProgress");
        testList.add("Low");
        testList.add("Small");
        testList.add("Story which is very cool");
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
        testList.add("testStory1");
        testList.add("InProgress");
        testList.add("Low");
        testList.add("Small");
        testList.add("Story which is very cool");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testBoard.getWorkItemsList().size());
    }

}
