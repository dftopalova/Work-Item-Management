package com.KSDT.models.common;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.*;

import java.util.*;
import java.util.stream.Collectors;

public class SortHelper {
    static List finalList = new ArrayList<>();

    public static void sortBy(String sortingCriteria, List<WorkItem> unsortedList, WorkItemRepository repository) {
        switch (sortingCriteria.toUpperCase()) {
            case "TITLE":
                sortByTitle(unsortedList);
                break;
            case "PRIORITY":
                sortByPriority(unsortedList, repository);
                break;
            case "SEVERITY":
                sortBugsBySeverity(unsortedList, repository);
                break;
            case "SIZE":
                sortStoriesBySize(unsortedList,repository);
                break;
            case "RATING":
                sortFeedbacksByRating(unsortedList,repository);
                break;
            default:
                System.out.println("No such criteria!");
                break;
        }
    }

    private static void sortByTitle(List<WorkItem> unsortedList) {
        unsortedList.sort(Comparator.comparing(WorkItem::getTitle));
        finalList.clear();
        finalList.addAll(unsortedList);
    }

    public static List getFinalList() {
        return finalList;
    }

    private static void sortByPriority(List<WorkItem> unsortedList, WorkItemRepository repository) {
        List<Priorityable> tempList = new ArrayList<>();
        Map<Integer, WorkItem> allItemsMap = repository.getAllItems();
        Map<Integer, Priorityable> priorityableMap = repository.getPrioritizableItems();

        collectCommonItems(unsortedList, repository, tempList, priorityableMap);

        tempList.sort(new Comparator<Priorityable>() {
            @Override
            public int compare(Priorityable item1, Priorityable item2) {
                if (item1.getPriority().equals(item2.getPriority())) {
                    return 0;
                }
                return item1.getPriority().ordinal() < item2.getPriority().ordinal() ? -1 : 1;
            }
        });
        finalList.clear();
        finalList.addAll(tempList);
    }

    private static void sortBugsBySeverity(List<WorkItem> unsortedList, WorkItemRepository repository) {
        Map<Integer, Bug> bugMap = repository.getBugMap();
        List<Bug> tempList=new ArrayList<>();

        collectCommonItems(unsortedList, repository, tempList, bugMap);
        tempList.sort(new Comparator<Bug>() {
            @Override
            public int compare(Bug bug1, Bug bug2) {
                if (bug1.getSeverity().equals(bug2.getSeverity())) {
                    return 0;
                }
                return bug1.getSeverity().ordinal() < bug2.getSeverity().ordinal() ? -1 : 1;
            }
        });
        finalList.clear();
        finalList.addAll(tempList);
    }

    private static void sortStoriesBySize(List<WorkItem> unsortedList, WorkItemRepository repository) {
        Map<Integer,Story> storyMap=repository.getStoryMap();
        List<Story> tempList=new ArrayList<>();

        collectCommonItems(unsortedList, repository, tempList, storyMap);
        tempList.sort(new Comparator<Story>() {
            @Override
            public int compare(Story s1, Story s2) {
                if (s1.getSize().equals(s2.getSize())) {
                    return 0;
                }
                return s1.getSize().ordinal() < s2.getSize().ordinal() ? -1 : 1;
            }
        });
        finalList.clear();
        finalList.addAll(tempList);
    }

    private static void sortFeedbacksByRating(List<WorkItem> unsortedList, WorkItemRepository repository) {
        Map<Integer,Feedback> feedbackMap=repository.getFeedbackMap();
        List<Feedback> tempList=new ArrayList<>();

        collectCommonItems(unsortedList, repository, tempList, feedbackMap);
        tempList.sort(new Comparator<Feedback>() {
            @Override
            public int compare(Feedback f1, Feedback f2) {
                if (f1.getRating() == f2.getRating()) {
                    return 0;
                }
                return f1.getRating() < f2.getRating() ? -1 : 1;
            }
        });
        finalList.clear();
        finalList.addAll(tempList);
    }


    private static <E> void collectCommonItems(List<WorkItem> unsortedList, WorkItemRepository repository, List<E> tempList, Map<Integer, E> customMap ) {
        Map<Integer, WorkItem> allItemsMap = repository.getAllItems();

        for (WorkItem item : unsortedList) {
            if (customMap.containsValue(item)) ;
            {
                int id = repository.getWorkItemID(allItemsMap, item);
                tempList.add(customMap.get(id));
            }
        }
    }
}
