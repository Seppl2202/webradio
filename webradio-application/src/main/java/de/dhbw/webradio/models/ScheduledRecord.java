package de.dhbw.webradio.models;

public class ScheduledRecord {
    private String title, actor;

    public ScheduledRecord(String title, String actor) {
        this.title = title;
        this.actor = actor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object obj) {
        ScheduledRecord r = (ScheduledRecord) obj;
        return title.equalsIgnoreCase(r.getTitle()) && actor.equalsIgnoreCase(r.getActor());
    }
}
