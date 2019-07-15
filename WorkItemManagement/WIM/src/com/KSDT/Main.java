package com.KSDT;

import com.KSDT.core.EngineImpl;
import com.KSDT.models.BoardImpl;
import com.KSDT.models.PersonImpl;
import com.KSDT.models.TeamImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.contracts.Board;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.Team;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.PriorityType;
import com.KSDT.models.enums.SeverityType;
import com.KSDT.models.enums.StatusType;
import com.KSDT.models.items.BugImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
     EngineImpl engine = new EngineImpl();

     engine.start();
    }
}
