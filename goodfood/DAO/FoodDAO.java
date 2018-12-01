public class FoodDAO implements GenericDAO<Food> {
	
    private ConnectionPool pool = ConnectionPool.getInstance();

	private String ADD_FOOD = "INSERT INTO FOOD(NAME, DESCRIPTION) VALUES(?, ?)";
    private String UPDATE_FOOD = "UPDATE FOOD SET NAME=?, DESCRIPTION=? WHERE ID=?";
	private String GET_FOOD = "SELECT ID, NAME, DESCRIPTION FROM FOOD";
	private String DELETE_FOOD = "DELETE FROM FOOD WHERE ID=?";
	
	
    @Override
    public void add(Food food) throws DaoException {
        addUpdate(food, ADD_FOOD);
    }

    @Override
    public void update(Food food) throws DaoException {
        addUpdate(food, UPDATE_FOOD);
    }

    private void addUpdate(Food food, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		preparedStatement.setString(1, food.getName());
		preparedStatement.setString(2, food.getDescription());
            if (food.getId() != null) preparedStatement.setInt(3, food.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
			pool.returnConnection(connection);
            }
	
	 @Override
    public Food getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }
	
	private Food getByParameter(Object parameter, String sqlParameter) throws DaoException {
		Connection connection = pool.getConnection();
        Food food = null;
            
			PreparedStatement preparedStatement = connection.prepareStatement(GET_FOOD + " WHERE " + sqlParameter + "=?");
            preparedStatement.setObject(1, parameter);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                food = getFoodDbFields(resultSet);
            }
            resultSet.close();
            preparedStatement.close(); 
            pool.returnConnection(connection);
     
        return food;
    }
	
	 private Food getFoodDbFields(ResultSet resultSet) throws SQLException {
        Food food = new Food();
        
		food.setId(resultSet.getInt("ID"));
        food.setName(resultSet.getString("NAME"));
        food.setDescription(resultSet.getString("DESCRIPTION"));
		
        return food;
    }
	
    @Override
    public List<Food> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Food> foodList = new ArrayList<>();

            PreparedStatement preparedStatement = connection.prepareStatement(GET_FOOD);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Food food = getFoodDbFields(resultSet);
                foodList.add(food);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
 
        return foodList;
    }

    @Override
    public void remove(Food food) throws DaoException {
        Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FOOD);
		preparedStatement.setInt(1, food.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
    }

   
}
