package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.common.FilterHelper;
import com.KSDT.models.common.SortHelper;
import com.KSDT.models.contracts.*;
import com.KSDT.models.enums.StatusType;

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
        parametersValidityCheck();

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

    private void parametersValidityCheck() {
        if (itemType ==null) {
            throw new IllegalArgumentException(FILTER_TYPE_MISSING);
        }

        if (assignee == null && hasAssigneeFilter) {
            throw new IllegalArgumentException(PERSON_DOESNT_EXIST);
        }

        if (itemType.equalsIgnoreCase("feedback") && hasAssigneeFilter) {
            throw new IllegalArgumentException(FEEDBACK_HAS_NO_ASSIGNEE_ERROR);
        }

        if (itemType.equalsIgnoreCase("feedback") &&
                !sortCriteria.isEmpty() && !sortCriteria.matches("TITLE|RATING")) {
            throw new IllegalArgumentException(INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA);
        }
        if (itemType.equalsIgnoreCase("bug") &&
                !sortCriteria.isEmpty() && !sortCriteria.matches("TITLE|PRIORITY|SEVERITY")) {
            throw new IllegalArgumentException(INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA);
        }
        if (itemType.equalsIgnoreCase("story") &&
                !sortCriteria.isEmpty() && !sortCriteria.matches("TITLE|PRIORITY|SIZE")) {
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
                sortCriteria = parameters.get(parameters.indexOf("-sort") + 1).toUpperCase();
            }

            if (parameters.contains("-assignee") ) {
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
