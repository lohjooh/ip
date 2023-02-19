package duke;

import duke.parser.Parser;
import duke.tasklist.DataManager;
import duke.ui.DukeMessages;
import duke.util.DukeException;

public class Duke {

    private final DataManager dm;
    private final DukeMessages ui;
    private final Parser parser;

    public Duke(String path) {
        ui = new DukeMessages();
        parser = new Parser(ui);
        dm = new DataManager(path, ui, parser);
        try {
            dm.initialize();
        } catch (DukeException e) {
            ui.printFileFatalError();
        }
        run();
    }

    public void run() {
        dm.run();
        do {
            String parsedCommand = parser.run();
            if (parsedCommand.equals("bye")) {
                break;
            }
            ui.printDiv();
            String next;
            try {
                next = parser.check();
            } catch (DukeException e) {
                ui.printError();
                ui.printDiv();
                continue;
            }
            dm.command(parsedCommand, next);
            ui.printDiv();
        } while (true);
        ui.printBye();
    }

    public static void main(String[] args) {
        String path = "data\\tasks.txt";
        new Duke(path);
    }
}