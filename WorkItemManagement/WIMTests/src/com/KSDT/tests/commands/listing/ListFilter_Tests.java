package com.KSDT.tests.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.listing.ListFilterCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.*;
import com.KSDT.models.common.FilterHelper;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.*;
import com.KSDT.models.items.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFilter_Tests {
    private Command test_command;
    private WorkItemRepository repository;
    private Map<Integer, WorkItem> notFilteredWorkItemMap;
    private List<WorkItem> filteredWorkItemList;
    private List<String> parameters;

    private String itemType;
    private StatusType status;
    private String sortCriteria;
    private boolean hasAssigneeFilter;
    private Person person1;
    Feedback feedback;
    Story story;
    Bug bug;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        test_command = new ListFilterCommand(repository);
        parameters = new ArrayList<>();
        person1 = new PersonImpl("person1");

        feedback = new FeedbackImpl("feedbackblabla", StatusType.FEEDBACK_SCHEDULED, "random_description", 10);
        story = new StoryImpl("story123456", StatusType.STORY_DONE, "random description", PriorityType.MEDIUM, SizeType.SMALL);
        bug = new BugImpl("test_bug123456", StatusType.BUG_ACTIVE, "bug description",
                "step1/step2/step3", PriorityType.MEDIUM, SeverityType.CRITICAL);

        repository.addFeedback(feedback);
        repository.addStory(story);
        repository.addBug(bug);

        FilterHelper.clearPredicates();
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenNoFilterIsPassed() {

        //Arrange
        parameters.add("-assignee");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenNoTypeFilterIsPassed() {

        //Arrange, Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedAssigneeDoesntExist() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-assignee");
        parameters.add("ivan");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedTypeFeedbackAndAssigneeFilter() {

        //Arrange
        repository.addPerson("person1", person1);
        parameters.add("-type");
        parameters.add("feedback");
        parameters.add("-assignee");
        parameters.add("person1");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedTypeFeedbackAndSortCriteriaIsIncorrect() {

        //Arrange
        repository.addPerson("person1", person1);
        parameters.add("-type");
        parameters.add("feedback");
        parameters.add("-sort");
        parameters.add("priority");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedTypeBugAndSortCriteriaIsIncorrect() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-sort");
        parameters.add("size");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void execute_Should_ThrowExceptionWhenPassedTypeStoryAndSortCriteriaIsIncorrect() {

        //Arrange
        parameters.add("-type");
        parameters.add("story");
        parameters.add("-sort");
        parameters.add("rating");

        //Act, Assert
        test_command.execute(parameters);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedValidItemTypeAndHasAssigneeFilter() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-assignee");
        parameters.add("person1");

        FilterHelper.clearPredicates();
        repository.addPerson("person1", person1);

        person1.addWorkItem(bug);
        bug.setAssignee(person1);

        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: test_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: person1" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }


    @Test
    public void execute_Should_BeSuccessfulWhenPassedValidItemType() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: test_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedValidItemTypeAndCorrectStatusType() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-status");
        parameters.add("active");
        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: test_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }


}
