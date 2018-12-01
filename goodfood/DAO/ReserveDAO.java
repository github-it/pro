public class ReserveDAO implements GenericDAO<Reserve> {
	
    private ConnectionPool pool = ConnectionPool.getInstance();

	private String ADD_RESERVE = "INSERT INTO RESERVE(CLIENT_ORDER_ID, CONTENT_ID, QUANTITY_RESERVE) VALUES(?, ?, ?)";
    private String UPDATE_RESERVE = "UPDATE RESERVE SET CLIENT_ORDER_ID=?, CONTENT_ID=?, QUANTITY_RESERVE WHERE ID=?";
	private String GET_RESERVE = "SELECT ID, CLIENT_ORDER_ID, CONTENT_ID, QUANTITY_RESERVE FROM RESERVE";
	private String DELETE_RESERVE = "DELETE FROM RESERVE WHERE ID=?";
	
    @Override
    public void add(Reserve reserve) throws DaoException {
        addUpdate(reserve, ADD_RESERVE);
    }

    @Override
    public void update(Reserve reserve) throws DaoException {
        addUpdate(reserve, UPDATE_RESERVE);
    }

    private void addUpdate(Reserve reserve, String sqlCommand) throws DaoException {
        Connection connection = pool.getConnection();

		PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);
		preparedStatement.setInt(1, reserve.getClientOrderId());
		preparedStatement.setInt(2, reserve.getContent().getId());	
		preparedStatement.setInt(3, reserve.getQuantityReserve());
            if (reserve.getId() != null) preparedStatement.setInt(4, reserve.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
			pool.returnConnection(connection);
            }
	
	@Override
    public Reserve getById(int id) throws DaoException {
        return getByParameter(id, "ID");
    }
	
	public Reserve getClientOrderId(int id) throws DaoException {
        return getByParameter(id, "CLIENT_ORDER_ID");
	}
	
	private Reserve getByParameter(Object parameter, String sqlParameter) throws DaoException {
		Connection connection = pool.getConnection();
        Reserve reserve = null;
    
		PreparedStatement preparedStatement = connection.prepareStatement(GET_RESERVE + " WHERE " + sqlParameter + "=?");
		preparedStatement.setObject(1, parameter);
		ResultSet resultSet = preparedStatement.executeQuery();
		ContentDao contentDao = new ContentDao();
            if (resultSet.next()) {
                reserve = getReserveDbFields(ContentDao, resultSet);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
      
        return reserve;
    }
	
	private Reserve getReserveDbFields(ResultSet resultSet) throws SQLException {
        Reserve reserve = new Reserve();
        reserve.setId(resultSet.getInt("ID"));
		
		int content_id = resultSet.getInt("CONTENT_ID");
        Content content = ContentDao.getById(content_id);
        reserve.setContent(content);
		
        reserve.setClientOrderId(resultSet.getInt("CLIENT_ORDER_ID"));
		reserve.setContentId(resultSet.getInt("CONTENT_ID"));
		reserve.setQuantityReserve(resultSet.getInt("QUANTITY_RESERVE"));
		
        return reserve;
    }
	
    @Override
    public List<Reserve> getAll() throws DaoException {
        Connection connection = pool.getConnection();
        List<Reserve> reserveList = new ArrayList<>();

		PreparedStatement preparedStatement = connection.prepareStatement(GET_RESERVE);
		ResultSet resultSet = preparedStatement.executeQuery();
		ContentDao contentDao = new ContentDao();
            while (resultSet.next()) {
                Reserve reserve = getReserveDbFields(ContentDao, resultSet);
                reserveList.add(reserve);
            }
            resultSet.close();
            preparedStatement.close();
			pool.returnConnection(connection);
  
        return reserveList;
    }
	
	public List<Reserve> getAllByClientOrderId(int clientOrderId) throws DaoException {
        Connection connection = pool.getConnection();
        List<Reserve> reserveList = new ArrayList<>();

		PreparedStatement preparedStatement = connection.prepareStatement(GET_RESERVE + " WHERE CLIENT_ORDER_ID=?");
		preparedStatement.setInt(1, clientOrderId);
		ResultSet resultSet = preparedStatement.executeQuery();	
		ContentDao contentDao = new ContentDao();
            while (resultSet.next()) {
                Reserve reserve = getReserveDbFields(ContentDao, resultSet);
                reserveList.add(reserve);
            }
            resultSet.close();
            preparedStatement.close();

        return reserveList;
    }

    @Override
    public void remove(Reserve reserve) throws DaoException {
        Connection connection = pool.getConnection();
        
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVE);
		preparedStatement.setInt(1, reserve.getId());
		preparedStatement.executeUpdate();
		preparedStatement.close();
		pool.returnConnection(connection);
            }
        }