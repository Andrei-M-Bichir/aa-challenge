package ro.scoala.mytodolist.domain;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ro.scoala.mytodolist.data.TodoListItemDto;

public class TodoListSourceMediator {
    private final TodoListRepository localRepository;
    private final TodoListRepository diskRepository;
    private final MutableLiveData<List<TodoListItem>> items;
    private final ExecutorService executorService;

    public TodoListSourceMediator(TodoListRepository localRepository,TodoListRepository diskRepository) {
        this.localRepository = localRepository;
        this.diskRepository = diskRepository;
        this.executorService = Executors.newSingleThreadExecutor();
        this.items = new MutableLiveData<>();
        this.items.setValue(new ArrayList<>());
    }

    public LiveData<List<TodoListItem>> getItems() {
        List<TodoListItem> items = new ArrayList<>();
        for (TodoListItemDto dto : localRepository.getItems()) {
            TodoListItem item = map(dto);
            if (!items.contains(item)) {
                items.add(item);
            }
        }
        this.items.setValue(items);

        executorService.execute(() -> {
            for (TodoListItemDto dto : diskRepository.getItems()) {
                TodoListItem item = map(dto);
                if (!items.contains(item)) {
                    items.add(item);
                }
            }

            TodoListSourceMediator.this.items.postValue(items);
        });

        return this.items;
    }

    public void addItem(TodoListItem item) {
        List<TodoListItem> items = this.items.getValue();
        items.add(item);
        TodoListItemDto dto = map(item);
        localRepository.addItem(dto);

        this.items.setValue(items);
    }

    public void updateItem(TodoListItem item) {
        List<TodoListItem> items = this.items.getValue();
        TodoListItemDto dto = map(item);
        localRepository.updateItem(dto);

        this.items.setValue(items);
    }

    public void removeByItemId(int id) {
        List<TodoListItem> items = this.items.getValue();
        Iterator<TodoListItem> iterator = this.items.getValue().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId() == id) {
                iterator.remove();
            }
        }

        localRepository.removeItemById(id);

        this.items.setValue(items);
    }

    @Nullable
    public TodoListItem getById(int id) {
        for (TodoListItem item : items.getValue()) {
            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    public void updateDoneState(int id, boolean isDone) {
        for (TodoListItem item : items.getValue()) {
            if (item.getId() == id) {
                item.setDone(isDone);
                return;
            }
        }
    }

    private TodoListItem map(TodoListItemDto dto) {
        TodoListItem item = new TodoListItem(dto.id);
        item.setAlertAtMillis(dto.alertAtMillis);
        item.setDone(dto.isDone);
        item.setName(dto.name);
        item.setDetails(dto.details);
        item.setAlertAtMillis(dto.alertAtMillis);
        return item;
    }

    private TodoListItemDto map(TodoListItem item) {
        TodoListItemDto dto = new TodoListItemDto();
        dto.id = item.getId();
        dto.name = item.getName();
        dto.isDone = item.isDone();
        dto.alertAtMillis = item.getAlertAtMillis();
        dto.details = item.getDetails();
        return dto;
    }
}
