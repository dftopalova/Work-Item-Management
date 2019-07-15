package com.KSDT.models.common;

import com.KSDT.models.contracts.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortHelper {

    public static void sortBy(String sortingCriteria,List<WorkItem> unsortedList){
//        ValidationHelper.nullCheck(sortingCriteria);
        switch (sortingCriteria.toUpperCase()){
            case "TITLE":
                sortByTitle(unsortedList);
                break;
            case "PRIORITY":
                sortByPriority(unsortedList);
                break;
            case "SEVERITY":
                sortBugsBySeverity(unsortedList);
                break;
            case "SIZE":
                sortStoriesBySize(unsortedList);
                break;
            case "RATING":
                sortFeedbacksByRating(unsortedList);
                break;
                default:
                    System.out.println("No such a criteria!");
        }
    }

    private static void sortByTitle(List<WorkItem> unsortedList) {
        unsortedList.sort(Comparator.comparing(WorkItem::getTitle));
    }

    private static void sortByPriority(List<WorkItem> unsortedList) {
        unsortedList.stream().map(BasicItem.class::cast).collect(Collectors.toList()).sort(new Comparator<BasicItem>() {
            @Override
            public int compare(BasicItem item1, BasicItem item2) {
                if (item1.getPriority().equals(item2.getPriority())) {
                    return 0;
                }
                return  item1.getPriority().ordinal() < item2.getPriority().ordinal() ? -1 :1;
            }
        });
    }

    private static void sortBugsBySeverity(List<WorkItem> unsortedList) {
        unsortedList.stream().map(Bug.class::cast).collect(Collectors.toList()).sort(new Comparator<Bug>() {
            @Override
            public int compare(Bug bug1, Bug bug2) {
                if (bug1.getSeverity().equals(bug2.getSeverity())) {
                    return 0;
                }
                return bug1.getSeverity().ordinal() < bug2.getSeverity().ordinal() ? -1 : 1;
            }
        });
    }

    private static void sortStoriesBySize(List<WorkItem> unsortedList){
        unsortedList.stream().map(Story.class::cast).collect(Collectors.toList()).sort(new Comparator<Story>() {
            @Override
            public int compare(Story s1,Story s2) {
                if (s1.getSize().equals(s2.getSize())) {
                    return 0;
                }
                return s1.getSize().ordinal() < s2.getSize().ordinal() ? -1 : 1;
            }
        });
    }

    private static void sortFeedbacksByRating(List<WorkItem> unsortedList){
        unsortedList.stream().map(Feedback.class::cast).collect(Collectors.toList()).sort(new Comparator<Feedback>() {
            @Override
            public int compare(Feedback f1, Feedback f2) {
                if(f1.getRating() == f2.getRating()) {
                    return 0;
                }
                return f1.getRating() < f2.getRating() ? -1 : 1;
            }
        });
    }
}
