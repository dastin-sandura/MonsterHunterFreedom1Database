package sandura.mhdatabase.kitchen;

import sandura.mhdatabase.servlet.DatabaseServlet;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeRepository {

    private static final Logger logger =Logger.getLogger(RecipeRepository.class.getName());


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

    Map<String, String> recipeMap;

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
            lines.forEach(s -> logger.log(Level.INFO,s));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public RecipeRepository() {
        recipeMap = new HashMap<>();
//        recipeMap = new HashMap<>();
        //        System.out.println("RecipeRepository constructor");
//                loadDataFromBaseDir("\"kitchen_recipe.db\"");
    }

    public void loadDataFromBaseDir(String pathString) {
        recipeMap = new HashMap<>();
        try {
            Path path = Paths.get(pathString);
//            ClassLoader.getSystemResourceAsStream("kitchen_recipe.db");
//            File fileFromResource = getFileFromResource("kitchen_recipe.db");
//            printFile(fileFromResource);

            Path currentPath = Paths.get(".");
//            logger.log(Level.INFO,"absolute" + currentPath.toAbsolutePath().toString());
//            logger.log(Level.INFO,"real path" + currentPath.toRealPath().toString());
            List<String> strings = Files.readAllLines(path);
            for (int i = 0; i < strings.size(); i++) {
                if (i == 0) {
                    logger.log(Level.INFO,"Reading table with headers:");
                    logger.log(Level.INFO,strings.get(i));
                } else {
                    String row = strings.get(i);
                    logger.log(Level.INFO,row);
                    String[] split = row.split(",");
                    String[] ingredients = split[4].split("\\+");
                    recipeMap.put(ingredients[0] + "+" + ingredients[1], split[3]);
                }

            }
        } catch (IOException ioe) {
            logger.log(Level.INFO, ioe, () -> ioe.getMessage());
            logger.log(Level.INFO,"Current path is " + Paths.get(".").toAbsolutePath());
            Path absolutePath = Paths.get("kitchen_recipe.db").toAbsolutePath();
            logger.log(Level.INFO,"Is this path a directory? " + absolutePath+"path is " + new File(String.valueOf(absolutePath)).isDirectory() );
//            logger.log(Level.INFO,"Current path is " + Paths.get(".").getR());
        }
        logger.log(Level.INFO,"""
                Finished loading data into RecipeRepository \
                loaded:""" + recipeMap.keySet().size() + " rows.");
    }

    public Map<String, String> getRecipes() {
        return recipeMap;
    }

    public String generatePossibleRecipes(String first, String second, int chefsCount) {
        logger.log(Level.INFO,"Possible combinations:");
        return recipeMap.get(first + "+" + second);
    }
}
