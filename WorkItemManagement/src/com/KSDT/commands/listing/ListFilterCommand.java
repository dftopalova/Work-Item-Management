package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.FilterHelper;
import com.KSDT.models.common.HistoryHelper;
import com.KSDT.models.common.SortHelper;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.StatusType;

import java.util.ArrayList;
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
    private String sortCriteria = "";
    private boolean hasAssigneeFilter;

    public ListFilterCommand(WorkItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        parseParameters(parameters);

        notFilteredWorkItemList = repository.getWorkItems();
        StringBuilder strBuilder = new StringBuilder();

        filteredWorkItemList = FilterHelper.filter(notFilteredWorkItemList);

        if (hasAssigneeFilter) {
            filteredWorkItemList = FilterHelper.assigneeFilter(filteredWorkItemList, assignee);
        }

        if(!sortCriteria.isEmpty()) {
            SortHelper.sortBy(sortCriteria, filteredWorkItemList);
        }

        filteredWorkItemList.forEach(item -> strBuilder.append(item));

        return strBuilder.toString();
    }

    private void parseParameters(List<String> parameters) {

        if (parameters.contains("-type")) {
            itemType = parameters.get(parameters.indexOf("-type") + 1);
            FilterHelper.addPredicates(item -> item.getWorkItemType().equalsIgnoreCase(itemType));
        }

        if (parameters.contains("-status")) {
            status = StatusType.valueOf(itemType.toUpperCase() + "_" + (parameters.get(parameters.indexOf("-status") + 1)).toUpperCase());
            FilterHelper.addPredicates(item -> item.getStatus().equals(status));
        }

        if (parameters.contains("-sort")) {
            sortCriteria = parameters.get(parameters.indexOf("-sort") + 1);
        }

        if (parameters.contains("-assignee")) {
            hasAssigneeFilter = true;
            assignee = repository.getPersons().get(parameters.get(parameters.indexOf("-assignee") + 1));
        }
    }

}
