package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.WorkItem;
import com.KSDT.models.enums.StatusType;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.KSDT.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class ListFilterCommand implements Command {

    private WorkItemRepository repository;
    private List<WorkItem> workItemList;
    StatusType status;



    @Override
    public String execute(List<String> parameters) {
        validateInput(parameters);

        workItemList=repository.getWorkItems();
        StringBuilder strBuilder = new StringBuilder();

        workItemList.stream().filter(getFilters(parameters));
//        strBuilder.append(System.lineSeparator());

        return strBuilder.toString();
    }


    private void validateInput(List<String> parameters) {
        if (parameters.contains("-status")) {
          status = StatusType.valueOf( parameters.get(parameters.indexOf("-status") +1));
        }
    }

    private Predicate getFilters(List<String> parameters) {
        if (parameters.contains("-status")) {
            status = StatusType.valueOf( parameters.get(parameters.indexOf("-status") +1));
            Predicate<WorkItem> statusPred = (item -> item.getStatus().equals(status));

            return statusPred ;
        }

        return null;
    }


    private void parseParameters(List<String> parameters) {
        try {




        } catch (Exception e) {
            throw new IllegalArgumentException("");
//            TODO EXCEPTION
        }
    }



}
