package indentDetector;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CodeLineTest {
    // ==== parse tests (non-parameterized) ====

    @Test(expected = IllegalArgumentException.class)
    public void testParseWhitespaceLine() throws Exception {
        String input = "    ";

        CodeLine.parse(input, "", new ArrayList<>());
    }

    // ==== getIndentType tests ====

    @Test
    public void testGetIndentTypeOnSpaces() {
        String input = "    public static void main(string[] args) {";

        IndentType indentType = CodeLine.getIndentType(input);

        assertEquals(IndentType.Spaces, indentType);
    }

    @Test
    public void testGetIndentTypeOnTabs() {
        String input = "\tpublic static void main(string[] args) {";

        IndentType indentType = CodeLine.getIndentType(input);

        assertEquals(IndentType.Tabs, indentType);
    }

    @Test
    public void testGetIndentTypeOnMixed() {
        String input = " \t   public static void main(string[] args) {";

        IndentType indentType = CodeLine.getIndentType(input);

        assertEquals(IndentType.Mixed, indentType);
    }

    @Test
    public void testGetIndentTypeOnUnknown() {
        String input = "package indentDetector;";

        IndentType indentType = CodeLine.getIndentType(input);

        assertEquals(IndentType.Unknown, indentType);
    }
}