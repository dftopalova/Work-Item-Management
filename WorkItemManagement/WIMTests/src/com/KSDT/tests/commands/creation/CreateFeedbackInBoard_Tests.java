package com.KSDT.tests.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateFeedbackInBoard;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.FeedbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateFeedbackInBoard_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Team testTeam;
    private WorkItem testWorkItem;
    private Board testBoard;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateFeedbackInBoard(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testWorkItem = new FeedbackImpl("testFeedback", StatusType.FEEDBACK_SCHEDULED, "asd asd asd", 5);
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
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testFeedback");
        testList.add("Scheduled");
        testList.add("5");
        testList.add("asd asd asd");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testFeedback");
        testList.add("Scheduled");
        testList.add("5");
        testList.add("asd asd asd");
        repository.addTeam("testTeam", testTeam);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_CreateFeedbackWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testFeedback");
        testList.add("Scheduled");
        testList.add("5");
        testList.add("asd asd asd");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testBoard.getWorkItemsList().size());
        Assert.assertEquals(1, testBoard.getWorkItemsList().size());
    }

}
