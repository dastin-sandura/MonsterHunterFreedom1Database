package sandura.mhdatabase.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sandura.mhdatabase.item.ItemRepository;
import sandura.mhdatabase.kitchen.FelyneRecipesService;
import sandura.mhdatabase.kitchen.RecipeRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseServlet extends HttpServlet {

    public static final RecipeRepository recipeRepository = new RecipeRepository();
    PrintWriter writer;
    private Logger logger = Logger.getLogger(DatabaseServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

//        Logger.getLogger (DatabaseServlet.class.getName()).log(Level.WARNING, e.getMessage(), e);
        logger.log(Level.INFO, "hello world");
        req.getQueryString();
        String first = req.getParameter("first");
        String second = req.getParameter("second");

        String[] ingredients = req.getParameterValues("ingredient");
        if (ingredients!= null && ingredients.length == 2) {
            first = ingredients[0];
            second = ingredients[1];

        }

        //        super.doGet(req, resp);
        writer = resp.getWriter();

        printAsIs("<!doctype html>");
        printAsIs("<html lang=\"en-US\">");
        printAsIs("<head>");
        printAsIs("<title>MHFreedom Dish generator</title>");

        printAsIs("</head>");
        printAsIs("<body>");

//        print("Value of environment variable 'catalina.base' is :" + System.getProperty("catalina.base"));
        String webAppWebInfDirectory = getServletContext().getRealPath("/").toString() + "WEB-INF/";

        String kitchenRecipeDbFilePath = webAppWebInfDirectory + "kitchen_recipe.db";
//        print("getServletContext().getRealPath(\"/\") : " + kitchenRecipeDbFilePath);
//        print("Attributes:");
//        getServletContext().getAttributeNames().asIterator().forEachRemaining(s -> print(s + " = " + getServletContext().getAttribute(s)));
//        print("First parameter %s".formatted(first));
//        print("Second parameter %s".formatted(second));

        FelyneRecipesService felyneRecipesService = new FelyneRecipesService();

        if (recipeRepository.getRecipes().keySet().isEmpty()){
            logger.log(Level.INFO, "Loading data because recipe repo is empty");
            recipeRepository.loadDataFromBaseDir(kitchenRecipeDbFilePath);
        }
        if (first != null && second != null) {
            logger.log(Level.INFO, "Checking possibilites for " + first + " and " + second);
            List<String> requestParameters = new ArrayList<>();
            requestParameters.add(first);
            requestParameters.add(second);
//            print("Possible ingredient from " + requestParameters);
            print( "Possible ingredient from " +felyneRecipesService.getPossibleIngredientPairs(requestParameters).toString());

            try {
                print("Effect from ingredients: " + recipeRepository.generatePossibleRecipes(first, second, 0));
            } catch (Exception e) {
                print(e.toString());
            }

        }
        ItemRepository var1 = new ItemRepository();
        var1.loadDataFromPath(webAppWebInfDirectory + "item.db");
//        print(recipeRepository.generatePossibleRecipes("Cubesteak", "Hardtack", 2));
//        print("Contents of Items Repository");
        //print(var1.getItems().toString());
        ArrayList var4 = new ArrayList();
        var4.add("Chili Cheese");
        var4.add("Wild Wonton");
//        print("Possible ingredient from " + var4);
//        print(felyneRecipesService.getPossibleIngredientPairs(var4).toString());
        felyneRecipesService.getNumberOfCooksFromIngredient("Chili Cheese");
        printAsIs("""
                       <form action="" method"get">
                                   <div>
                                       Cubesteak
                                       <input type="checkbox" name="ingredient" value="Cubesteak" id="second"/>
                                   </div>
                                  <div>
                                       Dry Margarine
                                       <input type="checkbox" name="ingredient" value="Dry Margarine" id="second"/>
                                   </div>
                                 
                                   <div>
                                       Tuna head
                                       <input type="checkbox" name="ingredient" value="Tuna head" id="second"/>
                                   </div>
                                   <div>
                                       Hardtack
                                       <input type="checkbox" name="ingredient" value="Hardtack" id="second"/>
                                   </div>
                                  
                                   <div>
                                       Snake salmon
                                       <input type="checkbox" name="ingredient" value="Snake salmon" id="second"/>
                                   </div>
                                   <div>
                                       Sweetbug
                                       <input type="checkbox" name="ingredient" value="Sweetbug" id="second"/>
                                   </div>
                                  
                                   <div>
                                       Spicy Sausage
                                       <input type="checkbox" name="ingredient" value="Spicy Sausage" id="second"/>
                                   </div>
                                   
                                   <div>
                                       Aged Cheese
                                       <input type="checkbox" name="ingredient" value="Aged Cheese" id="second"/>
                                   </div>
                                   <div>
                                       <input type="submit" value="Generate" />
                                   </div>
                               </form>
                """);

        print("Combinations loaded from file = " + recipeRepository.getRecipes());
        printAsIs("</body>");
        printAsIs("</html>");

        writer = null;
    }

    private void printAsIs(String s) {
        writer.println(s);
    }

    private void print(String s) {
        writer.println("<p>" + s + "</p>");
    }

}
