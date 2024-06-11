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

import static java.util.logging.Level.*;

public class GrainIngredientRepository {


    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final Map<String, List<String>> grainMap;

    public GrainIngredientRepository() {
        grainMap = new HashMap<>();
        grainMap.put("1", new ArrayList<>());
        grainMap.put("2", new ArrayList<>());
        grainMap.put("3", new ArrayList<>());
        grainMap.put("4", new ArrayList<>());
        grainMap.put("5", new ArrayList<>());
    }

    public Map<String, List<String>> getGrainMap() {
        return grainMap;
    }

    public void loadDataFromBaseDir(String pathString) {
        try {
            Path path = Paths.get(pathString);
            List<String> strings = Files.readAllLines(path).stream().skip(1).toList();
            strings.forEach(meat -> {
                logger.log(FINE, "Processing row " + meat);
                String[] cookCountAndIngredient  = meat.split(",");
                String cookCount = cookCountAndIngredient[0];
                String ingredient = cookCountAndIngredient[1];
                grainMap.get(cookCount).add(ingredient);
            });

        } catch (Exception e) {
            logger.log(SEVERE, "Failed loading Grain data");
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(INFO, """
                Finished loading data into Grain repository \
                loaded:""" + grainMap.keySet().size() + " rows.");
    }
}
