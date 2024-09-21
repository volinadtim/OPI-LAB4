import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import my.web.area.TargetAreaCircle;

public class TargetAreaCircleTest {
    private TargetAreaCircle targetArea;

    @Before
    public void setUp() {
        targetArea = new TargetAreaCircle(2);
    }

    @Test
    public void testHit() {
        assertTrue(targetArea.contains(0.1f, 0.1f, 1));
        assertTrue(targetArea.contains(2, 2, 4));
        assertFalse(targetArea.contains(-5, -5, 1));
    }

}