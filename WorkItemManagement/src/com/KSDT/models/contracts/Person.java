package com.KSDT.models.contracts;

import java.util.List;

public interface Person {
    String getName();

    List<String> getHistory();

    List<WorkItem> getWorkItems();

    void addToPersonHistory(String change);

    void addWorkItem(WorkItem item);

    void removeWorkItem(WorkItem item);
}
