package com.KSDT.tests.models.common;

import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.common.FilterHelper;
import com.KSDT.models.contracts.Assignable;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Story;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.StoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FilterHelper_Tests {
    Predicate<WorkItem> test_predicate;
    Map<Integer, WorkItem> test_unFilteredMap;
    WorkItem test_item;

    @Before
    public void before() {

        test_predicate = (item -> item.getStatus().equals(StatusType.FEEDBACK_SCHEDULED));
        test_unFilteredMap = new HashMap<>();
        test_item = new StoryImpl("story123456", StatusType.STORY_DONE, "random description", PriorityType.MEDIUM, SizeType.SMALL);
    }

    @After
    public void after(){
        FilterHelper.clearPredicates();
    }

    @Test
    public void addPredicates_Should_BeSuccessfulWhenPassedCorrectPredicate() {
        //Arrange,Act
        FilterHelper.addPredicates(test_predicate);

        //Assert
        Assert.assertEquals(1, FilterHelper.getAllPredicates().size());
    }

    @Test
    public void allpredicates_Should_BeEmptyBeforeExitFunction() {
        //Arrange,Act
        test_unFilteredMap.put(1, test_item);
        FilterHelper.addPredicates(test_predicate);

        List<WorkItem> workItemList = FilterHelper.filter(test_unFilteredMap);

        //Assert
        Assert.assertEquals(0, FilterHelper.getAllPredicates().size());
    }

    @Test
    public void filter_Should_ReturnListWithAllUnchangedItems() {
        //Arrange
        test_unFilteredMap.put(1, test_item);

        //Act
        List<WorkItem> workItemList = FilterHelper.filter(test_unFilteredMap);

        //Assert
        Assert.assertEquals(1, workItemList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void assigneeFilter_Should_ThrowExceptionWhenPersonDoesntHaveAssignedItems(){
        //Arrange
        List<WorkItem> workItemList=new ArrayList<>();
        Person test_person=new PersonImpl("pesho");
        WorkItemRepository repository = new WorkItemRepositoryImpl();
        List<WorkItem> filtered = new ArrayList<>();

        //Act
        filtered=FilterHelper.assigneeFilter(workItemList,test_person,repository);

        //Assert
        Assert.assertEquals(0,filtered.size());
    }

    @Test
    public void assigneeFilter_Should_BeSuccessfulWhenPersonHaveAssignedItems(){
        //Arrange
        List<WorkItem> workItemList=new ArrayList<>();
        workItemList.add(test_item);
        WorkItemRepository repository = new WorkItemRepositoryImpl();
        repository.addStory((Story) test_item);
        Person test_person=new PersonImpl("pesho");
        ((Assignable)test_item).setAssignee(test_person); // cast only for tests

        List<WorkItem> filtered = new ArrayList<>();

        //Act
        filtered=FilterHelper.assigneeFilter(workItemList,test_person,repository);

        //Assert
        Assert.assertEquals(1,filtered.size());
    }
}
