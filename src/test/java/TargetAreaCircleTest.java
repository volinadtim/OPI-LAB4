import my.web.area.TargetAreaCircle;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class TargetAreaCircleTest {
    private TargetAreaCircle targetArea;

    @Before
    public void setUp() {
        targetArea = new TargetAreaCircle(2);
    }

    @Test
    public void testHit() {
        assertTrue(targetArea.contains(1, 1, 1));
        assertTrue(targetArea.contains(2, 2, 4));
        assertFalse(targetArea.contains(-5, -5, 1));
    }

}