package com.KSDT.tests.models;

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

public class TeamImpl_Tests {
    Person test_person;
    Team test_team;
    Board test_board;

    @Before
    public void before() {
        test_person = new PersonImpl("Valentin");
        test_team = new TeamImpl("test_team");
        test_board = new BoardImpl("test_board", test_team);
    }

    @Test
    public void getMember_Should_ReturnMemberWhenPassedPresentMember() {
        //Arrange
        test_team.addPerson("Valentin", test_person);

        //Act, Assert
        Assert.assertEquals(test_team.getMember("Valentin"), test_person);
    }

    @Test
    public void getBoard_Should_ReturnBoardWhenPassedPresentBoard() {
        //Arrange
        test_team.addBoard("test_board", test_board);

        //Act, Assert
        Assert.assertEquals(test_team.getBoard("test_board"), test_board);
    }

    @Test
    public void toString_Should_PrintValidInformation() {
        //Arrange
        WorkItem test_bug = new BugImpl("bugche12345",
                StatusType.BUG_ACTIVE,
                "serious bug",
                "step1/step2/step3",
                PriorityType.HIGH, SeverityType.CRITICAL);

        test_person.addWorkItem(test_bug);
        ((BugImpl) test_bug).setAssignee(test_person);

        test_team.addPerson("Valentin", test_person);
        test_team.addBoard("test_board", test_board);

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("Team test_team`s info:" + System.lineSeparator() +
                "Person Valentin`s info:" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: bugche12345" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: serious bug" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Assignee: Valentin" + System.lineSeparator() +
                "=*=*=*=*=" +
                System.lineSeparator() + System.lineSeparator() +
                "Board test_board info:" + System.lineSeparator() +
                "Team Owner: test_team" + System.lineSeparator() +
                "Work items in board: " + System.lineSeparator() +
                System.lineSeparator() +
                "History:" + System.lineSeparator());

        //Act, Assert
        Assert.assertEquals(test_team.toString(),strBuilder.toString());
    }
}
