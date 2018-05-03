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
                {"public static void main(String[] args) {", new BraceLayout(1, 0)},
                {"try { System.out.println(); } catch (Exception e) {", new BraceLayout(2, 1)},
                {"System.out.println();", new BraceLayout(0, 0)},
                {"}", new BraceLayout(0, 1)},
                {"} catch (Exception e) {", new BraceLayout(1, 1)}
        };
    }

    @Test
    public void testGetBraceLayout() throws Exception {
        BraceLayout result = CodeLine.getBraceLayout(input);

        assertEquals(expected, result);
    }
}
