package com.KSDT.commands.listing;

import com.KSDT.commands.contracts.Command;
import com.KSDT.core.contracts.WorkItemFactory;
import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.Person;
import com.KSDT.models.contracts.WorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.KSDT.commands.CommandConstants.JOIN_DELIMITER;
import static com.KSDT.commands.CommandConstants.NO_REGISTERED_PERSONS_MESSAGE;

public class ShowAllPeopleCommand implements Command {

    private final WorkItemRepository repository;
    private final WorkItemFactory factory;

    private Map<String, Person> persons;

    public ShowAllPeopleCommand(WorkItemRepository repository, WorkItemFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public String execute(List<String> parameters) {

        this.persons = repository.getPersons();

        if (persons.isEmpty()) {
            return NO_REGISTERED_PERSONS_MESSAGE;
        }

        List<String> personsToStringList = personsToString();

        return String.join(JOIN_DELIMITER + System.lineSeparator(), personsToStringList).trim();
    }

    private List<String> personsToString() {

        List<String> stringifiedPersons = new ArrayList<>();
        for (Person person : persons.values()) {
            stringifiedPersons.add(person.toString());
        }
        //persons.forEach((s, person) -> stringifiedPersons.add(person.toString()));
        return stringifiedPersons;
    }

}
