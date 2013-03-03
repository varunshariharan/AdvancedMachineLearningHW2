/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 3/1/13
 * Time: 11:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Variable implements Comparable{
    public int d;
    public boolean is_evidence;
    public int value;
    public int degree;

    public Variable(int d, int value) {
        this.d = d;
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
        Variable variable = (Variable) o;
        if (variable.value > this.value)
            return variable.value;
        else
            return this.value;
    }
}
