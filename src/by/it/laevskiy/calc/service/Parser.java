package by.it.laevskiy.calc.service;

import by.it.laevskiy.calc.constant.Patterns;
import by.it.laevskiy.calc.exception.CalcException;
import by.it.laevskiy.calc.model.Var;
import by.it.laevskiy.calc.repository.VarRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Map<String, Integer> map = Map.of(
            "=", 0,
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2);

    private VarRepository varRepository;
    private VarCreator varCreator;

    public Parser(VarRepository varRepository) {
        this.varRepository = varRepository;
    }

    public Parser(VarRepository varRepository, VarCreator varCreator) {
        this.varRepository = varRepository;
        this.varCreator = varCreator;
    }

    public Var evaluate(String expression) throws CalcException {
        //2+2 {1,3}*5
        expression = expression
                .replace(" ", "")
                .trim();

        List<String> operands = new ArrayList<>(List.of(expression.split(Patterns.OPERATION)));
        List<String> operations = new ArrayList<>();
        Matcher matcher = Pattern.compile(Patterns.OPERATION).matcher(expression);
        while (matcher.find()) {
            operations.add(matcher.group());
        }

        while (operations.size() > 0) {
            int index = getIndex(operations);
            String operation = operations.remove(index);
            String left = operands.remove(index);
            String right = operands.remove(index);
            Var var = oneOperation(left, operation, right);
            operands.add(index, var.toString());
        }
        return varCreator.create(operands.get(0));
    }

    private Var oneOperation(String stingLeftVar, String operation, String stingRightVar) throws CalcException {

        Var right = varCreator.create(stingRightVar);
        if (operation.equals("=")) {
            varRepository.save(stingLeftVar, right);
            return right;
        }


        Var left = varCreator.create(stingLeftVar);
        switch (operation) {
            case "+":
                return left.add(right);
            case "-":
                return left.sub(right);
            case "*":
                return left.mul(right);
            case "/":
                return left.div(right);
        }
        throw new CalcException("Something stupid");
    }

    private int getIndex(List<String> operation) {
        int index = -1;
        int priority = -1;
        for (int i = 0; i < operation.size(); i++) {
            String currenOperation = operation.get(i);
            Integer currentPriority = map.get(currenOperation);
            if (priority < currentPriority) {
                index = i;
                priority = currentPriority;
            }
        }
        return index;
    }
}