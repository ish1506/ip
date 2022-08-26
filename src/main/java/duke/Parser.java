package duke;

import java.util.Arrays;
import java.util.List;

/**
 * Deals with making sense of the user command.
 */
public class Parser {
    private final String input;
    private Command command;
    private int taskIndex = -1;
    private String[] args;

    public Parser(String input) throws DukeException {
        this.input = input;
    }

    public Command getCommand() {
        return command;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public String[] getArgs() {
        return args;
    }

    /**
     * Parses the user input to get the command and arguments
     */
    public void parse() throws DukeException {
        List<String> listOfInputs = Arrays.asList(input.split(" "));
        if (listOfInputs.isEmpty()) {
            throw new DukeException("Please enter a command!");
        } else {
            int numArgs = listOfInputs.size();
            String firstArg = listOfInputs.get(0);

            if (firstArg.equals("bye")) {
                command = Command.BYE;
            } else if (firstArg.equals("list")) {
                command = Command.LIST;
            } else if (firstArg.equals("mark")) {
                if (numArgs != 2) {
                    throw new DukeException("Input format: mark (duke.task number)");
                }
                try {
                    command = Command.MARK;
                    taskIndex = Integer.parseInt(listOfInputs.get(1)) - 1;
                } catch (NumberFormatException e) {
                    throw new DukeException("Input format: mark (duke.task number)");
                }
            } else if (firstArg.equals("unmark")) {
                if (numArgs != 2) {
                    throw new DukeException("Input format: unmark (duke.task number)");
                }
                try {
                    command = Command.UNMARK;
                    taskIndex = Integer.parseInt(listOfInputs.get(1)) - 1;
                } catch (NumberFormatException e) {
                    throw new DukeException("Input format: unmark (duke.task number)");
                }
            } else if (firstArg.equals("delete")) {
                if (numArgs != 2) {
                    throw new DukeException("Input format: delete (duke.task number)");
                }
                try {
                    command = Command.DELETE;
                    taskIndex = Integer.parseInt(listOfInputs.get(1)) - 1;
                } catch (NumberFormatException e) {
                    throw new DukeException("Input format: delete (duke.task number)");
                }
            } else if (firstArg.equals("todo")) {
                if (numArgs == 1) {
                    throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                }
                command = Command.TODO;
                String todoName = input.split("todo ")[1];
                args = new String[]{todoName};
            } else if (firstArg.equals("event")) {
                if (numArgs < 4 || !listOfInputs.contains("\\at")) {
                    throw new DukeException("Input format: event (event name) \\at (event date/time)");
                }
                command = Command.EVENT;
                int index = input.indexOf("\\at ");
                String eventName = input.substring(6, index);
                String eventTime = input.substring(index + 4);
                args = new String[]{eventName, eventTime};
            } else if (firstArg.equals("deadline")) {
                if (numArgs < 4 || !listOfInputs.contains("\\by")) {
                    throw new DukeException("Input format: deadline (deadline name) \\by (YYYY-MM-DD)");
                }
                command = Command.DEADLINE;
                int index = input.indexOf("\\by ");
                String deadlineName = input.substring(9, index);
                String deadlineTime = input.substring(index + 4);
                args = new String[]{deadlineName, deadlineTime};
            } else {
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
            }
        }
    }
}