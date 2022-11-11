package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;

public class DrinkIngredientRepositoryTest {

    private Logger logger = new Logger(Logger.LoggingLevel.DEBUG);

    public static void main(String[] args) throws IOException {
        DrinkIngredientRepositoryTest tests = new DrinkIngredientRepositoryTest();

        tests.testRepositoryConstructor();
    }

    public void testRepositoryConstructor() throws IOException {
        DrinkIngredientRepository repository = new DrinkIngredientRepository();
        boolean b = repository.getDatabaseFileContents().size() == 6;
        if (!b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[1];
            logger.logError("Test failed in " + stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName());
        }
    }
}
