package com.KSDT.core.factories;

import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.contracts.Team;

public class WorkItemFactoryImpl implements WorkItemFactory {


    @Override
    public Team createTeam(String name) {
        return new TeamImpl(name);
    }


}
