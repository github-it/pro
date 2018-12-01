public class ClientOrderDAO implements GenericDAO<ClientOrder> {

    private ConnectionPool pool = ConnectionPool.getInstance();

	private String ADD_CLIENT_CLIENT_ORDER = "INSERT INTO CLIENT_ORDER(USER_ID, ADDRESS_ID, FIRSTNAME, PHONE_NUMBER, TOTALSUM, DATE, STATUS) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private String UPDATE_CLIENT_ORDER = "UPDATE CLIENT_ORDER SET USER_ID=?, ADDRESS_ID=?, FIRSTNAME, PHONE_NUMBER=?, TOTALSUM=?, DATE=?, STATUS=? WHERE ID=?";
	private String GET_CLIENT_ORDER = "SELECT ID, USER_ID, ADDRESS_ID, FIRSTNAME, PHONE_NUMBER, TOTALSUM, DATE, STATUS FROM CLIENT_ORDER";
	private String DELETE_CLIENT_ORDER = "DELETE FROM CLIENT_ORDER WHERE ID=?";
	
    @Override
    public void add(ClientOrder clientOrder) throws DaoException {
        addUpdate(clientOrder, ADD_CLIENT_CLIENT_ORDER);
    }

    @Override
    public void update(ClientOrder clientOrder) throws DaoException {
        addUpdate(clientOrder, UPDATE_CLIENT_ORDER);
    }

    private void addUpdate(ClientOrder clientOrder, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		preparedStatement.setInt(1, clientOrder.getUserId());
		preparedStatement.setInt(2, clientOrder.getAddressId());
		preparedStatement.setString(3, clientOrder.getFirstName());
		preparedStatement.setString(4, clientOrder.getPhoneNumber());
		preparedStatement.setInt(5, clientOrder.getTotalSum());
		preparedStatement.setDate(6, new java.sql.Date(clientOrder.getDate().getTime()));
		preparedStatement.setString(7, String.valueOf(clientOrder.getStatus()));
            if (clientOrder.getId() != null) preparedStatement.setInt(8, clientOrder.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            pool.returnConnection(connection);
            }
	
	@Override
    public ClientOrder getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }
	
	public ClientOrder getUserId(int id) throws DaoException {
        return getByParameter(id, "USER_ID");
	}
	
	private List<ClientOrder> getByParameter(Object parameter, String sqlParameter) throws DaoException {
		Connection connection = pool.getConnection();
		List<ClientOrder> clientOrderList = new ArrayList<>();
        ClientOrder clientOrder = null;
        PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_ORDER + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
        ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOrder clientOrder = getClientOrderDbFields(resultSet);
                clientOrderList.add(clientOrder);
            }
            resultSet.close();
            preparedStatement.close();
            pool.returnConnection(connection);
			
        return clientOrderList;
    }
	
	private ClientOrder getClientOrderDbFields(ResultSet resultSet) throws SQLException {
        ClientOrder clientOrder = new ClientOrder();
		
        clientOrder.setId(resultSet.getInt("ID"));
        clientOrder.setUserId(resultSet.getInt("USER_ID"));
		clientOrder.setAddressId(resultSet.getInt("ADDRESS_ID"));
		clientOrder.setFirstName(resultSet.getString("FIRSTNAME"));
		clientOrder.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
		clientOrder.setTotalSum(resultSet.getInt("TOTALSUM"));
		clientOrder.setDate(resultSet.getDate("DATE"));
		clientOrder.setStatus(ClientOrder.Status.valueOf(resultSet.getString("STATUS")));
		
        return clientOrder;
    }
	
    @Override
    public List<ClientOrder> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<ClientOrder> clientOrderList = new ArrayList<>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(GET_CLIENT_ORDER);
		ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientOrder clientOrder = getClientOrderDbFields(resultSet);
                clientOrderList.add(clientOrder);
            }
		resultSet.close();
		preparedStatement.close();
		pool.returnConnection(connection);
      
        return clientOrderList;
    }

    @Override
    public void remove(ClientOrder clientOrder) throws DaoException {
        Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_ORDER);
		preparedStatement.setInt(1, clientOrder.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
    }
}
