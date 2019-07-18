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
import java.util.List;

public class ListFilter_Tests {
    private Command test_command;
    private WorkItemRepository repository;
    private List<String> parameters;

    private String itemType;
    private StatusType status;
    private String sortCriteria;
    private Person person1;
    private Feedback feedback1;
    private Feedback feedback2;
    private Story story1;
    private Story story2;
    private Bug bug1;
    private Bug bug2;

    @Before
    public void before() {
        repository = new WorkItemRepositoryImpl();
        test_command = new ListFilterCommand(repository);
        parameters = new ArrayList<>();
        person1 = new PersonImpl("person1");

        feedback1 = new FeedbackImpl("feedbackblabla1", StatusType.FEEDBACK_SCHEDULED, "random_description", 10);
        feedback2 = new FeedbackImpl("feedbackblabla2", StatusType.FEEDBACK_UNSCHEDULED, "random_description2", 15);
        story1 = new StoryImpl("story123456", StatusType.STORY_DONE, "random description", PriorityType.MEDIUM, SizeType.SMALL);
        story2 = new StoryImpl("story56478", StatusType.STORY_INPROGRESS, "random description2", PriorityType.HIGH, SizeType.LARGE);
        bug1 = new BugImpl("atest_bug123456", StatusType.BUG_ACTIVE, "bug1 description",
                "step1/step2/step3", PriorityType.MEDIUM, SeverityType.MINOR);
        bug2 = new BugImpl("btest_bug5678", StatusType.BUG_FIXED, "bug2 description",
                "step5/step6/step7", PriorityType.HIGH, SeverityType.CRITICAL);

        repository.addFeedback(feedback2);
        repository.addFeedback(feedback1);
        repository.addStory(story1);
        repository.addStory(story2);
        repository.addBug(bug2);
        repository.addBug(bug1);

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
        parameters.add("feedback1");
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

        repository.addPerson("person1", person1);

        person1.addWorkItem(bug1);
        bug1.setAssignee(person1);

        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
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
                "Title: btest_bug5678" + System.lineSeparator() +
                "Status: Bug fixed" + System.lineSeparator() +
                "Description: bug2 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step5, step6, step7]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
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
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedTypeBugAndPrioritySortCriteria() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-sort");
        parameters.add("priority");
        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: btest_bug5678" + System.lineSeparator() +
                "Status: Bug fixed" + System.lineSeparator() +
                "Description: bug2 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step5, step6, step7]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedTypeBugAndSeveritySortCriteria() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-sort");
        parameters.add("severity");
        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: btest_bug5678" + System.lineSeparator() +
                "Status: Bug fixed" + System.lineSeparator() +
                "Description: bug2 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step5, step6, step7]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedTypeBugAndTitleSortCriteria() {

        //Arrange
        parameters.add("-type");
        parameters.add("bug");
        parameters.add("-sort");
        parameters.add("title");
        StringBuilder expected = new StringBuilder();
        expected.append("Bug info:" + System.lineSeparator() +
                "Title: atest_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug1 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Minor" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: btest_bug5678" + System.lineSeparator() +
                "Status: Bug fixed" + System.lineSeparator() +
                "Description: bug2 description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step5, step6, step7]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedTypeFeedbackAndRatingSortCriteria() {

        //Arrange
        parameters.add("-type");
        parameters.add("feedback");
        parameters.add("-sort");
        parameters.add("rating");
        StringBuilder expected = new StringBuilder();
        expected.append("Feedback info:" + System.lineSeparator() +
                "Title: feedbackblabla1" + System.lineSeparator() +
                "Status: Feedback scheduled" + System.lineSeparator() +
                "Description: random_description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Rating: 10" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Feedback info:" + System.lineSeparator() +
                "Title: feedbackblabla2" + System.lineSeparator() +
                "Status: Feedback unscheduled" + System.lineSeparator() +
                "Description: random_description2" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Rating: 15" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

        String actual = test_command.execute(parameters);
        //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
    }

    @Test
    public void execute_Should_BeSuccessfulWhenPassedTypeStoryAndSizeSortCriteria() {

    //Arrange
        parameters.add("-type");
        parameters.add("story");
        parameters.add("-sort");
        parameters.add("size");
    StringBuilder expected = new StringBuilder();
        expected.append("Story info:" + System.lineSeparator() +
                "Title: story56478" + System.lineSeparator() +
                "Status: Story inprogress" + System.lineSeparator() +
                "Description: random description2" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Priority: High" + System.lineSeparator() +
                "Size: Large" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Story info:" + System.lineSeparator() +
                "Title: story123456" + System.lineSeparator() +
                "Status: Story done" + System.lineSeparator() +
                "Description: random description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Size: Small" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator());

    String actual = test_command.execute(parameters);
    //Act, Assert
        Assert.assertEquals(expected.toString(), actual);
}

}
