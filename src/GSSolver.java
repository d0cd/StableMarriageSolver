/*
 *  A Stable Marriage solver implementing the Gale-Shapely algorithm.
 *  Documentation on the algorithm can be found here:
 *  https://en.wikipedia.org/wiki/Stable_marriage_problem
 */

import java.io.*;
import java.util.*;

public class GSSolver {

    private ArrayDeque<Man> queue;
    private HashMap<String, Human> all;

    /*
     * Constructor for solver.
     */
    public GSSolver (String filename) {
        this.all = new HashMap<>();
        this.queue = new ArrayDeque<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            Integer n; Integer m;
            String[] params = reader.readLine().split(",");
            n = Integer.parseInt(params[0]);
            m = Integer.parseInt(params[1]);

            String[] suitors = reader.readLine().split(",");
            for (int i = 0; i < suitors.length; i++) {
                Man suitor = new Man(suitors[i]);
                all.put(suitor.name, suitor);
                queue.add(suitor);
            }

            String[] picks = reader.readLine().split(",");
            for (int i = 0; i < picks.length; i++) {
                Woman pick = new Woman(picks[i]);
                all.put(pick.name, pick);
            }
            for (int i = 0; i < n; i++) {
                String[] data = reader.readLine().split(",");
                Man suitor = (Man) all.get(data[0]);
                for (int j = 1; j < data.length; j++) {
                    suitor.addPick((Woman) all.get(data[j]), -1 * j);
                }
            }
            for (int i = 0; i < m; i++) {
                String[] data = reader.readLine().split(",");
                Woman pick = (Woman) all.get(data[0]);
                for (int j = 1; j < data.length; j++) {
                    pick.addSuitor((Man) all.get(data[j]), -1 * j);
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error reading the file. Check f");
        }
    }

    /*
     * Produces a stable marriage pairing.
     */
    public void solve () {
        while (!queue.isEmpty()) {
            Man suitor = queue.removeFirst();
            Woman choice = suitor.picks.removeFirst();
            if (choice.partner == null) {
                marry(suitor, choice);
            } else {
                if (choice.getPriority(choice.partner) < choice.getPriority(suitor)) {
                    unmarry(choice.partner, choice);
                    marry(suitor, choice);
                } else {
                    queue.add(suitor);
                }

            }
        }
    }

    /*
     * Prints out the matchings. Currently redundant. Must be called after `solve` for correctness.
     */
    public void printMatchings() {
        Iterator<String> names = all.keySet().iterator();
        while (names.hasNext()) {
            Human h = all.get(names.next());
            if (h instanceof Man) {
                Man suitor = (Man) h;
                System.out.println(suitor.name + " -- " + suitor.partner.name);
            }
        }
    }

    /*
     * Main method for solver. Takes the name of a CSV file as input.
     */
    public static void main(String[] args) {
        assert(args.length == 1);
        GSSolver solver = new GSSolver(args[1]);
        solver.solve();
        solver.printMatchings();
    }

    private void marry(Man man, Woman woman) {
        man.partner = woman;
        woman.partner = man;
    }

    private void unmarry(Man man, Woman woman) {
        man.partner = null;
        woman.partner = null;
        queue.add(man);
    }

}
