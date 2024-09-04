package tracker;

import tracker.states.MainState;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(MainState.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();
            context.handleCommand(command);
        }

    }
}
