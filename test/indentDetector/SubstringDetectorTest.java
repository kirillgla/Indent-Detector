package indentDetector;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubstringDetectorTest {
    @Test
    public void testSubstringDetectorOnWrongString() {
        SubstringDetector detector = new SubstringDetector("\\Wbreak\\W", 7);

        boolean b = detector.nextChar('b');
        boolean l = detector.nextChar('l');
        boolean e = detector.nextChar('e');
        boolean a = detector.nextChar('a');
        boolean k = detector.nextChar('k');
        boolean ending = detector.nextChar(';');

        assertFalse(b);
        assertFalse(l);
        assertFalse(e);
        assertFalse(a);
        assertFalse(k);
        assertFalse(ending);
    }

    @Test
    public void testSubstringDetectorOnCorrectString() {
        SubstringDetector detector = new SubstringDetector("\\Wbreak\\W", 7);

        boolean b = detector.nextChar('b');
        boolean r = detector.nextChar('r');
        boolean e = detector.nextChar('e');
        boolean a = detector.nextChar('a');
        boolean k = detector.nextChar('k');
        boolean ending = detector.nextChar(';');

        assertFalse(b);
        assertFalse(r);
        assertFalse(e);
        assertFalse(a);
        assertFalse(k);
        assertTrue(ending);
    }
}
