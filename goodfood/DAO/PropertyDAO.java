public class PropertyDAO implements GenericDAO<Property> {
	
    private ConnectionPool pool = ConnectionPool.getInstance();

	private String ADD_PROPERTY = "INSERT INTO PROPERTY(NAME) VALUES(?)";
    private String UPDATE_PROPERTY = "UPDATE PROPERTY SET NAME=? WHERE ID=?";
	private String GET_PROPERTY = "SELECT ID, NAME FROM PROPERTY";
	private String DELETE_PROPERTY = "DELETE FROM PROPERTY WHERE ID=?";

    @Override
    public void add(Property property) throws DaoException {
        addUpdate(property, ADD_PROPERTY);
    }

    @Override
    public void update(Property property) throws DaoException {
        addUpdate(property, UPDATE_PROPERTY);
    }

    @Override
    public Property getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }

    private void addUpdate(Property property, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            preparedStatement.setString(1, property.getName());
            if (property.getId() != null) preparedStatement.setInt(2, property.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            pool.returnConnection(connection);
            }
    
	private Property getByParameter(Object parameter, String sqlParameter) throws DaoException {
		Connection connection = pool.getConnection();
        Property property = null;
     
		PreparedStatement preparedStatement = connection.prepareStatement(GET_PROPERTY + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
		ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                property = getPropertyDbFields(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
            
        return property;
    }
	
	 private Property getPropertyDbFields(ResultSet resultSet) throws SQLException {
        Property property = new Property();
       
		property.setId(resultSet.getInt("ID"));
        property.setName(resultSet.getString("NAME"));
		
        return property;
    }
	
    @Override
    public List<Property> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Property> propertyList = new ArrayList<>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(GET_PROPERTY);
		ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Property property = getPropertyDbFields(resultSet);
                propertyList.add(property);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
 
        return propertyList;
    }

    @Override
    public void remove(Property property) throws DaoException {
         Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROPERTY);
		preparedStatement.setInt(1, property.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
    } 
}
