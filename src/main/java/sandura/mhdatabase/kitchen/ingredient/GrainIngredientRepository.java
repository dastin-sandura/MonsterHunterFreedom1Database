package sandura.mhdatabase.kitchen.ingredient;

import sandura.mhdatabase.logging.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GrainIngredientRepository {

    private static final Logger log = new Logger(Logger.LoggingLevel.INFO);
    List<String> databaseLines;

    public GrainIngredientRepository() {
        try {
            databaseLines = Files.readAllLines(Paths.get("./src/main/resources/felyne-kitchen/ingredient/grain_ingredient.db"));
        } catch (IOException ioe) {
            log.logError(ioe);
        }
    }

}
