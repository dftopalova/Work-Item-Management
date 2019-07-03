package com.KSDT;

import com.KSDT.core.EngineImpl;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.enums.StatusType;

public class Main {

    public static void main(String[] args) {


        EngineImpl engine = new EngineImpl();
//        StatusType statusType = StatusType.BUG_ACTIVE;
//        StatusType newstatusType = StatusType.BUG_FIXED;
//        System.out.println(HistoryHelper.collectChange(statusType, newstatusType   ));
        engine.start();
    }
}
