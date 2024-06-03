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
        repository.loadDataFromDirectory(DrinkIngredientRepository.DATABASE_FILE_PATH);
        boolean b = repository.getDatabaseFileContents().size() == 5;
        System.out.println(repository.getAsMap());
        if (!b) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[1];
            logger.logError("Test failed in " + stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName());
        }
    }
}
