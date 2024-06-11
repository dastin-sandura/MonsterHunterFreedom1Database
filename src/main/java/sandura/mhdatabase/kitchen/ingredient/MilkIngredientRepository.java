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

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.INFO;

public class MilkIngredientRepository {


    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final Map<String, List<String>> milkMap;

    public MilkIngredientRepository() {
        milkMap = new HashMap<>();
        milkMap.put("1",new ArrayList<>());
        milkMap.put("2",new ArrayList<>());
        milkMap.put("3",new ArrayList<>());
        milkMap.put("4",new ArrayList<>());
        milkMap.put("5",new ArrayList<>());
    }

    public Map<String, List<String>> getMilkMap() {
        return milkMap;
    }

    public void loadDataFromBaseDir(String pathString) {
        try {
            logger.log(INFO, "Loading data from " + pathString + " to get all values for Milk");
            Path path = Paths.get(pathString);
            List<String> strings = Files.readAllLines(path).stream().skip(1).toList();
            strings.forEach(veg -> {
                logger.log(FINE, "Processing row " + veg);
                String[] cookCountAndIngredient  = veg.split(",");
                String cookCount = cookCountAndIngredient[0];
                String ingredient = cookCountAndIngredient[1];
                milkMap.get(cookCount).add(ingredient);
            });

        } catch (Exception e) {
            logger.severe("Loading Milk ingredients failed - loading from path " + pathString);
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(INFO, """
                Finished loading data into Milk repository \
                loaded:""" + milkMap.keySet().size() + " rows.");
    }
}
