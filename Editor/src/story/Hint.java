package story;

import org.json.JSONArray;
import org.json.JSONObject;

public class Hint {
    private String[] hints = new String[3];
    private String[] costs = new String[3];

    public String[] getHints() {
        return hints;
    }

    public void setHints(String[] hints) {
        this.hints = hints;
    }

    public String[] getCosts() {
        return costs;
    }

    public void setCosts(String[] costs) {
        this.costs = costs;
    }

    public Hint(String h1, String h2, String h3, String c1, String c2, String c3){
        this.hints[0] = h1;
        this.hints[1] = h2;
        this.hints[2] = h3;
        this.costs[0] = c1;
        this.costs[1] = c2;
        this.costs[2] = c3;
    }

    public Hint(){
    }

    public JSONArray getJSON(){
        JSONArray hints = new JSONArray();
        for(int i = 0; i < this.hints.length; i++){
            JSONObject tmp = new JSONObject();
            tmp.put("hint", this.hints[i]);
            tmp.put("cost", this.costs[i]);
            hints.put(tmp);
        }
        return hints;
    }
}
