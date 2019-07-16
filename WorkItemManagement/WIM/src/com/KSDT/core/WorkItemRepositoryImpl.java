package com.KSDT.core;

import com.KSDT.core.contracts.WorkItemRepository;
import com.KSDT.models.contracts.*;

import java.util.*;

public class WorkItemRepositoryImpl implements WorkItemRepository {
    private Map<String, Team> teams;
    private Map<String, Person> persons;
//    private List<WorkItem> workItems;
    private Map<Integer, Bug> bugMap;
    private Map<Integer, Feedback> feedbackMap;
    private Map<Integer, Story> storyMap;
    private List<Board> boards;
    private int workItemID = 0;
    private List<Assignable> assignableList;

    public WorkItemRepositoryImpl() {
        this.teams = new HashMap<>();
//        this.workItems = new ArrayList<>();
        this.bugMap = new HashMap<>();
        this.storyMap = new HashMap<>();
        this.feedbackMap = new HashMap<>();
        this.persons = new HashMap<>();
        this.boards = new ArrayList<>();
        this.assignableList = new ArrayList<>();
    }

    @Override
    public Map<String, Team> getTeams() {
        return new HashMap<>(teams);
    }

    @Override
    public Map<Integer, Bug> getBugMap() {
        return new HashMap<>(bugMap);
    }

    @Override
    public Map<Integer, Story> getStoryMap() {
        return new HashMap<>(storyMap);
    }

    @Override
    public Map<Integer, Feedback> getFeedbackMap() {
        return new HashMap<>(feedbackMap);
    }

//    @Override
//    public List<WorkItem> getWorkItems() {
//        return new ArrayList<>(workItems);
//    }

    @Override
    public Map<String, Person> getPersons() {
        return new HashMap<>(persons);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addTeam(String name, Team team) {
        this.teams.put(name, team);
    }

    @Override
    public void addPerson(String name, Person person) {
        this.persons.put(name, person);
    }

    @Override
    public void addBoard( Board board) {
        this.boards.add(board);
    }

    @Override
    public Map<Integer, Priorityable> getPrioritizableItems() {
        Map<Integer, Priorityable> tempMap = new HashMap();

        getAllItems().entrySet()
                .stream()
                .filter(item -> getAllInterfaces(item.getValue().getClass()).contains(Priorityable.class))
                .forEach(item -> tempMap.put(getWorkItemID(getAllItems(), item.getValue()), (Priorityable) item.getValue()));

        return tempMap;
    }

    @Override
    public Map<Integer, Assignable> getAssignableItems() {
        Map<Integer, Assignable> tempMap = new HashMap();

                 getAllItems().entrySet()
                .stream()
                .filter(item -> getAllInterfaces(item.getValue().getClass()).contains(Assignable.class))
                .forEach(item -> tempMap.put(getWorkItemID(getAllItems(), item.getValue()), (Assignable) item.getValue()));

                 return tempMap;
    }

    public Map<Integer, WorkItem> getAllItems() {
        Map<Integer, WorkItem> tempList = new HashMap<>();

        tempList.putAll(bugMap);
        tempList.putAll(storyMap);
        tempList.putAll(feedbackMap);

        return tempList;
    }

    @Override
    public void addBug(Bug bug) {
        this.bugMap.put(getCurrentID(), bug);
        incrementIDCounter();
    }

    @Override
    public void addStory(Story story) {
        this.storyMap.put(getCurrentID(), story);
        incrementIDCounter();
    }

    @Override
    public void addFeedback(Feedback feedback) {
        this.feedbackMap.put(getCurrentID(), feedback);
        incrementIDCounter();
    }

    private void incrementIDCounter() {
        workItemID++;
    }

    private int getCurrentID() {
        return workItemID;
    }

    public <K, V> K getWorkItemID(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(item -> item
                .getValue().equals(value))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }

    private static List<Class<?>> getAllInterfaces(final Class<?> cls) {
        if (cls == null) {
            return null;
        }

        final LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<>();
        getAllInterfaces(cls, interfacesFound);

        return new ArrayList<>(interfacesFound);
    }

    /**
     * Get the interfaces for the specified class.
     *
     * @param cls  the class to look up, may be {@code null}
     * @param interfacesFound the {@code Set} of interfaces for the class
     */
    private static void getAllInterfaces(Class<?> cls, final HashSet<Class<?>> interfacesFound) {
        while (cls != null) {
            final Class<?>[] interfaces = cls.getInterfaces();

            for (final Class<?> i : interfaces) {
                if (interfacesFound.add(i)) {
                    getAllInterfaces(i, interfacesFound);
                }
            }

            cls = cls.getSuperclass();
        }
    }

//    @Override
//    public void addWorkItem(WorkItem workItem) {
//        this.workItems.add(workItem);
//    }

}
