package sandura.mhdatabase.kitchen;

public class IngredientPair {

    public String first, second;

    public IngredientPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        int i = first.compareTo(second);
        if (i >= 0) {
            return (first+second).hashCode();
        } else {
            return  (second+first).hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IngredientPair secondObject) {
            boolean firstCondition = (first + second).equals(secondObject.first + secondObject.second);
            boolean secondCondition = (second + first).equals(secondObject.first + secondObject.second);
            return firstCondition || secondCondition;
        }
        return false;
    }

    @Override
    public String toString() {
        return first+second;
    }
}
