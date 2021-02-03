package ro.scoala.mytodolist.di;

import android.content.Context;

import ro.scoala.mytodolist.data.source.DiskDataSource;
import ro.scoala.mytodolist.data.source.InMemoryDataSource;
import ro.scoala.mytodolist.domain.AddItemUseCase;
import ro.scoala.mytodolist.domain.GetItemByIdUseCase;
import ro.scoala.mytodolist.domain.LoadItemsUseCase;
import ro.scoala.mytodolist.domain.RemoveItemUseCase;
import ro.scoala.mytodolist.domain.TodoListRepository;
import ro.scoala.mytodolist.domain.TodoListSourceMediator;
import ro.scoala.mytodolist.domain.UpdateItemDoneStateUseCase;
import ro.scoala.mytodolist.domain.UpdateItemUseCase;

public class TodoListDependencyProvider {
    private static TodoListDependencyProvider instance;
    private final TodoListRepository localRepo;
    private final TodoListRepository diskRepo;
    private final TodoListSourceMediator mediator;

    private final LoadItemsUseCase loadItemsUseCase;
    private final AddItemUseCase addItemUseCase;
    private final RemoveItemUseCase removeItemUseCase;
    private final GetItemByIdUseCase getItemByIdUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final UpdateItemDoneStateUseCase updateItemDoneStateUseCase;

    private TodoListDependencyProvider(Context context) {
        localRepo = new InMemoryDataSource();
        diskRepo = new DiskDataSource(context);
        mediator = new TodoListSourceMediator(localRepo, diskRepo);

        loadItemsUseCase = new LoadItemsUseCase(mediator);
        addItemUseCase = new AddItemUseCase(mediator);
        removeItemUseCase = new RemoveItemUseCase(mediator);
        getItemByIdUseCase = new GetItemByIdUseCase(mediator);
        updateItemUseCase = new UpdateItemUseCase(mediator);
        updateItemDoneStateUseCase = new UpdateItemDoneStateUseCase(mediator);
    }

    public static TodoListDependencyProvider get(Context context) {
        if (instance == null) {
            instance = new TodoListDependencyProvider(context);
        }
        return instance;
    }

    public LoadItemsUseCase provideLoadItemsUseCase() {
        return loadItemsUseCase;
    }

    public GetItemByIdUseCase provideGetItemByIdUseCase() {
        return getItemByIdUseCase;
    }

    public AddItemUseCase provideAddItemUseCase() {
        return addItemUseCase;
    }

    public RemoveItemUseCase provideRemoveItemUseCase() {
        return removeItemUseCase;
    }

    public UpdateItemUseCase provideUpdateItemUseCase() {
        return updateItemUseCase;
    }

    public UpdateItemDoneStateUseCase provideUpdateItemDoneStateUseCase() {
        return updateItemDoneStateUseCase;
    }

    public TodoListSourceMediator provideMediator() {
        return mediator;
    }
}
