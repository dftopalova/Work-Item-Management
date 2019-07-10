package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.enums.StatusType;

import java.util.List;

import static com.KSDT.commands.CommandConstants.INVALID_NUMBER_OF_ARGUMENTS;

public class ListFilterCommand implements Command {

    private WorkItemRepository repository;


    @Override
    public String execute(List<String> parameters) {
        return null;
    }


    private void validateInput(List<String> parameters) {
        if (parameters.contains("-status")) {
           StatusType status = StatusType.valueOf( parameters.get(parameters.indexOf("-status") +1));
        }
    }


    private void parseParameters(List<String> parameters) {
        try {




        } catch (Exception e) {
            throw new IllegalArgumentException("");
//            TODO EXCEPTION
        }
    }



}
