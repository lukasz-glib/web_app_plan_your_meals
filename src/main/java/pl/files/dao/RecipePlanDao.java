package pl.files.dao;

import pl.files.exception.NotFoundException;
import pl.files.model.Plan;
import pl.files.model.Recipe;
import pl.files.model.RecipePlan;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipePlanDao {
    private static final String CREATE_RECIPEPLAN_NAME_QUERY = "INSERT INTO recipe_plan (recipe_id, meal_name, display_order, day_name_id, plan_id) VALUES (?, ?, ?, ?, ?);";
    private static final String DELETE_RECIPEPLAN_NAME_QUERY = "DELETE FROM recipe_plan where id = ?;";
    private static final String FIND_ALL_RECIPEPLAN_NAME_QUERY = "SELECT * FROM recipePlan_name;";
    private static final String READ_RECIPEPLAN_NAME_QUERY = "SELECT * from recipePlan_name where id = ?;";
    private static final String UPDATE_RECIPEPLAN_NAME_QUERY = "UPDATE recipe_id = ?, meal_name = ?, display_order = ?, day_name_id = ?, plan_id = ? WHERE id = ?;";
    private static final String GET_ALL_RECIPE_PLANS_FOR_PLAN_SIMPLE = "SELECT * FROM recipe_plan WHERE plan_id = ?;";
    private static final String GET_ALL_RECIPE_PLANS_FOR_PLAN = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.id as recipe_id \n" +
            "            FROM `recipe_plan` \n" +
            "            JOIN day_name on day_name.id=day_name_id \n" +
            "            JOIN recipe on recipe.id=recipe_id WHERE \n" +
            "            recipe_plan.plan_id =  ? \n" +
            "            ORDER by day_name.display_order, recipe_plan.display_order;";

    public ArrayList<RecipePlan> findAllRecipePlansForPlan(int planId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.GET_ALL_RECIPE_PLANS_FOR_PLAN_SIMPLE, false)) {
            preparedStatement.setInt(1, planId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RecipePlan> recipePlans = new ArrayList<>();
            while (resultSet.next()) {
                RecipePlan recipePlan = new RecipePlan();
                recipePlan.setId(resultSet.getInt("id"));
                recipePlan.setRecipeId(resultSet.getInt("recipe_id"));
                recipePlan.setMealName(resultSet.getString("meal_name"));
                recipePlan.setDisplayOrder(resultSet.getInt("display_order"));
                recipePlan.setDayNameId(resultSet.getInt("day_name_id"));
                recipePlan.setPlanId(resultSet.getInt("plan_id"));
                recipePlans.add(recipePlan);
            }
            return (ArrayList<RecipePlan>) recipePlans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RecipePlan> findAll() {
        List<RecipePlan> recipePlans = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.FIND_ALL_RECIPEPLAN_NAME_QUERY, true);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                RecipePlan recipePlan = new RecipePlan(
                        resultSet.getInt("id"), resultSet.getInt("recipe_id"), resultSet.getString("meal_name"),
                        resultSet.getInt("display_order"), resultSet.getInt("day_name_id"), resultSet.getInt("plan_id")
                );
                recipePlans.add(recipePlan);
            }
            return recipePlans;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public RecipePlan read(Integer recipePlanId) {
        RecipePlan recipePlanName = new RecipePlan();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.READ_RECIPEPLAN_NAME_QUERY, true)
        ) {
            preparedStatement.setInt(1, recipePlanId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                RecipePlan recipePlan = new RecipePlan(
                        resultSet.getInt("id"), resultSet.getInt("recipe_id"), resultSet.getString("meal_name"),
                        resultSet.getInt("display_order"), resultSet.getInt("day_name_id"), resultSet.getInt("plan_id")
                );

                return recipePlan;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipePlanName;
    }

    public RecipePlan create(RecipePlan recipePlan) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.CREATE_RECIPEPLAN_NAME_QUERY, true)) {
            preparedStatement.setInt(1, recipePlan.getRecipeId());
            preparedStatement.setString(2, recipePlan.getMealName());
            preparedStatement.setInt(3, recipePlan.getDisplayOrder());
            preparedStatement.setInt(4, recipePlan.getDayNameId());
            preparedStatement.setInt(5, recipePlan.getPlanId());

            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    recipePlan.setId(generatedKeys.getInt(1));
                    return recipePlan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Integer recipePlanNameID) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.DELETE_RECIPEPLAN_NAME_QUERY, true)) {
            preparedStatement.setInt(1, recipePlanNameID);
            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(RecipePlan recipePlan) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.UPDATE_RECIPEPLAN_NAME_QUERY, true)) {
            preparedStatement.setInt(1, recipePlan.getRecipeId());
            preparedStatement.setString(2, recipePlan.getMealName());
            preparedStatement.setInt(3, recipePlan.getDisplayOrder());
            preparedStatement.setInt(4, recipePlan.getDayNameId());
            preparedStatement.setInt(5, recipePlan.getPlanId());
            preparedStatement.setInt(6, recipePlan.getId());

            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new NotFoundException("Product not found");
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RecipePlan> findRecipePlansForDisplay(Integer planId) {
        Plan plan = new Plan();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(RecipePlanDao.GET_ALL_RECIPE_PLANS_FOR_PLAN, false)) {

            preparedStatement.setInt(1, planId);
            ResultSet resultSet = preparedStatement.executeQuery();
            String name_day = "day";
            List<RecipePlan> recipePlans = new ArrayList<>();

            while (resultSet.next()) {

                RecipeDao recipeDao = new RecipeDao();
                List<Recipe> recipes = recipeDao.findAll();
                Recipe recipe = new Recipe();
                for (Recipe recipeTemp : recipes) {
                    if (resultSet.getString("recipe_name").equalsIgnoreCase(recipeTemp.getName())) {
                        recipe = recipeTemp;
                        break;
                    }
                }

                RecipePlan recipePlan;

                if (name_day.equalsIgnoreCase(resultSet.getString("day_name"))) {
                    recipePlan = recipePlans.get(recipePlans.size() - 1);
                    recipePlan.addToMealName(resultSet.getString("meal_name"), recipe);
                    recipePlans.set((recipePlans.size() - 1), recipePlan);
                } else {
                    name_day = resultSet.getString("day_name");
                    recipePlan = new RecipePlan(
                            resultSet.getString("day_name"), resultSet.getString("meal_name"), recipe
                    );
                    recipePlans.add(recipePlan);
                }
            }

            return (ArrayList<RecipePlan>) recipePlans;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

