import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: varunhariharan
 * Date: 3/1/13
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class VariableElimination {
    static String uiaFile = "";
    static String networkType = "";// MARKOV or BAYESIAN
    static int numberOfVariables = 0;
    static int numberOfFactors = 0;
    static List<Variable> variableList;
    static List<Factor> factorList;

    public static void main(String[] args) {
        uiaFile = args[0];
        try {
            readFileAndGetVariables();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static void readFileAndGetVariables() throws IOException {
        int lineNumber = 0;
        File uiaFile = new File(VariableElimination.uiaFile);
        List<String> uiaLines = FileUtils.readLines(uiaFile);
        networkType = uiaLines.get(lineNumber++);   // 0
        numberOfVariables = Integer.parseInt(uiaLines.get(lineNumber++));   // 1
        String[] dValues = uiaLines.get(lineNumber++).split(" ");           // 2
        numberOfFactors = Integer.parseInt(uiaLines.get(lineNumber++));   // 3

        variableList = new ArrayList<Variable>();
        factorList = new ArrayList<Factor>();

        for (int index = 0; index < numberOfVariables; index++){
            variableList.add(new Variable(Integer.parseInt(dValues[index]),index));
        }

        //create factors based on each function
        for (int factorIndex = 0; factorIndex < numberOfFactors; factorIndex++){
            String[] factorLine;
            if(uiaLines.get(lineNumber).contains("\t")){
                factorLine = uiaLines.get(lineNumber++).split("\t");
            }
            else {
                factorLine = uiaLines.get(lineNumber++).split(" ");
            }
//            System.out.println(uiaLines.get(lineNumber-1));
            int numberOfVariablesInvolved = Integer.parseInt(factorLine[0]);
            Factor factor = new Factor();

            for (int factorVariableIndex = 0; factorVariableIndex < numberOfVariablesInvolved; factorVariableIndex++){
                int variableIndex = Integer.parseInt(factorLine[factorVariableIndex + 1]);
                factor.variables.add(variableList.get(variableIndex).value);
            }

            factorList.add(factor);
        }
        //accommodate for line break
        //create table
        lineNumber++;
        for (int factorIndex = 0; factorIndex < numberOfFactors; factorIndex++){

            Factor factor = factorList.get(factorIndex);
            List<Integer> factorVariables = factor.variables;
            System.out.print("ø(");
            for (Integer factorVariable : factorVariables) {
                System.out.print(factorVariable + " ");
            }
            System.out.println(")");
//            int numberOfVariablesConcerned = Integer.parseInt(uiaLines.get(lineNumber++));
            lineNumber++;
            String[] tableLineSplit = uiaLines.get(lineNumber++).split(" ");

//            for (int index = 0; index < numberOfVariablesConcerned; index++){
//                factorList.get(factorIndex).table.add(Double.parseDouble(tableLineSplit[index]));
//            }
            FactorUtility factorUtility = new FactorUtility(factor,variableList,factorList,tableLineSplit);
        }
        System.out.println("All factors done");
        GM gm = new GM(variableList,factorList);
    }

//    private void recursiveGetVariableValue(List<Variable> factorVariables, int variableIndex, int[] variableCurrentValues, String[] tableLineSplit, int lineSplitIndex) {
//
//        Variable variable = variableList.get(variableIndex);
//        for (int i = 0; i < variable.d; i++){
//            variableCurrentValues[variableIndex] = i;
//            //if we are the last variable, do not recurse
//            if(variableIndex == (factorVariables.size() - 1)){
//                System.out.print("\n[");
//                for (int variableCurrentValue : variableCurrentValues) {
//                    System.out.print(variableCurrentValue + " ");
//                }
//                System.out.println("] \t=\t"+tableLineSplit[lineSplitIndex++]);
//                if (i == variable.d-1){
//                    variableCurrentValues[variableIndex] = 0;
//                    variableIndex--;
//                }
//            }
//            else {
//                recursiveGetVariableValue(factorVariables, ++variableIndex, variableCurrentValues, tableLineSplit, lineSplitIndex);
//            }
//        }
//
//    }
}
