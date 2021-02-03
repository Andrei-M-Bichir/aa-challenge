package ro.scoala.mytodolist.data.source;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ro.scoala.mytodolist.data.TodoListItemDto;
import ro.scoala.mytodolist.domain.TodoListRepository;

public class InMemoryDataSource implements TodoListRepository {
    private final List<TodoListItemDto> data;

    public InMemoryDataSource() {
        data = new LinkedList<>();
    }

    @Override
    public List<TodoListItemDto> getItems() {
        return data;
    }

    @Override
    public void addItem(TodoListItemDto item) {
        if (!data.contains(item)) {
            data.add(item);
        }
    }

    @Override
    public void updateItem(TodoListItemDto item) {
        TodoListItemDto dto = null;
        for (TodoListItemDto cached : data) {
            if (cached.id == item.id) {
                dto = item;
                break;
            }
        }
        if (dto != null) {
            dto.alertAtMillis = item.alertAtMillis;
            dto.isDone = item.isDone;
            dto.name = item.name;
            dto.details = item.details;
        }
    }

    @Override
    public void removeItemById(int id) {
        Iterator<TodoListItemDto> iterator = data.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().id == id) {
                iterator.remove();
            }
        }
    }
}
