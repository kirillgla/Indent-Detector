package indentDetector;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class ParserTest {
    // ==== isNullOrWhitespace tests ====
    @Test
    public void testIsNullOrWhitespaceOnNull() {
        String arg = null;

        boolean result = Parser.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhitespaceOnEmptyString() {
        String arg = "";

        boolean result = Parser.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhiteSpaceOnWhitespaceString() {
        String arg = "  \t ";

        boolean result = Parser.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhitespaceOnNonEmptyString() {
        String arg = "    System.out.println(\"Hello, world!\");";

        boolean result = Parser.isNullOrWhitespace(arg);

        assertFalse(result);
    }

    // ==== parse tests (non-parameterized) ====

    @Test(expected = IllegalArgumentException.class)
    public void testParseWhitespaceLine() throws Exception {
        String input = "    ";

        new Parser().parse(input);
    }

    // ==== getIndentType tests ====

    @Test
    public void testGetIndentTypeOnSpaces() throws InvalidIndentationException {
        String input = "    public static void main(string[] args) {";

        IndentType indentType = Parser.getIndentType(input);

        assertEquals(IndentType.Spaces, indentType);
    }

    @Test
    public void testGetIndentTypeOnTabs() throws InvalidIndentationException {
        String input = "\tpublic static void main(string[] args) {";

        IndentType indentType = Parser.getIndentType(input);

        assertEquals(IndentType.Tabs, indentType);
    }

    @Test(expected = InvalidIndentationException.class)
    public void testGetIndentTypeOnMixed() throws InvalidIndentationException {
        String input = " \t   public static void main(string[] args) {";

        Parser.getIndentType(input);
    }

    @Test
    public void testGetIndentTypeOnUnknown() throws InvalidIndentationException {
        String input = "package indentDetector;";

        IndentType indentType = Parser.getIndentType(input);

        assertEquals(IndentType.Unknown, indentType);
    }
}
