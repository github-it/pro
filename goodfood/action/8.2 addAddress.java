public class AddAddress implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	String jspStreet = request.getParameter(STREET);
	String jspBuilding = request.getParameter(BUILDING);
	String jspApartment = request.getParameter(APARTMENT);
	
	Address address = new Address();
	AddressDAO addressDAO = new AddressDAO();
	
	Validator.validateTextInput(jspStreet);
	Validator.validateTextInput(jspBuilding);
	Validator.validateTextInput(jspApartment);
	
	if (addressDAO.getByStreet(jspStreet) && addressDAO.getByBuilding(jspBuilding) && addressDAO.getByApartment(jspApartment) == null){
	address.setUserId(user.getById());	
	address.setStreet(jspStreet);
	address.setBuilding(jspBuilding);
	address.setApartment(jspApartment);
	addressDAO.add(address);
	}
	}
	return ADDRESS_JSP;
}