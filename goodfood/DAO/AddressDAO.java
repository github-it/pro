public class AddressDAO implements GenericDao<Address> {

    private ConnectionPool pool = ConnectionPool.getInstance();
	
	private String ADD_ADDRESS = "INSERT INTO ADDRESS(STREET, BUILDING, APARTMENT, USER_ID) VALUES( ?, ?, ?, ?)";
	private String UPDATE_ADDRESS = "UPDATE ADDRESS SET STREET=?, BUILDING=?, APARTMENT=?, USER_ID=? WHERE ID=?";
	private String GET_ADDRESS = "SELECT ID, STREET, BUILDING, APARTMENT, USER_ID FROM ADDRESS";
	private String DELETE_ADDRESS = "DELETE FROM ADDRESS WHERE ID=?";
    
	@Override
    public void add(Address address) throws DaoException {
        addUpdate(address, ADD_ADDRESS);
    }

    @Override
    public void update(Address address) throws DaoException {
        addUpdate(address, UPDATE_ADDRESS);
    }

	private void addUpdate(Address address, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();
		
		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		preparedStatement.setString(1, address.getStreet());
		preparedStatement.setString(2, address.getBuilding());
        preparedStatement.setString(3, address.getApartment());
		preparedStatement.setInt(4, address.getUserId());
		if (address.getId() != null) preparedStatement.setInt(5, address.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
        pool.returnConnection(connection);
        }

    @Override
    public Address getById(int id) throws DaoException { 
        return getByParameter(id, "ID");
    }

    public Address getByUserId(int id) throws DaoException {
        return getByParameter(id, "USER_ID");
    }
	
	public Address getByStreet(String street) throws DaoException {
        return getByParameter(id, "STREET");
    }
	
	public Address getByBuilding(String building) throws DaoException {
        return getByParameter(id, "BUILDING");
    }
	
	public Address getByApartment(String apartment) throws DaoException {
        return getByParameter(id, "APARTMENT");
    }
	
	private List<Address> getByParameter(Object parameter, String sqlParameter) throws DaoException {
        Connection connection = pool.getConnection();
		List<Address> addressList = new ArrayList<>();
        Address address = null;
        
		PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
		ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
			Address address = getAddressDbFields(resultSet);
			addressList.add(address);
            }
        resultSet.close();
		preparedStatement.close();
		pool.returnConnection(connection);

        return addressList;
    }
	
    @Override
    public List<Address> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Address> addressList = new ArrayList<>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS);
		ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
			Address address = getAddressDbFields(resultSet);
			addressList.add(address);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);

        return addressList;
    }
    
    private Address getAddressDbFields(ResultSet resultSet) throws SQLException {
        Address address = new Address();
		
        address.setId(resultSet.getInt("ID"));
		address.setUserId(resultSet.getInt("USER_ID"));
        address.setStreet(resultSet.getString("STREET"));
        address.setBuilding(resultSet.getString("BUILDING"));
        address.setApartment(resultSet.getString("APARTMENT"));
		
        return address;
    }
	
	@Override
    public void remove(Address address) throws DaoException {
        Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS);
		preparedStatement.setInt(1, address.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
            }
    }
