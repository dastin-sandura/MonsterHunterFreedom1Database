package sandura.mhdatabase.kitchen.ingredient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FishIngredientRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    public static final String DATABASE_FILE_PATH = "./src/main/resources/felyne-kitchen/ingredient/fish_ingredient.db";

    private List<String> dbFileLines;

    private Map<Integer, List<String>> fishMap;

    public FishIngredientRepository() {
        fishMap = new HashMap<>();
    }

    public void loadDataFromFile(String filePath) {
        try {
            Path dbFilePath = Paths.get(filePath);
            dbFileLines = Files.readAllLines(dbFilePath).stream().skip(1).toList();
            dbFileLines.forEach(fishRow -> {
                logger.fine("Processing Fish row " + fishRow);
                Integer key = Integer.parseInt(fishRow.split(",")[0]);
                String value = fishRow.split(",")[1];
                List<String> strings = fishMap.get(key);
                if (strings == null) {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(value);
                    fishMap.put(key, list);
                } else {
                    strings.add(value);
                }
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Loading fish data from dir " + filePath + " failed");
        }
    }

    public List<String> getDbFileLines() {
        //TODO think if it is worth returning copy of list or an immutable version of this collection
        return dbFileLines;
    }

    public Map<Integer, List<String>> getFishMap() {
        return fishMap;
    }
}
