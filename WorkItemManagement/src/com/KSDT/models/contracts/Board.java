package com.KSDT.models.contracts;

import java.util.List;

public interface Board {
    String getName();
    List<WorkItem> getWorkItem();
    List<String> getHistory();
}
