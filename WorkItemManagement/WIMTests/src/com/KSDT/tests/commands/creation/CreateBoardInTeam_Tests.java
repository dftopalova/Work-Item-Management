package com.KSDT.tests.commands.creation;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateBoardInTeamCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardInTeam_Tests {
    private Command testCommand;
    private WorkItemRepository repository;
    private WorkItemFactory factory;
    private Team testTeam;
    private Board testBoard;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        factory = new WorkItemFactoryImpl();
        testCommand = new CreateBoardInTeamCommand(repository, factory);
        testTeam = new TeamImpl("testTeam");
        testBoard = new BoardImpl("testBoard", testTeam);
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

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenTeamDoesntExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testTeam");

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenBoardAlreadyExist() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);
        testTeam.addBoard("testBoard", testBoard);

        //Act & Assert
        testCommand.execute(testList);
    }

    @Test
    public void execute_Should_CreateBoardWhenInputIsValid() {
        //Arrange
        List<String> testList = new ArrayList<>();
        testList.add("testBoard");
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);

        //Act
        testCommand.execute(testList);

        //Assert
        Assert.assertEquals(1, testTeam.getBoardsList().size());
        Assert.assertEquals(1, repository.getBoardsList().size());
    }


}
