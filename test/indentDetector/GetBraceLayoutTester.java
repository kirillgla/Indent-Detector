package indentDetector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class GetBraceLayoutTester {
    private String input;
    private ArrayList<OpenBraceType> context;
    private BraceLayout expected;

    public GetBraceLayoutTester(String input, ArrayList<OpenBraceType> context, BraceLayout expected) {
        this.input = input;
        this.context = context;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Object[][] layoutExamples() {
        return new Object[][]{
                {
                        "public static void main(String[] args) {",
                        new ArrayList<>(),
                        new BraceLayout(1, 0, false)
                },
                {
                        "try { System.out.println(); } catch (Exception e) {",
                        new ArrayList<>(),
                        new BraceLayout(2, 1, false)
                },
                {
                        "System.out.println();",
                        new ArrayList<>(),
                        new BraceLayout(0, 0, false)
                },
                {
                        "}",
                        new ArrayList<OpenBraceType>() {{
                            add(OpenBraceType.Other);
                        }},
                        new BraceLayout(0, 1, true)
                },
                {
                        "} catch (Exception e) {",
                        new ArrayList<OpenBraceType>() {{
                            add(OpenBraceType.Other);
                        }},
                        new BraceLayout(1, 1, true)
                },
                {
                        "} else if (arg == null) {",
                        new ArrayList<OpenBraceType>() {{
                            add(OpenBraceType.Other);
                        }},
                        new BraceLayout(1, 1, true)
                }
        };
    }

    @Test
    public void testGetBraceLayout() throws Exception {
        BraceLayout result = new Parser() {{
            openBraces = context;
        }}.getBraceLayout(input);

        assertEquals(expected, result);
    }
}
