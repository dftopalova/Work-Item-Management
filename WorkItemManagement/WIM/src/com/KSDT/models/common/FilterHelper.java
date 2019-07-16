package com.KSDT.models.common;

import com.KSDT.models.contracts.Assignable;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterHelper {

    static List<Predicate<WorkItem>> allPredicates = new ArrayList<>();

    public static void addPredicates(Predicate<WorkItem> predicate) {
        allPredicates.add(predicate);
    }

    public static List<WorkItem> filter(List<WorkItem> unFilteredList) {

        if (allPredicates.size() == 0) {
            return unFilteredList;
        }

        List<WorkItem> filtered = new ArrayList<>();

        filtered = unFilteredList.stream().filter(getAllPredicates().stream()
                .reduce(item -> true, Predicate::and))
                .collect(Collectors.toList());
            allPredicates.clear();
        return filtered;
    }

    public static List<WorkItem> assigneeFilter(List<Assignable> unfilteredList, Person assignee) {

        List<WorkItem> filtered = new ArrayList<>();

        filtered = unfilteredList.stream()
                //TODO do shit

                .filter(item -> item.getAssignee().equals(assignee))
                .collect(Collectors.toList());

        return filtered;
    }

    public static List<Predicate<WorkItem>> getAllPredicates() {
        return new ArrayList<>(allPredicates);
    }

}
