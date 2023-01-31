import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class Duke {
    static private final String HI = "\n" +
            "██████╗░░█████╗░██████╗░\n" +
            "██╔══██╗██╔══██╗██╔══██╗\n" +
            "██████╦╝██║░░██║██████╦╝\n" +
            "██╔══██╗██║░░██║██╔══██╗\n" +
            "██████╦╝╚█████╔╝██████╦╝\n" +
            "╚═════╝░░╚════╝░╚═════╝░";
    static private final String BYE = "\n" +
            "████████████████████████████████████████████████████████████████\n" +
            "█▄─▄─▀█─▄▄─█▄─▄─▀███─▄▄▄▄██▀▄─██▄─█─▄█─▄▄▄▄███▄─▄─▀█▄─█─▄█▄─▄▄─█\n" +
            "██─▄─▀█─██─██─▄─▀███▄▄▄▄─██─▀─███▄─▄██▄▄▄▄─████─▄─▀██▄─▄███─▄█▀█\n" +
            "▀▄▄▄▄▀▀▄▄▄▄▀▄▄▄▄▀▀▀▀▄▄▄▄▄▀▄▄▀▄▄▀▀▄▄▄▀▀▄▄▄▄▄▀▀▀▄▄▄▄▀▀▀▄▄▄▀▀▄▄▄▄▄▀";
    static private final String DIV = "\n===========================================================================\n";
    static private final Task[] tasks = new Task[100];
    static private int taskCount = 0;

    public static void hello() {
        System.out.println(HI);
    }

    public static void shutdown() {
        System.out.println("\n" + BYE);
    }

    public static void query() {
        System.out.println(DIV + "\nWhat do you want from me boss?\n" + DIV);
    }

    public static void listOut() {
        if (taskCount == 0) {
            System.out.println("*Tumbleweed passes by*\nUh oh! Looks like your list is empty!");
            return;
        } else {
            System.out.println("Here's your list boss! *Crosses arms and nods* : ");
        }
        for (int i = 0; i < taskCount; ++i) {
            System.out.println(tasks[i].getNumber() + "." + tasks[i]);
        }
    }

    public static  void printError() {
        System.out.println("Wrong words boss! Try again!");
    }

    public static int convertString(String str) throws DukeException {
        int num = -1;
        str = str.trim();
        try {
            num = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number boss! Try again!");
        }
        if (num < 1 || num > taskCount) {
            System.out.println("Wrong number boss! Try again!");
            throw new DukeException();
        }
        return num;
    }

    public static void checkSameDone(int index, boolean changeTo, String type) throws DukeException{
        if (tasks[index - 1].isDone == changeTo) {
            System.out.println("It is already " + type + "ed! *Shakes head* ");
            throw new DukeException();
        }
    }

    public static void command() {
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("What do you want: ");
            String cmd = in.next();
            String checkCmd = cmd.toLowerCase();
            if (cmd.equals("bye")) {
                return;
            }
            System.out.println(DIV);
            switch (checkCmd) {
            case "list": {
                listOut();
                break;
            }
            case "mark": {
                String next = in.nextLine();
                int num;
                try {
                                        num = convertString(next);
                    checkSameDone(num, false, checkCmd);
                } catch (DukeException e) {
                    break;
                }
                System.out.println("Bob commends you! *Nods head* ");
                tasks[num - 1].setDone(true);
                System.out.println(tasks[num - 1]);
                break;
            }
            case "unmark": {
                String next = in.nextLine();
                int num;
                try {
                    num = convertString(next);
                    checkSameDone(num, true, checkCmd);
                } catch (DukeException e) {
                    break;
                }
                System.out.println("A mistake! *Shakes head* ");
                tasks[num - 1].setDone(false);
                System.out.println(tasks[num - 1]);
                break;
            }
            case "todo": {
                String next = in.nextLine();
                System.out.println("Understood! *Salutes* Task added!");
                tasks[taskCount] = new ToDo(next.stripLeading(), taskCount + 1, false);
                System.out.println(tasks[taskCount]);
                ++taskCount;
                break;
            }
            case "deadline": {
                String next = in.nextLine();
                String[] deadline;
                try {
                    deadline = next.split("/by", 2);
                } catch (PatternSyntaxException e) {
                    printError();
                    break;
                }
                System.out.println("Understood *Salutes* Task with deadline added!\n"
                        + "Remember to complete it by the deadline!");
                tasks[taskCount] = new Deadline(deadline[0].trim(), taskCount + 1, false,
                        deadline[1].trim());
                System.out.println(tasks[taskCount]);
                ++taskCount;
                break;
            }
            case "event": {
                String next = in.nextLine();
                String[] eventName;
                String[] eventTime;
                try {
                    eventName = next.split("/from", 2);
                    eventTime = eventName[1].split("/to", 2);
                } catch (PatternSyntaxException e) {
                    printError();
                    break;
                }
                System.out.println("Understood *Salutes* Event added!\n"
                        + "Remember the starting time! Don't be late!");
                tasks[taskCount] = new Event(eventName[0].trim(), taskCount + 1, false,
                        eventTime[0].trim(), eventTime[1].trim());
                System.out.println(tasks[taskCount]);
                ++taskCount;
                break;
            }
            default:
                printError();
                in.nextLine();
            }
            System.out.println(DIV);
        } while (true);
    }

    public static void main(String[] args) {
        hello();
        query();
        command();
        shutdown();
    }
}