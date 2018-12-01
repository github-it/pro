public class AddInfoToClientOrder implements Action {
	
	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	String jspStreet = request.getParameter(STREET);
	String jspBuilding = request.getParameter(BUILDING);
	String jspApartment = request.getParameter(APARTMENT);
	
	ClientOrder clientOrder = new ClientOrder();
	ClientOrderDAO clientOrderDAO = new ClientOrderDAO();
	
	Validator.validateTextInput(jspStreet);
	Validator.validateTextInput(jspBuilding);
	Validator.validateTextInput(jspApartment);
	
	if (clientOrderDAO.getByStreet(jspStreet) && clientOrderDAO.getByBuilding(jspBuilding) && clientOrderDAO.getByApartment(jspApartment) == null){
	clientOrder.setUserId(user.getById());	
	clientOrder.setStreet(jspStreet);
	clientOrder.setBuilding(jspBuilding);
	clientOrder.setApartment(jspApartment);
	clientOrderDAO.add(clientOrder);
	}
	}
	return CLIENT_ORDER_INFO_JSP;
}