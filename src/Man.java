/*
 * Encapsulates info for the `males` in the canonical sense of the algorithm.
 * Any woman's final matching is not necessarily provably optimal.
 */

import java.util.LinkedList;
import java.util.HashMap;

public class Man extends Human {

    public String name;
    public LinkedList<Woman> picks;
    public HashMap<Woman, Integer> preferences;
    public Woman partner;

    public Man(String _name) {
        this.name = _name;
        this.partner = null;
        this.preferences = new HashMap<>();
        this.picks = new LinkedList<>();
    }

    public void addPick(Woman pick, Integer priority) {
        preferences.put(pick, priority);
        picks.add(pick);
    }

    private int compare(Woman a, Woman b) {
        Integer first; Integer second;
        first = preferences.get(a);
        second = preferences.get(b);
        return first < second ? -1
                : first > second ? 1
                : 0;
    }
}
