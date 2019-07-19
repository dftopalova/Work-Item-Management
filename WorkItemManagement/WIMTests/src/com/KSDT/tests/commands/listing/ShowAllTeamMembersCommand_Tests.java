package com.KSDT.tests.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.listing.ShowAllTeamBoardsCommand;
import com.KSDT.commands.listing.ShowAllTeamMembersCommand;
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
import static com.KSDT.commands.CommandConstants.NO_MEMBERS_IN_TEAM_ERROR;

public class ShowAllTeamMembersCommand_Tests {

    WorkItemRepository repository;
    Command testCommand;
    List<String> testList;
    Team testTeam;
    Person testPerson1;
    Person testPerson2;

    @Before
    public void before() {
        testList = new ArrayList();
        repository = new WorkItemRepositoryImpl();
        testCommand = new ShowAllTeamMembersCommand(repository);
        testTeam = new TeamImpl("testTeam");
        testPerson1 = new PersonImpl("nameasd");
        testPerson2 = new PersonImpl("nameasd1");
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
    public void execute_Should_ReturnErrorMessageWhenNoMembersAreInTeam() {
        //Arrange
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);

        // Act
        String result = testCommand.execute(testList);

        //Assert
        Assert.assertEquals(String.format(NO_MEMBERS_IN_TEAM_ERROR,testTeam.getName()), result);
    }

    @Test
    public void execute_Should_ReturnCorrectMessage() {
        //Arrange
        testList.add("testTeam");
        repository.addTeam("testTeam", testTeam);
        repository.addPerson("nameasd", testPerson1);
        testTeam.addPerson("nameasd", testPerson1);


        StringBuilder strBuild = new StringBuilder();
        strBuild.append("All Members in team testTeam:"
                + System.lineSeparator() + testPerson1.getName()
                + System.lineSeparator());

        // Act
        String result = testCommand.execute(testList);

        //Assert
        Assert.assertEquals(strBuild.toString(), result);
    }
}
