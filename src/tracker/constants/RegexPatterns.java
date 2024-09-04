package tracker.constants;

public enum RegexPatterns {
    NAME_PATTERN("^[A-Za-z][a-zA-Z]*(?:[-'][a-zA-Z]+)*(?: [A-Z][a-zA-Z]*(?:[-'][a-zA-Z]+)*)*$"),
    LAST_NAME_PATTERN("^[A-Za-z][a-zA-Z]*(?:[-'][a-zA-Z]+)*(?: [a-zA-Z]+)*(?: [a-zA-Z]+(?:[-'][a-zA-Z]+)*)*$"),
    EMAIL_PATTERN("^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z\\d.-]+$");
    private final String regex;

    RegexPatterns(String pattern) {
        this.regex = pattern;
    }

    public String getRegex() {
        return regex;
    }
}
