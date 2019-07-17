package com.KSDT.tests.commands;

import com.KSDT.commands.change.ChangeFeedRatingCommand;
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
import com.KSDT.models.items.FeedbackImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ChangeFeedRatingCommand_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Feedback testFeed;
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
        testCommand = new ChangeFeedRatingCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testTeam1 = new TeamImpl("testTeam1");
        testPerson = new PersonImpl("nameasd");
        testPerson1 = new PersonImpl("nameasd1");
        testBoard = new BoardImpl("testBoard", testTeam);
        testBoard1 = new BoardImpl("testBoard1", testTeam1);
        testFeed = new FeedbackImpl("testFeedback", StatusType.FEEDBACK_SCHEDULED, "asd asd asd", 5);
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
        testList.add("testFeedback");
        testList.add("12");
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
        testList.add("testFeedback");
        testList.add("12");
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
        testList.add("testFeedback");
        testList.add("12");
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
        testList.add("testFeedback");
        testList.add("12");
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
        testList.add("testFeedback");
        testList.add("12");
        testList.add("nameasd");
        repository.addBoard(testBoard);
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testFeedback", testFeed);
        repository.addBoard(testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenRatingIsTheSame() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testFeedback");
        testList.add("5");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        repository.addFeedback(testFeed);
        repository.addPerson("nameasd", testPerson);
        testTeam.addPerson("nameasd", testPerson);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testFeedback", testFeed);

        //Act & Assert
        testCommand.execute(testList);
    }



    @Test
    public void execute_Should_ChangeFeedbackRatingWhenInputIsValid() {
        // Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testTeam");
        testList.add("testBoard");
        testList.add("testFeedback");
        testList.add("12");
        testList.add("nameasd");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        repository.addFeedback(testFeed);
        repository.addPerson("nameasd", testPerson);
        testTeam.addPerson("nameasd", testPerson);
        testTeam.addBoard("testBoard", testBoard);
        testBoard.addWorkItem("testFeedback", testFeed);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(12, testFeed.getRating());

    }


}
