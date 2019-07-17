package com.KSDT.commands;

import java.util.Locale;

public class CommandConstants {
//    TODO List all messages as constants

    //Error messages
    public static final String PERSON_HAS_NO_ASSIGNED_ITEMS = "! Person has no assigned items!";
    public static final String INVALID_NUMBER_OF_ARGUMENTS = "! Invalid number of arguments. Expected: %d, Received: %d";
    public static final String INVALID_TEAM = "! Team with ID %s doesn't exist.";
    public static final String INVALID_PERSON = "! Person with ID %s doesn't exist.";
    public static final String PERSON_NOT_IN_TEAM = "! Person with ID %s is not in team %s, owner of board with ID %d.";
    public static final String BOARD_NOT_IN_TEAM = "! Board with ID %s is not in team %s.";
    public static final String INVALID_BOARD = "! Board with ID %s doesn't exist.";
    public static final String INVALID_WORK_ITEM = "! Work item with ID %s doesn't exist.";
    public static final String INVALID_ASSIGNABLE_WORK_ITEM = "! Assignable work item with title %s doesn't exist.";
    public static final String BOARD_ALREADY_EXISTS = "! Board with ID %s in team %s already exists.";
    public static final String WORK_ITEM_STATUS_SAME = "! Work item with ID %s has the same status.";
    public static final String WORK_ITEM_PRIORITY_SAME = "! Work item with ID %s has the same priority.";
    public static final String WORK_ITEM_RATING_SAME = "! Work item with ID %s has the same rating.";
    public static final String WORK_ITEM_SEVERITY_SAME = "! Work item with ID %s has the same severity.";
    public static final String WORK_ITEM_SIZE_SAME = "! Work item with ID %s has the same size.";
    public static final String WORK_ITEM_NOT_STORY = "! Work item with id %s is not Story.";
    public static final String WRONG_WORK_ITEM_TYPE = "! Work item type %s doesn't exist.";
    public static final String FEEDBACK_DOESNT_CONTAIN_PRIORITY = "! Feedback doesn't contain priority field.";
    public static final String NO_REGISTERED_PERSONS_MESSAGE = "! There are no registered persons.";
    public static final String NO_CREATED_TEAMS_MESSAGE = "! No created teams." ;
    public static final String NO_MEMBERS_IN_TEAM_ERROR = "! There are no members in team %s.";
    public static final String NO_BOARDS_IN_TEAM_ERROR = "! There are no boards assigned to team %s.";
    public static final String FEEDBACK_HAS_NO_ASSIGNEE_ERROR = "! Feedback has no assignee.";
    public static final String FILTER_TYPE_MISSING = "! You must declare type of work items! /bug/story/feedback/";
    public static final String PERSON_DOESNT_EXIST = "! Person with that name doesn't exist!";
    public static final String INCOMPATIBLE_ITEM_TYPE_AND_SORT_CRITERIA = "! Item type is incompatible with entered sort criteria!";

    // TODO BUG, STORY AND FEEDBACK ERRORS

    //Success messages
    public static final String TEAM_CREATED = "Team with ID %s was created.";
    public static final String PERSON_CREATED = "Person with ID %s was created.";
    public static final String PERSON_ADDED_TO_TEAM = "Person with ID %s was added to %s team.";
    public static final String BOARD_ADDED_TO_TEAM = "Board with ID %s was added to %s team.";
    public static final String BUG_ADDED_TO_BOARD = "Bug with ID %s  was added to %s board.";
    public static final String STORY_ADDED_TO_BOARD = "Story with ID %s was added to %s board.";
    public static final String FEEDBACK_ADDED_TO_BOARD = "Feedback with ID %s was added to %s board.";
    public static final String STATUS_SUCCESSFULLY_CHANGED = "Status of work item with ID %s was changed from %s to %s.";
    public static final String PRIORITY_SUCCESSFULLY_CHANGED = "Priority of work item with ID %s was changed from %s to %s.";
    public static final String SIZE_SUCCESSFULLY_CHANGED = "Size of work item with ID %s was changed from %s to %s.";
    public static final String RATING_SUCCESSFULLY_CHANGED = "Rating of work item with ID %s was changed from %s to %s.";
    public static final String SEVERITY_SUCCESSFULLY_CHANGED = "Severity of work item with ID %s was changed from %s to %s.";
    public static final String COMMENT_SUCCESSFULLY_ADDED = "Comment ' %s ' successfully added to work item with ID %s .";
    public static final String SUCCESSFULLY_ASSIGNED_ITEM_MESSAGE = "Work item %s is successfully assigned to %s.";
    public static final String SUCCESSFULLY_UNASSIGNED_ITEM_MESSAGE = "Work item %s is successfully unassigned from %s.";;


}
