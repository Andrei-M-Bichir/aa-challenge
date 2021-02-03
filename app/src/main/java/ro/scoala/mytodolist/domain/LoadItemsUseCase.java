package ro.scoala.mytodolist.domain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class LoadItemsUseCase {
    private final TodoListSourceMediator mediator;

    public LoadItemsUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public LiveData<List<TodoListItem>> getItems() {
        return new MutableLiveData<>();
    }
}
