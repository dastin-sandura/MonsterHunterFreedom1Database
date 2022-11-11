package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MeatIngredientRepository {
    private static final Logger log = new Logger(Logger.LoggingLevel.INFO);

    List<String> dbLines;

    public MeatIngredientRepository() {
        try {
            dbLines = Files.readAllLines(Paths.get("./src/main/resources/felyne-kitchen/ingredient/meat_ingredient.db"));
        } catch (IOException ioe) {
            log.logError(ioe);
        }
    }
}
