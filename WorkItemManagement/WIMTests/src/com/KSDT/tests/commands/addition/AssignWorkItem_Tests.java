//package com.KSDT.tests.commands.addition;
//
//import com.KSDT.commands.addition.AssignWorkItemCommand;
//import com.KSDT.commands.contracts.Command;
//import com.KSDT.core.WorkItemRepositoryImpl;
//import com.KSDT.core.contracts.WorkItemRepository;
//import com.KSDT.models.PersonImpl;
//import com.KSDT.models.TeamImpl;
//import com.KSDT.models.contracts.Person;
//import com.KSDT.models.contracts.Team;
//import com.KSDT.models.contracts.WorkItem;
//import com.KSDT.models.enums.PriorityType;
//import com.KSDT.models.enums.SeverityType;
//import com.KSDT.models.enums.StatusType;
//import com.KSDT.models.items.BugImpl;
//import com.KSDT.models.items.FeedbackImpl;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AssignWorkItem_Tests {
//    private Command testCommand;
//    private WorkItemRepository repository;
//    private Team testTeam;
//    private BasicItem testBasicItem;
//    private WorkItem testWorkItem;
//    private Person testPerson;
//
//    @Before
//    public void before() {
//        repository = new WorkItemRepositoryImpl();
//        testCommand = new AssignWorkItemCommand(repository);
//        testTeam = new TeamImpl("testTeam");
//        testBasicItem = new BugImpl("testBug123", StatusType.BUG_ACTIVE, "asd asd asd", "asd/asd/asd/asd", PriorityType.HIGH, SeverityType.CRITICAL);
//        testWorkItem = new FeedbackImpl("testFeedback", StatusType.FEEDBACK_SCHEDULED, "asd asd asd", 5);
//        testPerson = new PersonImpl("nameasd");
//    }
//
//
//    @Test(expected = IllegalArgumentException.class)
//    public void execute_Should_ThrowExceptionWhenPassedLessArguments() {
//        // Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("asdasd");
//
//        //Act & Assert
//        testCommand.execute(testList);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void execute_Should_ThrowExceptionWhenPassedMoreArguments() {
//        // Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("asdasd");
//        testList.add("asdasd");
//        testList.add("asdasd");
//
//        //Act & Assert
//        testCommand.execute(testList);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void execute_Should_ThrowExceptionWhenWorkItemDoesntExist() {
//        //Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("0");
//        testList.add("nameasd");
//
//        //Act & Assert
//        testCommand.execute(testList);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void execute_Should_ThrowExceptionWhenPersonDoesntExist() {
//        //Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("0");
//        testList.add("nameasd");
//        repository.addWorkItem(testBasicItem);
//
//        //Act & Assert
//        testCommand.execute(testList);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void execute_Should_ThrowExceptionWhenItemIsWrongType() {
//        //Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("0");
//        testList.add("nameasd");
//        repository.addPerson("nameasd", testPerson);
//        repository.addWorkItem(testWorkItem);
//
//        //Act & Assert
//        testCommand.execute(testList);
//    }
//
//    @Test
//    public void execute_Should_AssignWorkItemToPerson() {
//        //Arrange
//        List<String> testList = new ArrayList<>();
//        testList.add("0");
//        testList.add("nameasd");
//        repository.addPerson("nameasd", testPerson);
//        repository.addWorkItem(testBasicItem);
//
//        //Act
//        testCommand.execute(testList);
//
//        //Assert
//        Assert.assertEquals(testPerson.getName(), testBasicItem.getAssignee().getName());
//    }
//
//
//
//}
