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

public class OilIngredientRepository {


    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Map<String, List<String>> oilMap;

    public OilIngredientRepository() {
        oilMap = new HashMap<>();
        oilMap.put("1", new ArrayList<>());
        oilMap.put("2", new ArrayList<>());
        oilMap.put("3", new ArrayList<>());
        oilMap.put("4", new ArrayList<>());
        oilMap.put("5", new ArrayList<>());
    }

    public Map<String, List<String>> getOilMap() {
        return oilMap;
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
                oilMap.get(cookCount).add(ingredient);
            });

        } catch (Exception e) {
            logger.log(SEVERE, "Failed loading Oil data from path " + pathString);
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(INFO, """
                Finished loading data into Oil repository \
                loaded:""" + oilMap.keySet().size() + " rows.");
    }

}
