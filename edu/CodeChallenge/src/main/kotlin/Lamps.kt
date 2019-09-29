import org.junit.Test;

/**
 * Stellt Euch 100 Glühbirnen in einer langen Reihe vor. Zu jeder Glühbirne gehört ein Ein/Aus-Schalter.
 *
 * Zunächst werden alle 100 Schalter bewegt, so dass alle Lampen brennen.
 * Im nächsten Schritt wird nur jeder 2. Schalter bewegt, im 3. Schritt jeder 3., im 4. Schritt jeder 4., im 5. jeder 5. und
 * das geht so weiter bis zum 99. und zum 100. Schalter.
 *
 * Und jetzt die große Preisfrage: Wie viele Lampen brennen nach dieser Aktion noch?
 */
public class Lampen {

    @Test
    public void testLampen() {
        boolean[] array = new boolean[100];

        //first step: set all bools to true
        for (int i = 0; i < array.length; i++) {
            array[i] = true;
        }

        //second step: update bools with each iteration
        for (int i = 2; i <= 100; i++) {
            for (int a = 0; a < array.length; a += i) {
                if (i <= a) {
                    array[a] = !array[a];
                }
            }
        }

        System.out.println("Lamps active: " + getTrueCount(array));
    }

    private int getTrueCount(final boolean[] array) {
        int boolCounter = 0;
        for (boolean anArray : array) {
            if (anArray) {
                boolCounter++;
            }
        }
        return boolCounter;
    }
}