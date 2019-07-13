package com.KSDT.models.contracts;

import com.KSDT.models.common.Pair;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.StatusType;

import java.util.List;

public interface WorkItem {
    String getTitle();

    StatusType getStatus();

    List<Pair<Person, String>> getComments();

    List<Pair<Person, String>> getHistoryPairs();

    String getDescription();

    String getWorkItemType();

    void changeStatus(Person person, StatusType newStatus);

    void addComment(Person person, String comment);

    void addToHistory(Person person, String change);

}
