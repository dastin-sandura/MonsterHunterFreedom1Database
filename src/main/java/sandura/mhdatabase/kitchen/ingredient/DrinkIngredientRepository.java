package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DrinkIngredientRepository {

    private static final String DATABASE_FILE_PATH = "./src/main/resources/felyne-kitchen/ingredient/drink_ingredient.db";

    private Logger logger = new Logger(Logger.LoggingLevel.INFO);

    private List<String> databaseFileContents;

    public DrinkIngredientRepository() throws IOException{
        Path path = Paths.get(DATABASE_FILE_PATH);
        databaseFileContents= Files.readAllLines(path);
        logger.logInfo(databaseFileContents);
    }

    public List<String> getDatabaseFileContents() {
        return databaseFileContents;
    }

}
