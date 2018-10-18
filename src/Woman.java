/*
 * Encapsulates info for the `males` in the canonical sense of the algorithm.
 * Any man's final matching will be provably optimal.
 */

import java.util.HashMap;

public class Woman extends Human {

    public String name;
    private HashMap<Man, Integer> preferences;
    public Man partner;

    public Woman(String _name) {
        this.name = _name;
        this.partner = null;
        this.preferences = new HashMap<>();
    }

    public Integer getPriority(Man man) {
        return preferences.get(man);
    }

    public void addSuitor(Man suitor, Integer priority) {
        preferences.put(suitor, priority);
    }

}
