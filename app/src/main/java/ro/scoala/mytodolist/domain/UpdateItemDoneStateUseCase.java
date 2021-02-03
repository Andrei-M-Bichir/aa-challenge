package ro.scoala.mytodolist.domain;

public class UpdateItemDoneStateUseCase {
    private final TodoListSourceMediator mediator;

    public UpdateItemDoneStateUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public void execute(int id, boolean isDone) {
        mediator.updateDoneState(id, isDone);
    }
}
