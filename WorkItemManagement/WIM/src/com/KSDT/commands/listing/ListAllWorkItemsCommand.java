package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.WorkItem;

import java.util.List;
import java.util.stream.Collectors;

public class ListAllWorkItemsCommand implements Command {

    private WorkItemRepository repository;

    private List<WorkItem> workItemList;

    public ListAllWorkItemsCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        workItemList=repository.getAllItems().values().stream().collect(Collectors.toList());

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("List of all work items:"+System.lineSeparator());
        workItemList.stream().forEach(item -> strBuilder.append(item));
        strBuilder.append(System.lineSeparator());

        return strBuilder.toString();
    }
}
