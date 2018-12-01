 public class ContentDAO implements GenericDao<Content> {
	
	private ConnectionPool pool = ConnectionPool.getInstance();
	
	private String ADD_CONTENT = "INSERT INTO CONTENT (FOOD_ID, PROPERTY_ID, PRICE, QUANTITY_ALL) VALUES(?, ?, ?, ?)";
	private String GET_CONTENT = "SELECT ID, FOOD_ID, PROPERTY_ID, PRICE, QUANTITY_ALL FROM CONTENT";
	private String DELETE_CONTENT = "DELETE FROM CONTENT WHERE ID=?";
	
	@Override
    public void add(Content content) throws DaoException {
        addRemove(content, ADD_CONTENT);
    }
	@Override
    public void update(Content content) throws DaoException {
        throw new DaoException("ContentDAO update() not allowed");
		
	private void addRemove(Content content, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
            if (content.getId() == null) {       
				preparedStatement.setInt(1, content.getFood().getId());
                preparedStatement.setInt(2, content.getProperty().getId());
                preparedStatement.setInt(3, content.getPrice());
				preparedStatement.setInt(4, content.getQuantityAll());
            } else preparedStatement.setInt(1, content.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
			pool.returnConnection(connection);   
    }	
	
    @Override
    public Content getById(int id) throws DaoException { 
        return getByParameter(id, "ID");	
	}
	
	@Override
    public Content getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        Content content = null;
		
		PreparedStatement preparedStatement = connection.prepareStatement(GET_CONTENT + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
		ResultSet resultSet = preparedStatement.executeQuery();	
		FoodDAO foodDao = new FoodDAO();
		PropertyDAO propertyDao = new PropertyDAO();
            if (resultSet.next()) {
                content = getContentDbFields(foodDao, propertyDao, resultSet);
            }
        resultSet.close();
		preparedStatement.close();
		pool.returnConnection(connection);
 
        return content;
    }		
	
	public List<Content> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Content> contentList = new ArrayList<>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(GET_CONTENT);
		ResultSet resultSet = preparedStatement.executeQuery();	
		FoodDAO foodDao = new FoodDAO();
		PropertyDAO propertyDao = new PropertyDAO();    
			while (resultSet.next()) {
                Content content = getContentDbFields(resultSet, foodDao, propertyDao);
                contentList.add(content);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
        
		return contentList;
 }
 
	private Content getContentDbFields(FoodDAO foodDAO, PropertyDAO propertyDao, ResultSet resultSet) throws SQLException, DaoException {
        Content content = new Content();
        content.setId(resultSet.getInt("ID"));
		
        int food_id = resultSet.getInt("FOOD_ID");
        Food food = foodDao.getById(food_id);
        content.setFood(food);
		
        int property_id = resultSet.getInt("PROPERTY_ID");
        Property property = propertyDao.getById(property_id);
        content.setProperty(property);
		
		content.setPrice(resultSet.getInt("PRICE"));
        content.setQuantityAll(resultSet.getInt("QUANTITY_ALL"));
        return content;
    }
	
	@Override
    public void remove(Content content) throws DaoException {
        addRemove(content, DELETE_CONTENT);