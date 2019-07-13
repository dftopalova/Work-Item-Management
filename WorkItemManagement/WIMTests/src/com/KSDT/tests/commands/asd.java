package com.KSDT.tests.commands;

import com.KSDT.commands.contracts.Command;
import com.KSDT.commands.creation.CreateTeamCommand;
import com.KSDT.core.WorkItemRepositoryImpl;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.core.factories.WorkItemFactoryImpl;
import com.KSDT.models.PersonImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class asd {

    private Command testCommand;
    private WorkItemRepository repository = new WorkItemRepositoryImpl();
    private WorkItemFactory factory = new WorkItemFactoryImpl();


    @Test
    public void test1() {

        testCommand = new CreateTeamCommand(repository, factory);
        List<String> testList = new ArrayList<>();
        testList.add("asdasd");
        testCommand.execute(testList);

    }

}
