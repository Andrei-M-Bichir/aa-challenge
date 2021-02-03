package ro.scoala.mytodolist.domain;

import java.util.Random;

public class AddItemUseCase {
    private final TodoListSourceMediator mediator;
    private final Random random = new Random();

    public AddItemUseCase(TodoListSourceMediator mediator) {
        this.mediator = mediator;
    }

    public void addItem(String name,
                        long remindAtTimestampMillis) {
        TodoListItem item = new TodoListItem(random.nextInt());
        item.setName(name);
        item.setAlertAtMillis(remindAtTimestampMillis);
        item.setDone(false);
        mediator.addItem(item);
    }
}
