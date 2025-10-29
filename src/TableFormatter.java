import java.util.ArrayList;
import java.util.stream.Collectors;

public class TableFormatter {
    private String headerFormatString;
    private String separatorLine;
    private String oddRowFormatLine;
    private String evenRowFormatLine;
    private String title = "\n";
    private String tableNote = "\n";
    private boolean willPrintHeader = true;
    private boolean willPrintSeparator = true;
    private boolean willDifferEvenRows = true;
    private boolean nextRowIsOdd = true;
    ArrayList<Integer> tabs = new ArrayList<>();
    private boolean boldHeader = false;
    private String vertical = "│", horizontal = "─", cross = "┼";

    public boolean isBoldHeader() {
        return boldHeader;
    }

    public TableFormatter setBoldHeader(boolean boldHeader) {
        this.boldHeader = boldHeader;
        return this;
    }

    public TableFormatter setDelimiters(char vertical, char horizontal, char cross) {
        this.vertical = "" + vertical;
        this.horizontal = "" + horizontal;
        this.cross = "" + cross;
        return this;
    }

    public TableFormatter setTabs(int... tabSizes) {
        tabs.clear();
        ArrayList<String> tabFormats = new ArrayList<>();
        ArrayList<String> separatorParts = new ArrayList<>();
        for (int tabSize : tabSizes) {
            this.tabs.add(tabSize);
            tabFormats.add("%-" + tabSize + "s");
            separatorParts.add(horizontal.repeat(tabSize));
        }
        headerFormatString = String.join(" " + vertical + " ", boldHeader ? tabFormats.stream().map(f -> "\033[1m" + f + "\033[22m").collect(Collectors.toList()) : tabFormats);
        separatorLine = String.join(horizontal + cross + horizontal, separatorParts);
        oddRowFormatLine = String.join(" " + vertical + " ", tabFormats);
        evenRowFormatLine = "\033[47m" + oddRowFormatLine + "\033[49m";
        return this;
    }

    public String formatHeader(String... items) {
        return String.format(headerFormatString, (Object[]) items);
    }

    public String formatSeparator() {
        return separatorLine;
    }

    public String formatAnyRow(boolean isOdd, String... items) {
        return String.format(isOdd? oddRowFormatLine : evenRowFormatLine, (Object[]) items);
    }

    public String formatNextRow(String... items) {
        nextRowIsOdd = !nextRowIsOdd;
        return formatAnyRow(!nextRowIsOdd, items);
    }

    public String formatRow(String... items) {
        return formatAnyRow(true, items);
    }

    public String formatEvenRow(String... items) {
        return formatAnyRow(false, items);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTableNote(String tableNote) {
        this.tableNote = tableNote;
    }

    public String formatTitle(String... titleItems) {
        return String.format(title, (Object[]) titleItems);
    }

    public String formatTitle() {
        return title;
    }

    public String formatTableNote(String... noteItems) {
        return String.format(tableNote, (Object[]) noteItems);
    }

    public String formatTableNote() {
        return tableNote;
    }
}
