package ro.scoala.mytodolist.domain;

public class GetItemByIdUseCase {
    private final TodoListSourceMediator mediator;

    public GetItemByIdUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public TodoListItem get(int id) {
        return mediator.getById(id);
    }
}
