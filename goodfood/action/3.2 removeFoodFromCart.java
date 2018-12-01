public class removeFoodFromCart implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {	 
	ReserveDAO reserveDAO = new ReserveDAO();
	int jspReserveId = Integer.parseInt(request.getParameter(RESERVE_ID));
	reserveDAO.remove(jspReserveId);
	 
	return CATALOG_JSP;
}
}