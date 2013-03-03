import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 3/2/13
 * Time: 8:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class FactorUtility {

    Factor factor;
    List<Variable> variableList;
    List<Factor> factorList;
    String[] tableLineSplit;
    int lineSplitIndex = 0;

    public FactorUtility(Factor factor, List<Variable> variableList, List<Factor> factorList, String[] tableLineSplit) {
        this.factor = factor;
        this.variableList = variableList;
        this.tableLineSplit = tableLineSplit;
        this.factorList = factorList;
        int variableIndex = 0;
        int[] variableCurrentValues = new int[factor.variables.size()];
        recursiveGetVariableValue(factor.variables,variableIndex,variableCurrentValues,tableLineSplit);
        System.out.println("\n");
    }

    private void recursiveGetVariableValue(List<Integer> factorVariables, int variableIndex, int[] variableCurrentValues, String[] tableLineSplit) {
        Variable variable = variableList.get(variableIndex);
        for (int i = 0; i < variable.d; i++){
            variableCurrentValues[variableIndex] = i;
            //if we are the last variable, do not recurse
            if(variableIndex == (factorVariables.size() - 1)){
                System.out.print("\n[");
                String mapInputString = "";
                for (int variableCurrentValue : variableCurrentValues) {
                    System.out.print(variableCurrentValue + " ");
                    mapInputString += (variableCurrentValue+ " ");
                }
                System.out.println("] \t=\t"+tableLineSplit[lineSplitIndex]);
                factor.table.put(mapInputString,Double.parseDouble(tableLineSplit[lineSplitIndex++]));
            }
            else {
                recursiveGetVariableValue(factorVariables, ++variableIndex, variableCurrentValues, tableLineSplit);
                variableCurrentValues[variableIndex] = 0;
                variableIndex--;
            }
        }

    }

    public Factor multiply(Factor f1, Factor f2){
        Factor resultFactor = new Factor();
        List<Integer> list1 = f1.variables;
        List<Integer> list2 = f2.variables;
        resultFactor.variables = merge(list1,list2);
        List<Integer> intersect = intersectVariables(list1, list2);
        List<Integer> intersectIndices1 = new ArrayList<Integer>();
        List<Integer> intersectIndices2 = new ArrayList<Integer>();
        for (Integer integer : intersect) {
            intersectIndices1.add(f1.variables.indexOf(integer));
            intersectIndices2.add(f2.variables.indexOf(integer));
        }
        for (Map.Entry<String, Double> f1Entry : f1.table.entrySet()) {
            for (Map.Entry<String, Double> f2Entry : f2.table.entrySet()) {
                boolean flag = true;
                double product = 0.0;
                String[] f1Values = f1Entry.getKey().split(" ");
                String[] f2Values = f2Entry.getKey().split(" ");
                double f1Value = f1Entry.getValue();
                double f2Value = f2Entry.getValue();
                for (int index = 0; index < intersect.size(); index++) {
                    if(!f1Values[intersectIndices1.get(index)].equals(f2Values[intersectIndices2.get(index)])){
                        flag = false;
                    }
                }
                if (flag){
                    //multiply values and put into new map
                    product = f1Value * f2Value;
                    String keyForNewMap = "";
                    for (Integer variable : resultFactor.variables) {
                        if(f1.variables.contains(variable)){
                            keyForNewMap += (String.valueOf(f1Values[f1.variables.indexOf(variable)]) + " ");
                        } else {
                            keyForNewMap += (String.valueOf(f2Values[f2.variables.indexOf(variable)]) + " ");
                        }
                        resultFactor.table.put(keyForNewMap,product);
                    }
                }
            }
        }
        //merge tables. This is the tough part :(
        return null;//CHANGE THIS LATER
    }

    private List<Integer> intersectVariables(List<Integer> list1, List<Integer> list2) {
        List<Integer> result = new ArrayList<Integer>();
        for (Integer variable : list1) {
            if(list2.contains(variable))
                result.add(variable);
        }
        return result;

    }

    private List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> resultList = new ArrayList<Integer>();
        int i = 0, j = 0;
        while(i < list1.size() && j < list2.size()){
            if (list1.get(i) == list2.get(j)){
                resultList.add(list1.get(i++));
                j++;
            }
            else if (list1.get(i) > list2.get(j)) {
                resultList.add(list2.get(j++));
            }
            else if (list1.get(i) < list2.get(j)) {
                resultList.add(list1.get(i++));
            }
        }
        while (i < list1.size()){
            //put the rest of i in the merge
            resultList.add(list1.get(i++));
        }
        while (j < list2.size()) {
            //put the rest of j in the merge
            resultList.add(list2.get(j++));
        }
        return resultList;
    }

    public Factor sumOut (Factor factor){

        return null;//CHANGE THIS LATER
    }

    private void recursiveMultiply(List<Variable> factorVariables, int variableIndex, int[] variableCurrentValues, Factor factor1, Factor factor2) {
        Variable variable = variableList.get(variableIndex);
        for (int i = 0; i < variable.d; i++){
            variableCurrentValues[variableIndex] = i;
            //if we are the last variable, do not recurse
            if(variableIndex == (factorVariables.size() - 1)){
                System.out.print("\n[");
                String mapInputString = "";
                for (int variableCurrentValue : variableCurrentValues) {
                    System.out.print(variableCurrentValue + " ");
                    mapInputString += (variableCurrentValue+ " ");
                }
                //multiply here
//                System.out.println("] \t=\t"+tableLineSplit[lineSplitIndex]);
//                factor.table.put(mapInputString,Double.parseDouble(tableLineSplit[lineSplitIndex++]));
            }
            else {
                recursiveMultiply(factorVariables, ++variableIndex, variableCurrentValues, factor1, factor2);
                variableCurrentValues[variableIndex] = 0;
                variableIndex--;
            }
        }

    }
}
