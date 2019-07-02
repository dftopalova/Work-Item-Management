package com.KSDT.models.contracts;

import java.util.List;
import java.util.Map;

public interface Board {
    String getName();

    Map<String, WorkItem> getWorkItem();

    List<String> getHistory();

    void addWorkItem(String name, WorkItem workItem);

}
