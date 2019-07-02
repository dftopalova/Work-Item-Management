package com.KSDT.models.contracts;

import com.KSDT.models.enums.StatusType;

import java.util.List;

public interface WorkItem {
    String getTitle();
    StatusType getStatus();
    List<String> getComments();
    List<String> getHistory();
    String getDescription();
}
