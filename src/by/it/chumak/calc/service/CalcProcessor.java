package by.it.chumak.calc.service;

import by.it.chumak.calc.exception.CalcException;
import by.it.chumak.calc.model.ResourceManager;
import by.it.chumak.calc.model.Var;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CalcProcessor {

    public CalcProcessor() {
    }

    public Var calc(String operation, Var varLeftPartExpression, Var varRightPartExpression, ResourceManager resourceManager) throws CalcException {
        return switch (operation) {
            case "+" -> performCalculations("add", varLeftPartExpression, varRightPartExpression, resourceManager);
            case "-" -> performCalculations("sub", varLeftPartExpression, varRightPartExpression, resourceManager);
            case "*" -> performCalculations("mul", varLeftPartExpression, varRightPartExpression, resourceManager);
            case "/" -> performCalculations("div", varLeftPartExpression, varRightPartExpression, resourceManager);
            default -> null;
        };
    }

    @SuppressWarnings("deprecation")
    private Var performCalculations(String operation, Var varLeftPartExpression, Var varRightPartExpression, ResourceManager resourceManager) throws CalcException {
        String fullClassName = varLeftPartExpression.getClass().getPackageName() + "." + "MathExpressions";
        fullClassName = fullClassName.replace("model", "service");
        try {
            Class<?> desiredClass = Class.forName(fullClassName);
            Object instanceClass = desiredClass.newInstance();
            Class<?>[] arrayParametersFirst = new Class[]{ResourceManager.class};
            Class<?>[] arrayParametersSecond = new Class[]{Var.class};
            Method method = desiredClass.getDeclaredMethod(operation, arrayParametersFirst[0], arrayParametersSecond[0], arrayParametersSecond[0]);
            Object abc = method.invoke(instanceClass, resourceManager, varLeftPartExpression, varRightPartExpression);
            return (Var) abc;
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new CalcException(resourceManager, exception);
        }
    }

}
