package ro.scoala.mytodolist.domain;

import java.util.List;

import ro.scoala.mytodolist.data.TodoListItemDto;

public interface TodoListRepository {

    List<TodoListItemDto> getItems();

    void addItem(TodoListItemDto item);

    void updateItem(TodoListItemDto item);

    void removeItemById(int id);
}
