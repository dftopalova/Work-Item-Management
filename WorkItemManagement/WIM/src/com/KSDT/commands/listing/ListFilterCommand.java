package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.FilterHelper;
import com.KSDT.models.common.SortHelper;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.StatusType;
import com.KSDT.commands.CommandConstants.*;

import java.util.List;
import java.util.Map;

import static com.KSDT.commands.CommandConstants.*;

public class ListFilterCommand implements Command {


    private WorkItemRepository repository;
    private Map<Integer, WorkItem> notFilteredWorkItemMap;
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
        paramValidityCheck();

        notFilteredWorkItemMap = repository.getAllItems();
        StringBuilder strBuilder = new StringBuilder();

        filteredWorkItemList = FilterHelper.filter(notFilteredWorkItemMap);

        if (hasAssigneeFilter) {
            filteredWorkItemList = FilterHelper.assigneeFilter(filteredWorkItemList, assignee, repository);
        }


        if (!sortCriteria.isEmpty()) {
            SortHelper.sortBy(sortCriteria, filteredWorkItemList, repository);
            SortHelper.getFinalList().forEach(item -> strBuilder.append(item));
            return strBuilder.toString();
        }


        filteredWorkItemList.forEach(item -> strBuilder.append(item));

        return strBuilder.toString();
    }

    private void paramValidityCheck() {
        if (itemType ==null) {
            throw new IllegalArgumentException(FILTER_TYPE_MISSING);
        }

        if (assignee == null && hasAssigneeFilter) {
            throw new IllegalArgumentException(PERSON_DOESNT_EXIST);
        }

        if (itemType.equalsIgnoreCase("feedback") && hasAssigneeFilter) {
            throw new IllegalArgumentException(FEEDBACK_HAS_NO_ASSIGNEE_ERROR);
        }

        if (itemType.equalsIgnoreCase("feedback") && (!sortCriteria.equalsIgnoreCase("title") || !sortCriteria.equalsIgnoreCase("rating"))) {
            throw new IllegalArgumentException(INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA);
        }
        if (itemType.equalsIgnoreCase("bug") && !sortCriteria.equalsIgnoreCase("title")) {
            throw new IllegalArgumentException(INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA);
        }if (itemType.equalsIgnoreCase("bug") &&  !sortCriteria.equalsIgnoreCase("severity")) {
            throw new IllegalArgumentException(INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA);
        }
    }

    private void parseParameters(List<String> parameters) {
        try {
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

            if (parameters.contains("-assignee") ) { //TODO FIX!
                hasAssigneeFilter = true;
                assignee = repository.getPersons().get(parameters.get(parameters.indexOf("-assignee") + 1));
            }

        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Invalid filter!");
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

}
