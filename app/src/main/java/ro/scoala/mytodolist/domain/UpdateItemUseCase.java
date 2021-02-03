package ro.scoala.mytodolist.domain;

public class UpdateItemUseCase {
    private final TodoListSourceMediator mediator;

    public UpdateItemUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public void updateItem(TodoListItem item) {
        mediator.updateItem(item);
    }
}
