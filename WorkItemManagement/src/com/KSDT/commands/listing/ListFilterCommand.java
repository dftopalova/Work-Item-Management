package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Bug;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.KSDT.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class ListFilterCommand implements Command {

    private WorkItemRepository repository;
    private List<WorkItem> notFilteredWorkItemList;
    private List<WorkItem> filteredWorkItemList;


    private String itemType;
    private StatusType status;
    private Person assignee;

    public ListFilterCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        parseParameters(parameters);

        notFilteredWorkItemList = repository.getWorkItems();
        filteredWorkItemList = notFilteredWorkItemList.stream().filter(workItemTypeFilter()).collect(Collectors.toList());
        filteredWorkItemList=filteredWorkItemList.stream().filter(statusFilter()).collect(Collectors.toList());

        StringBuilder strBuilder = new StringBuilder();
//TODO get shit from filterhelper
        filteredWorkItemList.forEach(item -> strBuilder.append(item));
        return strBuilder.toString();
    }

    private Predicate<WorkItem> statusFilter() {

        Predicate<WorkItem> statusPred = (item -> item.getStatus().equals(status));

        return statusPred;
    }

    private Predicate<WorkItem> workItemTypeFilter() {

        Predicate<WorkItem> typePredicate = (item -> item.getWorkItemType().equalsIgnoreCase(itemType));

        return typePredicate;
    }

    private void parseParameters(List<String> parameters) {

        if (parameters.contains("-type")) {
            itemType = parameters.get(parameters.indexOf("-type") + 1);
        }

        if (parameters.contains("-status")) {
            status = StatusType.valueOf(itemType.toUpperCase() + "_" + (parameters.get(parameters.indexOf("-status") + 1)).toUpperCase());
        }

        if (parameters.contains("-assignee")) {
            assignee = repository.getPersons().get(parameters.get(parameters.indexOf("-assignee") + 1));
        }

    }


}
