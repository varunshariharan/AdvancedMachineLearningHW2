import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 3/2/13
 * Time: 12:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class GM {
    public List<Variable> variableList;
    public List<Factor> factors;

    public GM(List<Variable> variableList, List<Factor> factors) {
        this.variableList = variableList;
        this.factors = factors;
    }

}
