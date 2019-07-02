package com.KSDT.models.enums;

public enum StatusType {
    BUG_ACTIVE,
    BUG_FIXED,
    STORY_NOTDONE,
    STORY_INPROGRESS,
    STORY_DONE,
    FEED_NEW,
    FEED_UNSCHEDULED,
    FEED_SCHEDULED,
    FEED_DONE;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name().charAt(0));
        for (int i = 1; i < this.name().length(); i++) {
            if (name().charAt(i) == '_') {
                sb.append(' ');
            }
            else {
                sb.append(name().toLowerCase().charAt(i));
            }
        }
        return sb.toString();
    }

}
