package com.KSDT.core;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;

import java.util.Map;

public class WorkItemRepositoryImpl implements WorkItemRepository {
    @Override
    public Map<String, Team> getTeams() {
        return null;
    }

    @Override
    public Map<String, WorkItem> getWorkItems() {
        return null;
    }

    @Override
    public void addTeam(String name, Team team) {

    }

    @Override
    public void addWorkItem(String name, WorkItem workItem) {

    }
}
