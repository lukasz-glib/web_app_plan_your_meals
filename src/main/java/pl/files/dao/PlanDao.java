package pl.files.dao;


import pl.files.exception.NotFoundException;
import pl.files.model.Plan;
import pl.files.model.Recipe;
import pl.files.model.RecipePlan;
import pl.files.utils.DbUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlanDao {
    // ZAPYTANIA SQL
    private static final String CREATE_PLAN_QUERY = "INSERT INTO plan(name,description,created, admin_id) VALUES (?, ?, NOW(), ?);";
    private static final String DELETE_PLAN_QUERY = "DELETE FROM plan where id = ?;";
    private static final String FIND_ALL_PLANS_QUERY = "SELECT * FROM plan;";
    private static final String READ_PLAN_QUERY = "SELECT * from plan where id = ?;";
    private static final String UPDATE_PLAN_QUERY = "UPDATE	plan SET name = ? , description = ?, created = ? WHERE	id = ?;";
    private static final String FIND_USERS_RECENT_PLAN_QUERY = "SELECT * FROM plan WHERE admin_id = ? ORDER BY created DESC LIMIT 1";
    private static final String FIND_ALL_USERS_PLANS_NUMBER_QUERY = "SELECT COUNT(*) number FROM plan WHERE admin_id = ?";
    private static final String FIND_ALL_USERS_PLANS_BY_CREATED_QUERY = "SELECT * FROM plan WHERE admin_id = ? ORDER BY created ASC";
    private static final String FIND_RECIPE_PLAN = "SELECT day_name.name as day_name, meal_name,  recipe.name as recipe_name, recipe.id as recipe_id\n" +
            "FROM `recipe_plan`\n" +
            "JOIN day_name on day_name.id=day_name_id\n" +
            "JOIN recipe on recipe.id=recipe_id WHERE\n" +
            "recipe_plan.plan_id =  (SELECT MAX(id) from plan WHERE admin_id = ?) -- zamiast 1 należy wstawić id użytkownika (tabela admins) --\n" +
            "ORDER by day_name.display_order, recipe_plan.display_order;";


    /**
     * Get plan by id
     *
     * @param planId
     * @return
     */
    public Plan read(Integer planId) {
        Plan plan = new Plan();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.READ_PLAN_QUERY, false)
        ) {
            preparedStatement.setInt(1, planId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    plan.setId(resultSet.getInt("id"));
                    plan.setName(resultSet.getString("name"));
                    plan.setDescription(resultSet.getString("description"));
                    plan.setCreated(resultSet.getString("created"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return plan;

    }

    /**
     * Return all plans
     *
     * @return
     */
    public List<Plan> findAll() {
        List<Plan> planList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.FIND_ALL_PLANS_QUERY, false);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;

    }

    /**
     * Create plan
     *
     * @param plan
     * @return
     */
    public Plan create(Plan plan) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.CREATE_PLAN_QUERY, true)) {
            preparedStatement.setString(1, plan.getName());
            preparedStatement.setString(2, plan.getDescription());
            preparedStatement.setInt(3, plan.getAdminId());
            int result = preparedStatement.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.first()) {
                    plan.setId(generatedKeys.getInt(1));
                    return plan;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Remove plan by id
     *
     * @param planId
     */
    public void delete(Integer planId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.DELETE_PLAN_QUERY, true)) {
            preparedStatement.setInt(1, planId);

            int deleted = preparedStatement.executeUpdate();
            if (deleted != 1) {
                throw new NotFoundException("Product not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Update plan
     *
     * @param plan
     */
    public void update(Plan plan) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.UPDATE_PLAN_QUERY, true)) {
            preparedStatement.setInt(4, plan.getId());
            preparedStatement.setString(1, plan.getName());
            preparedStatement.setString(2, plan.getDescription());
            preparedStatement.setString(3, plan.getCreated());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int numberOfPlansAdded(int adminId) {
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.FIND_ALL_USERS_PLANS_NUMBER_QUERY, false)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("number");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Map finRecentUserPlan(Integer adminId) {
        Plan plan = new Plan();
        try (PreparedStatement findPlan = DbUtil.prepareStatement(PlanDao.FIND_USERS_RECENT_PLAN_QUERY, false);
             PreparedStatement findRecipePlan = DbUtil.prepareStatement(PlanDao.FIND_RECIPE_PLAN, false);
        ) {
            findPlan.setInt(1, adminId);
            ResultSet recentPlan = findPlan.executeQuery();
            while (recentPlan.next()) {
                plan.setId(recentPlan.getInt("id"));
                plan.setName(recentPlan.getString("name"));
                plan.setDescription(recentPlan.getString("description"));
                plan.setCreated(recentPlan.getString("created"));
            }
            findRecipePlan.setInt(1, adminId);
            ResultSet recipePlanSet = findRecipePlan.executeQuery();
            String name_day = "day";
            List<RecipePlan> recipePlans = new ArrayList<>();

            while (recipePlanSet.next()) {

                RecipeDao recipeDao = new RecipeDao();
                List<Recipe> recipes = recipeDao.findAll();
                Recipe recipe = new Recipe();
                for (Recipe recipeTemp : recipes) {
                    if (recipePlanSet.getString("recipe_name").equalsIgnoreCase(recipeTemp.getName())) {
                        recipe = recipeTemp;
                        break;
                    }
                }

                RecipePlan recipePlan;

                if (name_day.equalsIgnoreCase(recipePlanSet.getString("day_name"))) {
                    recipePlan = recipePlans.get(recipePlans.size() - 1);
                    recipePlan.addToMealName(recipePlanSet.getString("meal_name"), recipe);
                    recipePlans.set((recipePlans.size() - 1), recipePlan);
                } else {
                    name_day = recipePlanSet.getString("day_name");
                    recipePlan = new RecipePlan(
                            recipePlanSet.getString("day_name"), recipePlanSet.getString("meal_name"), recipe
                    );
                    recipePlans.add(recipePlan);
                }
            }

            return new HashMap<String, ArrayList<RecipePlan>>() {{
                put(plan.getName(), (ArrayList<RecipePlan>) recipePlans);
            }};


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Plan> findAllByUsersPlans(int adminId) {
        List<Plan> planList = new ArrayList<>();
        try (PreparedStatement preparedStatement = DbUtil.prepareStatement(PlanDao.FIND_ALL_USERS_PLANS_BY_CREATED_QUERY, true)) {
            preparedStatement.setInt(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Plan planToAdd = new Plan();
                planToAdd.setId(resultSet.getInt("id"));
                planToAdd.setName(resultSet.getString("name"));
                planToAdd.setDescription(resultSet.getString("description"));
                planToAdd.setCreated(resultSet.getString("created"));
                planList.add(planToAdd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return planList;
    }

}





































