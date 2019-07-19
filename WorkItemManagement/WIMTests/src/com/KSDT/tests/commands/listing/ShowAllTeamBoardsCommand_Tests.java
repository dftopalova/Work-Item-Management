package com.KSDT.tests.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.listing.ShowAllPeopleCommand;
import com.KSDT.commands.listing.ShowAllTeamBoardsCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.KSDT.commands.CommandConstants.NO_BOARDS_IN_TEAM_ERROR;
import static com.KSDT.commands.CommandConstants.NO_REGISTERED_PERSONS_MESSAGE;

public class ShowAllTeamBoardsCommand_Tests {
    WorkItemRepository repository;
    Command testCommand;
    List<String> testList;
    Team testTeam;
    Board testBoard;
    Board testBoard1;

    @Before
    public void before() {
        testList = new ArrayList();
        repository = new WorkItemRepositoryImpl();
        testCommand = new ShowAllTeamBoardsCommand(repository);
        testTeam = new TeamImpl("testTeam");
        testBoard = new BoardImpl("testBoard", testTeam);
        testBoard1 = new BoardImpl("testBoard1", testTeam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenLessArgumentsArePassed() {
        //Arrange, Act & Assert
        testCommand.execute(testList);

    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenMoreArgumentsArePassed() {
        //Arrange
        testList.add("asd");
        testList.add("asd");
        testList.add("asd");

        // Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenTeamDoesntExist() {
        //Arrange
        testList.add("testTeam");

        // Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_ReturnErrorMessageWhenNoBoardsAreInTeam() {
        //Arrange
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);

        // Act
       String result = testCommand.execute(testList);

       //Assert
        Assert.assertEquals(String.format(NO_BOARDS_IN_TEAM_ERROR, testTeam.getName()), result);
    }


    @Test
    public void execute_Should_ReturnCorrectMessage() {
        //Arrange
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);
        repository.addBoard(testBoard);
        testTeam.addBoard("testBoard", testBoard);

        StringBuilder strBuild = new StringBuilder();
        strBuild.append("All Boards in team testTeam:" + System.lineSeparator() + "=====" + System.lineSeparator() + testBoard + "=====" + System.lineSeparator());

        // Act
        String result = testCommand.execute(testList);

        //Assert
        Assert.assertEquals(strBuild.toString(), result);
    }




}
