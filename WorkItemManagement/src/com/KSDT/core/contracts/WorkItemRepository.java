package com.KSDT.core.contracts;

import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.Map;

public interface WorkItemRepository {

    Map<String, Team> getTeams();

    Map<String, WorkItem> getWorkItems();

    void addTeam(String name, Team team);

    void addWorkItem(String name, WorkItem workItem);
}
