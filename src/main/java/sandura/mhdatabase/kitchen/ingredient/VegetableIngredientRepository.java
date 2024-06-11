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

import static java.util.logging.Level.INFO;

public class VegetableIngredientRepository {


    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, List<String>> vegetableMap;

    public VegetableIngredientRepository() {
       vegetableMap = new HashMap<>();
       vegetableMap.put("1", new ArrayList<>());
       vegetableMap.put("2", new ArrayList<>());
       vegetableMap.put("3", new ArrayList<>());
       vegetableMap.put("4", new ArrayList<>());
       vegetableMap.put("5", new ArrayList<>());
    }

    public Map<String, List<String>> getVegetableMap() {
        return vegetableMap;
    }

    public void loadDataFromBaseDir(String pathString) {
        try {
            Path path = Paths.get(pathString);
            List<String> strings = Files.readAllLines(path).stream().skip(1).toList();
            strings.forEach(veg -> {
                logger.log(Level.FINE, "Processing vegetable row " + veg);
                String[] cookCountAndIngredient  = veg.split(",");
                String cookCount = cookCountAndIngredient[0];
                String ingredient = cookCountAndIngredient[1];
                vegetableMap.get(cookCount).add(ingredient);
            });

        } catch (Exception e) {
            logger.severe("Failed loading veggetable data from path " + pathString);
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(INFO, """
                Finished loading data into Vegetable repository \
                loaded:""" + vegetableMap.keySet().size() + " rows.");
    }

}
