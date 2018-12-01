public class addNewQuantityInCart implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	ReserveDAO reserveDAO = new ReserveDAO();
	int jspReserveId = Integer.parseInt(request.getParameter(CONTENT_ID));
	int jspQuantityReserve = Integer.parseInt(request.getParameter(QUANTITY_RESERVE));	
	int tempAll;
	int tempQuantityAll;
		
	Reserve reserve = reserveDAO.getById(jspReserveId);
	tempAll = reserve.getQuantityReseve() + reserve.content.getQuantityAll();
		
	Validator.validateNumber(jspQuantityReserve);
	tempQuantityAll = tempAll - jspQuantityReserve;
		
	if (tempQuantityAll > 0) {
	reserve.setQuantityReseve(jspQuantityReserve);
	reserve.content.setQuantityAll(tempQuantityAll);
	reserveDAO.add(reserve);
	}
	return CATALOG_JSP;
	}
}