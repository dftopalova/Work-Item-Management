package com.KSDT.models.common;

import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterHelper {
//TODO probably static, THINK, do shit
    List<Predicate<WorkItem>> allPredicates = new ArrayList<>();

    public void addPredicates(Predicate predicate) {
        allPredicates.add(predicate);
    }

    public List<WorkItem> filter(List<WorkItem> unFilteredList, List<Predicate<WorkItem>> predicateList) {
        List<WorkItem> filtered = new ArrayList<>();

        filtered =  unFilteredList.stream().filter(predicateList.stream()
                .reduce(item ->true, Predicate::and))
                .collect(Collectors.toList());

        return filtered;
    }


}
