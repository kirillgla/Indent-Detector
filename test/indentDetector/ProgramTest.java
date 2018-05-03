package indentDetector;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("ConstantConditions")
public class ProgramTest {
    @Test
    public void testIsNullOrWhitespaceOnNull() {
        String arg = null;

        boolean result = Program.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhitespaceOnEmptyString() {
        String arg = "";

        boolean result = Program.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhiteSpaceOnWhitespaceString() {
        String arg = "  \t ";

        boolean result = Program.isNullOrWhitespace(arg);

        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhitespaceOnNonEmptyString() {
        String arg = "    System.out.println(\"Hello, world!\");";

        boolean result = Program.isNullOrWhitespace(arg);

        assertFalse(result);
    }
}