package pl.files.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class RecipePlan {


    private String dayName;
    private Map<String, ArrayList<Recipe>> mealNameMap;
    private int id;
    private int recipeId;
    private String mealName;
    private int displayOrder;
    private int dayNameId;
    private int planId;

    public RecipePlan(int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
        this.recipeId = recipeId;
        this.mealName = mealName;
        this.displayOrder = displayOrder;
        this.dayNameId = dayNameId;
        this.planId = planId;
    }

    public RecipePlan(int id, int recipeId, String mealName, int displayOrder, int dayNameId, int planId) {
        this.id = id;
        this.recipeId = recipeId;
        this.mealName = mealName;
        this.displayOrder = displayOrder;
        this.dayNameId = dayNameId;
        this.planId = planId;
    }

    public RecipePlan(String dayName, Map<String, ArrayList<Recipe>> mealNameMap) {
        this.dayName = dayName;
        this.mealNameMap = mealNameMap;

    }

    public RecipePlan(String dayName, String mealNameMap, Recipe recipe){
        this.dayName = dayName;
        this.mealNameMap = new HashMap<String, ArrayList<Recipe>>(){{put(mealNameMap, new ArrayList<Recipe>(){{add(recipe);}});}};

    }

    public RecipePlan() {
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public Map<String, ArrayList<Recipe>> getMealNameMap() {
        return mealNameMap;
    }

    public void setMealNameMap(Map<String, ArrayList<Recipe>> mealNameMap) {
        this.mealNameMap = mealNameMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getDayNameId() {
        return dayNameId;
    }

    public void setDayNameId(int dayNameId) {
        this.dayNameId = dayNameId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public void addToMealName(String mealName, Recipe recipe){
        if(this.mealNameMap.containsKey(mealName)){
            this.mealNameMap.get(mealName).add(recipe);
        } else {
            this.mealNameMap.put(mealName, new ArrayList<Recipe>(){{add(recipe);}});
        }
    }


}
