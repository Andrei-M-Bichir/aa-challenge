package ro.scoala.mytodolist.data;

import java.io.Serializable;
import java.util.Objects;

public class TodoListItemDto implements Serializable {
    public int id;
    public String name;
    public String details;
    public boolean isDone;
    public long alertAtMillis;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoListItemDto that = (TodoListItemDto) o;
        return id == that.id &&
                isDone == that.isDone &&
                alertAtMillis == that.alertAtMillis &&
                Objects.equals(details, that.details) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
