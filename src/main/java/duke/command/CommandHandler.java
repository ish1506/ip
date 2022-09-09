package duke.command;

import duke.exceptions.DukeException;
import duke.parser.Parser;
import duke.storage.Storage;
import duke.task.*;
import duke.ui.Ui;

public class CommandHandler {
    private final Ui ui;
    private final TaskList tasks;

    public CommandHandler(Ui ui, TaskList tasks) {
        this.ui = ui;
        this.tasks = tasks;
    }

    /**
     * Executes user commands.
     *
     * @param input The input string.
     * @return The string message to display in the GUI.
     */

    public String execute(String input) {
        try {
            Parser parser = new Parser(input);
            parser.parse();
            Command command = parser.getCommand();
            int taskIndex = parser.getTaskIndex();
            String[] args = parser.getArgs();

            switch (command) {
            case BYE:
                // Write tasks data to storage before terminating program
                Storage.writeData(tasks.toStorageString());
                return ui.getGoodbyeMessage();
            case LIST:
                return tasks.toString();
            case MARK:
                tasks.mark(taskIndex);
                return ui.getMarkMessage(tasks.getTask(taskIndex));
            case UNMARK:
                tasks.unmark(taskIndex);
                return ui.getUnmarkMessage(tasks.getTask(taskIndex));
            case DELETE:
                Task temp = tasks.getTask(taskIndex);
                tasks.remove(taskIndex);
                return ui.getDeleteTaskMessage(temp);
            case TODO:
                Task newTodoTask = new Todo(args[0]);
                tasks.add(newTodoTask);
                return ui.getAddTaskMessage(newTodoTask);
            case DEADLINE:
                Task newDeadlineTask = new Deadline(args[0], args[1]);
                tasks.add(newDeadlineTask);
                return ui.getAddTaskMessage(newDeadlineTask);
            case EVENT:
                Task newEventTask = new Event(args[0], args[1]);
                tasks.add(newEventTask);
                return ui.getAddTaskMessage(newEventTask);
            case FIND:
                TaskList newList = tasks.find(args[0]);
                return ui.getFoundTasksListString(newList);
            default:
                throw new DukeException("Unrecognized command");
            }
        } catch (DukeException e) {
            return e.getMessage();
        }
    }
}
