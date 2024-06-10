package sandura.mhdatabase.kitchen;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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


    class IngridientsPair {
        String firstIndredient;
        String secondIngredient;

        IngridientsPair(String first, String second) {
            firstIndredient = first;
            secondIngredient = second;
        }

        public String toString() {
            return "Ingredient Pair: " + firstIndredient + ", " + secondIngredient;
        }
    }

    private static Map<String, String> recipeMap;

    private static Map<Integer, List<String>> recipeByCookCountMap;

    public Map<Integer, List<String>> getRecipeByCookCountMap() {
        return recipeByCookCountMap;
    }
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }

    // print a file
    private static void printFile(File file) {

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(s -> logger.log(Level.INFO, s));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
//        recipeMap = new HashMap<>();
        try {

            Path path = Paths.get(pathString);
            List<String> strings = Files.readAllLines(path);
            for (int i = 0; i < strings.size(); i++) {
                if (i == 0) {
//                    logger.log(Level.INFO,"Reading table with headers:");
//                    logger.log(Level.INFO,strings.get(i));
                } else {
                    String row = strings.get(i);
//                    logger.log(Level.INFO,row);
                    String[] split = row.split(",");
                    String[] ingredients = split[4].split("\\+");
                    String requiredIngredients = ingredients[0].toLowerCase() + "+" + ingredients[1].toLowerCase();
                    recipeMap.put(requiredIngredients, split[3]);
                    String cookCount = split[1];
                    recipeByCookCountMap.get(Integer.parseInt(cookCount)).add(requiredIngredients);
                }

            }
        } catch (Exception e) {
//            logger.log(Level.INFO, ioe, () -> ioe.getMessage());
//            logger.log(Level.INFO,"Current path is " + Paths.get(".").toAbsolutePath());
            Path absolutePath = Paths.get("kitchen_recipe.db").toAbsolutePath();
//            logger.log(Level.INFO,"Is this path a directory? " + absolutePath+"path is " + new File(String.valueOf(absolutePath)).isDirectory() );
//            logger.log(Level.INFO,"Current path is " + Paths.get(".").getR());
            logger.log(Level.SEVERE, e.toString());

        }
        logger.log(Level.INFO, """
                Finished loading data into RecipeRepository \
                loaded:""" + recipeMap.keySet().size() + " rows.");
    }

    public Map<String, String> getRecipes() {
        return recipeMap;
    }

    public String generatePossibleRecipes(String first, String second, int chefsCount) {
        logger.log(Level.INFO, "Possible combinations:");
        String s = recipeMap.get(first + "+" + second);
        if (s == null) {
            s = recipeMap.get(second + "+" + first);
        }
        return s;
    }
}
