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

public class MeatIngredientRepository {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    Map<String, List<String>> meatMap;
    public MeatIngredientRepository() {
        meatMap = new HashMap<>();
        meatMap.put("1", new ArrayList<>());
        meatMap.put("2", new ArrayList<>());
        meatMap.put("3", new ArrayList<>());
        meatMap.put("4", new ArrayList<>());
        meatMap.put("5", new ArrayList<>());
    }

    public Map<String, List<String>> getMeatMap() {
        return meatMap;
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
                meatMap.get(cookCount).add(ingredient);
            });

        } catch (Exception e) {
            logger.log(SEVERE, "Failed loading Meat data");
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(INFO, """
                Finished loading data into Meat repository \
                loaded:""" + meatMap.keySet().size() + " rows.");
    }


}
