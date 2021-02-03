package ro.scoala.mytodolist.domain;

import java.util.Random;

public class RemoveItemUseCase {
    private final TodoListSourceMediator mediator;
    private final Random random = new Random();

    public RemoveItemUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public void removeItemById(int id) {
        mediator.removeByItemId(id);
    }
}
