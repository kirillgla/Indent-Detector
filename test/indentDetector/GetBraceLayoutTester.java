package indentDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetBraceLayoutTester {
    private String input;
    private BraceLayout expected;

    public GetBraceLayoutTester(String input, BraceLayout expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] layoutExamples() {
        return new Object[][]{
                {"public static void main(String[] args) {", new BraceLayout(1, 0, false)},
                {"try { System.out.println(); } catch (Exception e) {", new BraceLayout(2, 1, false)},
                {"System.out.println();", new BraceLayout(0, 0, false)},
                {"}", new BraceLayout(0, 1, true)},
                {"} catch (Exception e) {", new BraceLayout(1, 1, true)},
                {"} else if (arg == null) {", new BraceLayout(1, 1, true)}
        };
    }

    @Test
    public void testGetBraceLayout() throws Exception {
        BraceLayout result = CodeLine.getBraceLayout(input);

        assertEquals(expected, result);
    }
}
