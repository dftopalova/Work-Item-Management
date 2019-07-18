package com.KSDT.tests.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.listing.ListAllWorkItemsCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Feedback;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import com.KSDT.models.items.FeedbackImpl;
import com.KSDT.models.items.StoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListAllWorkItemsCommand_Tests {
    private Command test_command;
    private WorkItemRepository repository;
    private List<WorkItem> test_list;
    List<String> parameters;

    @Before
    public void before(){
        repository=new WorkItemRepositoryImpl();
        test_command=new ListAllWorkItemsCommand(repository);
        test_list=new ArrayList<>();
        parameters=new ArrayList<>();
        WorkItem item1=new FeedbackImpl("feedbackblabla", StatusType.FEEDBACK_SCHEDULED,"random_description",10);
        WorkItem item2=new StoryImpl("story123456", StatusType.STORY_DONE,"random description", PriorityType.MEDIUM, SizeType.SMALL);
        WorkItem item3=new BugImpl("test_bug123456", StatusType.BUG_ACTIVE,"bug description",
                "step1/step2/step3", PriorityType.MEDIUM, SeverityType.CRITICAL);

        repository.addFeedback((Feedback) item1);
        repository.addStory((Story) item2);
        repository.addBug((Bug) item3);

        test_list.add(item1);
        test_list.add(item2);
        test_list.add(item3);
    }

    @Test
    public void execute_Should_PrintAllWorkItemsFromRepository(){
        //Arrange, Act
        StringBuilder expected=new StringBuilder();

        expected.append("List of all work items:" + System.lineSeparator() +
                "Feedback info:" + System.lineSeparator() +
                "Title: feedbackblabla" + System.lineSeparator() +
                "Status: Feedback scheduled" + System.lineSeparator() +
                "Description: random_description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Rating: 10" + System.lineSeparator() +
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
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator() +
                "Bug info:" + System.lineSeparator() +
                "Title: test_bug123456" + System.lineSeparator() +
                "Status: Bug active" + System.lineSeparator() +
                "Description: bug description" + System.lineSeparator() +
                "Comments: " + System.lineSeparator() +
                "History: " + System.lineSeparator() +
                "Steps to reproduce: [step1, step2, step3]" + System.lineSeparator() +
                "Severity: Critical" + System.lineSeparator() +
                "Priority: Medium" + System.lineSeparator() +
                "Assignee: NO_NAME" + System.lineSeparator() +
                "=*=*=*=*=*=*=*=*=*=*=*=*=*=*=" + System.lineSeparator()
                + System.lineSeparator());

        String actual=test_command.execute(parameters);

        //Assert
        Assert.assertEquals(expected.toString(),actual);
    }
}
