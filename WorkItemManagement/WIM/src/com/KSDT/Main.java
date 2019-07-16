package com.KSDT;

import com.KSDT.core.EngineImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.SizeType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;
import com.KSDT.models.items.FeedbackImpl;
import com.KSDT.models.items.StoryImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
     EngineImpl engine = new EngineImpl();

     engine.start();

//TODO first implement Assignable interface in BugImpl & FeedbackImpl and IT WORKS
//        WorkItem testBug = new BugImpl("testBug123", StatusType.BUG_ACTIVE, "asd asd asd", "asd/asd/asd/asd", PriorityType.HIGH, SeverityType.CRITICAL);
//        WorkItem testWorkItem = new FeedbackImpl("testFeedback", StatusType.FEEDBACK_SCHEDULED, "asd asd asd", 5);
//        WorkItem testStory = new StoryImpl("testStory1", StatusType.STORY_INPROGRESS, "asd asd asd", PriorityType.HIGH, SizeType.MEDIUM);
//
//        Map<Integer, WorkItem> allmap = new HashMap();
//        allmap.put(0, testBug);
//        allmap.put(1, testWorkItem);
//        allmap.put(2, testStory);
//
//        Map<Integer, WorkItem> tempMap = new HashMap();
//
//        allmap.entrySet()
//                .stream()
//                .filter(item -> Arrays.asList(item.getValue().getClass().getInterfaces()).contains(Assignable.class))
//                .forEach(item -> tempMap.put(item.getKey(), item.getValue()));
//
//        System.out.println(tempMap.toString());



    }
}
