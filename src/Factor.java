import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 3/2/13
 * Time: 12:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Factor {
    public List<Integer> variables;
    public Map<String, Double> table;

    public Factor() {
        this.variables = new ArrayList<Integer>();
        this.table = new HashMap<String, Double>();
    }
}
