package sandura.mhdatabase.servlet;

import jakarta.servlet.ServletException;
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

public class DatabaseServlet extends HttpServlet {

    PrintWriter writer;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getQueryString();
        String first = req.getParameter("first");
        String second = req.getParameter("second");

        //        super.doGet(req, resp);
        writer = resp.getWriter();

        printAsIs("<!doctype html>");
        printAsIs("<html lang=\"en-US\">");
        printAsIs("<head>");
        printAsIs("<title>MHFreedom Dish generator</title>");

        printAsIs("</head>");
        printAsIs("<body>");

//        print("Value of environment variable 'catalina.base' is :" + System.getProperty("catalina.base"));
        print("getServletContext().getRealPath(\"/\") : " + getServletContext().getRealPath("/").toString());
//        print("Attributes:");
//        getServletContext().getAttributeNames().asIterator().forEachRemaining(s -> print(s + " = " + getServletContext().getAttribute(s)));
        print("First parameter %s".formatted(first));
        print("Second parameter %s".formatted(second));

        FelyneRecipesService felyneRecipesService = new FelyneRecipesService();

        RecipeRepository recipeRepository = new RecipeRepository();
        if (recipeRepository.getRecipes().keySet().isEmpty()){
//            recipeRepository.loadDataFromBaseDir(getServletContext().getRealPath("/"));
        }
        if (first != null && second != null) {
            List<String> requestParameters = new ArrayList<>();
            requestParameters.add(first);
            requestParameters.add(second);
            print("Possible ingredient from " + requestParameters);
            print(felyneRecipesService.getPossibleIngredientPairs(requestParameters).toString());

            print("Possible recipies: " + recipeRepository.generatePossibleRecipes(first, second, 0));

        }
        ItemRepository var1 = new ItemRepository();
        print(recipeRepository.generatePossibleRecipes("Cubesteak", "Hardtack", 2));
        print("Contents of Items Repository");
        //print(var1.getItems().toString());
        ArrayList var4 = new ArrayList();
        var4.add("Chili Cheese");
        var4.add("Wild Wonton");
        print("Possible ingredient from " + var4);
        print(felyneRecipesService.getPossibleIngredientPairs(var4).toString());
        felyneRecipesService.getNumberOfCooksFromIngredient("Chili Cheese");
        printAsIs("""
                       <form action="" method"get">
                                   <div>
                                       <label for="first">First ingredient</label>
                                       <input type="text" name="first" id="first"/>
                                   </div>
                                   <div>
                                       <label for="second">Second ingredient</label>
                                       <input type="text" name="second" id="second"/
                                   </div>
                                   <div>
                                       <input type="submit" value="Generate" />
                                   </div>
                               </form>
                """);

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
