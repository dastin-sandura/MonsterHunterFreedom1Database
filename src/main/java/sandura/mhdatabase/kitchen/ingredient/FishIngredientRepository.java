package sandura.mhdatabase.kitchen.ingredient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FishIngredientRepository {

    private static final String DATABASE_FILE_PATH = "./src/main/resources/felyne-kitchen/ingredient/fish_ingredient.db";

    private List<String> dbFileLines;

    public FishIngredientRepository () throws IOException {
        Path dbFilePath = Paths.get(DATABASE_FILE_PATH);
        dbFileLines = Files.readAllLines(dbFilePath);
    }

    public List<String> getDbFileLines() {
        //TODO think if it is worth returning copy of list or an immutable version of this collection
        return dbFileLines;
    }
}
