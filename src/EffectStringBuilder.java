import java.util.Arrays;

public class EffectStringBuilder {
    // based on https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
    // and this https://www.baeldung.com/linux/formatting-text-in-terminals
    // and this https://invisible-island.net/xterm/ctlseqs/ctlseqs.html
    // and this https://youtu.be/kT4JYQi9w4w?si=VWOym5rtegyViccr
    public final StringBuilder builder;
    public final String ESC = "\033[", M = "m", BOLD = "1", UNBOLD = "22";


    EffectStringBuilder() {
        this("");
    }

    EffectStringBuilder(String initial) {
        this.builder = new StringBuilder(initial);
    }

    EffectStringBuilder append(String text) {
        builder.append(text);
        return this;
    }

    String escapeSequence(String... innerText) {
        return ESC + String.join(",", innerText) + M;
    }

    String escapeSequence(int... escapeCodes) {
        String[] stringCodes = Arrays.stream(escapeCodes).mapToObj(String::valueOf).toArray(String[]::new);
        return escapeSequence(stringCodes);
    }

    private EffectStringBuilder es(String... innerText) {
        builder.append(escapeSequence(innerText));
        return this;
    }

    private EffectStringBuilder es(int... escapeCodes) {
        builder.append(escapeSequence(escapeCodes));
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public EffectStringBuilder appendBold(String text) {
        return es(BOLD).append(text).es(UNBOLD);
    }

    public EffectStringBuilder bold() {
        return es(BOLD);
    }

    public EffectStringBuilder unbold() {
        return es(UNBOLD);
    }

    public static void main(String[] args) {
        System.out.println(new EffectStringBuilder().appendBold("What").append(" pain ").bold().append("will ").append("it ").unbold().append("take"));
    }
}
