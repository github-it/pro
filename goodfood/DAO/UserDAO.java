public class UserDAO implements GenericDAO<User> {
    
    private ConnectionPool pool = ConnectionPool.getInstance();
	
	private String ADD_USER = "INSERT INTO USER(LOGINEMAIL, PASSWORD, BALANCE, STATUS) VALUES( ?, ?, ?, ?)";
	private String UPDATE_USER = "UPDATE USER SET LOGINEMAIL=?, PASSWORD=?, BALANCE=?, STATUS=? WHERE ID=?";
	private String GET_USER = "SELECT ID, LOGINEMAIL, PASSWORD, BALANCE, STATUS FROM USER";
	private String DELETE_USER = "DELETE FROM USER WHERE ID=?";

    @Override
    public void add(User user) throws DaoException {
        addUpdate(user, ADD_USER);
    }

    @Override
    public void update(User user) throws DaoException {
        addUpdate(user, UPDATE_USER);
    }
	
	private void addUpdate(User user, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		preparedStatement.setString(1, user.getLoginEmail());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.setInt(3, user.getBalance());
		preparedStatement.setString(4, String.valueOf(user.getStatus()));
            if (user.getId() != null) preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
			pool.returnConnection(connection);
            }
	
    @Override
    public User getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }

    public User getByLogin(String loginEmail) throws DaoException {
        return getByParameter(loginEmail, "LOGINEMAIL");
    }
    
    private User getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
        User user = null;

        PreparedStatement preparedStatement = connection.prepareStatement(GET_USER + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
		ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUserDbFields(resultSet);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
 
        return user;
    }

	@Override
    public List<User> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        User user;
        List<User> userList = new ArrayList<>();
        
		PreparedStatement preparedStatement = connection.prepareStatement(GET_USER);
		ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getUserDbFields(resultSet);
                userList.add(user);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
      
        return userList;
    }

    private User getUserDbFields(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("ID"));
        user.setLoginEmail(resultSet.getString("LOGINEMAIL"));
        user.setPassword(resultSet.getString("PASSWORD"));
		user.setBalance(resultSet.getInt("BALANCE"));
        user.setStatus(User.Status.valueOf(resultSet.getString("STATUS")));
		
        return user;
    }

    @Override
    public void remove(User user) throws DaoException {
        Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER);
		preparedStatement.setInt(1, user.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
    }
}

