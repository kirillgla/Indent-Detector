package indentDetector;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubstringDetectorTest {
    @Test
    public void testSubstringDetectorOnTypo() {
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

    @Test
    public void testSubstringDetectorOnComplicatedExpression() {
        SubstringDetector detector = new SubstringDetector("\\Wdefault\\s*:", Integer.MAX_VALUE);

        boolean d = detector.nextChar('d');
        boolean e = detector.nextChar('e');
        boolean f = detector.nextChar('f');
        boolean a = detector.nextChar('a');
        boolean u = detector.nextChar('u');
        boolean l = detector.nextChar('l');
        boolean t = detector.nextChar('t');
        boolean colon = detector.nextChar(':');

        assertFalse(d);
        assertFalse(e);
        assertFalse(f);
        assertFalse(a);
        assertFalse(u);
        assertFalse(l);
        assertFalse(t);
        assertTrue(colon);
    }

    @Test
    public void testSubstringDetectorOnPrefixWord() {
        SubstringDetector detector = new SubstringDetector("\\Wbreak\\W", 7);

        boolean w = detector.nextChar('w');
        boolean b = detector.nextChar('b');
        boolean r = detector.nextChar('r');
        boolean e = detector.nextChar('e');
        boolean a = detector.nextChar('a');
        boolean k = detector.nextChar('k');
        boolean ending = detector.nextChar(';');

        assertFalse(w);
        assertFalse(b);
        assertFalse(r);
        assertFalse(e);
        assertFalse(a);
        assertFalse(k);
        assertFalse (ending);
    }
}
