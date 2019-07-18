package com.KSDT.tests.models.common;

import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.WorkItem;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortHelper_Tests {
    String sortingCriteria;
    List<WorkItem> unsortedList;
    WorkItemRepository repository;

    @Before
    public void before(){
        unsortedList=new ArrayList<>();
        repository=new WorkItemRepositoryImpl();
    }

}
