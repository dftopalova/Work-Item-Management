package com.KSDT.models.contracts;

import java.util.List;
import java.util.Map;

public interface Board {
    String getName();

    Map<String, WorkItem> getWorkItems();

//    TODO implement method getWorkItem !
    WorkItem getWorkItem(String name);

    List<String> getHistory();

    void addWorkItem(String name, WorkItem workItem);

}
