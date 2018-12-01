public class GetFoodFromCart implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();	
    ClientOrderDAO clientOrderDAO = new ClientOrderDAO();
	ClientOrder clientOrder = clientOrderDAO.getByUserId(request.getParameter(USER).getId());
	int totalSum;
		
	if (clientOrder.getStatus() == ClientOrder.Status.CART) {	
	List<Reserve> reserveList = ReserveDao.getAllByClientOrderId(clientOrder.getId());
			
	for (Reserve reserve : reserveList) {
		totalSum = totalSum + (reserve.content.getPrice() * reserve.getQuantityReserve());
	}
	clientOrder.setTotalSum(totalSum);
	clientOrderDAO.update(clientOrder);
	request.setParameter(CLIENT_ORDER, clientOrder);
	}
	}
}