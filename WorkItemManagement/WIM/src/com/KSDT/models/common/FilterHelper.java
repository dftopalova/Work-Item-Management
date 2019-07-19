package com.KSDT.models.common;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Assignable;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.KSDT.commands.CommandConstants.PERSON_HAS_NO_ASSIGNED_ITEMS;

public class FilterHelper {

    private static List<Predicate<WorkItem>> allPredicates = new ArrayList<>();

    public static void addPredicates(Predicate<WorkItem> predicate) {
        allPredicates.add(predicate);
    }

    public static List<WorkItem> filter(Map<Integer, WorkItem> unFilteredMap) {

        if (allPredicates.size() == 0) {
            return unFilteredMap.values().stream().collect(Collectors.toList());
        }

        List<WorkItem> filtered = new ArrayList<>();

        filtered = unFilteredMap.values().stream()
                .filter(getAllPredicates().stream()
                        .reduce(item -> true, Predicate::and))
                .collect(Collectors.toList());

        FilterHelper.clearPredicates(); // to clear predicates for next invocation

        return filtered;
    }

    public static List<WorkItem> assigneeFilter(List<WorkItem> unfilteredList, Person assignee, WorkItemRepository repository) {
        List<WorkItem> filtered = new ArrayList<>();
        List<Assignable> assignableList = new ArrayList<>();
        Map<Integer, Assignable> assignableItemsMap = repository.getAssignableItems();
        Map<Integer, WorkItem> allItemsMap = repository.getAllItems();

        for (WorkItem item : unfilteredList) {
            if (assignableItemsMap.containsValue(item)) ;
            {
                int id = repository.getWorkItemID(allItemsMap, item);
                assignableList.add(assignableItemsMap.get(id));
            }
        }

        filtered = assignableList.stream()
                .filter(item -> item.getAssignee().equals(assignee))
                .collect(Collectors.toList());

        if (filtered.isEmpty())
            throw new IllegalArgumentException(PERSON_HAS_NO_ASSIGNED_ITEMS);

        return filtered;
    }

    public static List<Predicate<WorkItem>> getAllPredicates() {
        return new ArrayList<>(allPredicates);
    }

    public static void clearPredicates(){
        FilterHelper.allPredicates.clear();
    }
}
