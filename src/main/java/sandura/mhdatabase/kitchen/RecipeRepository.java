package sandura.mhdatabase.kitchen;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeRepository {

    private static final Logger logger = Logger.getLogger(RecipeRepository.class.getName());

    private static Map<String, String> recipeMap;

    private static Map<Integer, List<String>> recipeByCookCountMap;

    public Map<Integer, List<String>> getRecipeByCookCountMap() {
        return recipeByCookCountMap;
    }

    public RecipeRepository() {
        recipeMap = new HashMap<>();
        recipeByCookCountMap = new HashMap<>();
        recipeByCookCountMap.put(1, new ArrayList<>());
        recipeByCookCountMap.put(2, new ArrayList<>());
        recipeByCookCountMap.put(3, new ArrayList<>());
        recipeByCookCountMap.put(4, new ArrayList<>());
        recipeByCookCountMap.put(5, new ArrayList<>());
    }

    public void loadDataFromBaseDir(String pathString) {
        try {

            Path path = Paths.get(pathString);
            List<String> strings = Files.readAllLines(path);
            for (int i = 0; i < strings.size(); i++) {
                if (i != 0) {
                    String row = strings.get(i);
                    logger.log(Level.FINE,row);
                    String[] split = row.split(",");
                    String[] ingredients = split[4].split("\\+");
                    String requiredIngredients = ingredients[0].toLowerCase() + "+" + ingredients[1].toLowerCase();
                    String effect = split[3];
                    recipeMap.put(requiredIngredients, effect);
                    String cookCount = split[1];
                    recipeByCookCountMap.get(Integer.parseInt(cookCount)).add(requiredIngredients + "=" + effect);
                }

            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.toString());
        }
        logger.log(Level.INFO, """
                Finished loading data into RecipeRepository \
                loaded:""" + recipeMap.keySet().size() + " rows.");
    }

    public Map<String, String> getRecipes() {
        return recipeMap;
    }

    public String generatePossibleRecipes(String first, String second) {
        logger.log(Level.INFO, "Possible combinations:");
        String s = recipeMap.get(first + "+" + second);
        if (s == null) {
            s = recipeMap.get(second + "+" + first);
        }
        return s;
    }
}
