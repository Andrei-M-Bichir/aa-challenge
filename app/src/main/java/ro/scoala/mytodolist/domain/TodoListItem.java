package ro.scoala.mytodolist.domain;

public class TodoListItem {
    private final int id;
    private String name;
    private String details;
    private boolean isDone;
    private long alertAtMillis;

    public TodoListItem(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getAlertAtMillis() {
        return alertAtMillis;
    }

    public void setAlertAtMillis(long alertAtMillis) {
        this.alertAtMillis = alertAtMillis;
    }
}
