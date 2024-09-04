package tracker.commands;

public interface Command {
    static <T extends Enum<T> & Command> T fromString(Class<T> enumType, String command) {
        for (T enumConstant : enumType.getEnumConstants()) {
            if (enumConstant.getMessage().equalsIgnoreCase(command)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Unknown command: " + command);
    }

    String getMessage();
}
