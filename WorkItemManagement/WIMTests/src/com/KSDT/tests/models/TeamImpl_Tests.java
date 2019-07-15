package com.KSDT.tests.models;

import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamImpl_Tests {
    Person test_person;
    Team test_team;
    Board test_board;

    @Before
    public void before(){
        test_person=new PersonImpl("Valentin");
        test_team=new TeamImpl("test_team");
        test_board=new BoardImpl("test_board",test_team);
    }

    @Test
    public void getMemeber_Should_ReturnMemberWhenPassedPresentMember() {
        //Arrange
        test_team.addPerson("Valentin",test_person);

        //Act, Assert
        Assert.assertEquals(test_team.getMember("Valentin"),test_person);
    }

    @Test
    public void getBoard_Should_ReturnBoardWhenPassedPresentBoard() {
        //Arrange
        test_team.addBoard("test_board",test_board);

        //Act, Assert
        Assert.assertEquals(test_team.getBoard("test_board"),test_board);
    }
}
