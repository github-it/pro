public class OrderConfirm implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();
	String jspFirstName = request.getParameter("firstName");
	String jspPhoneNumber = request.getParameter("phoneNumber");
	String jspStreet = request.getParameter("street");
	String jspBuilding = request.getParameter("building");
	String jspApartment = request.getParameter("apartment");
	String view;
	
	ClientOrderDAO clientOrderDAO = new ClientOrderDAO();
	AddressDAO addressDAO = new AddressDAO();
	
	Validator.validateTextInput(jspFirstName);
	Validator.validateTextInput(jspStreet);
	Validator.validateTextInput(jspBuilding);
	Validator.validateTextInput(jspApartment);
	Validator.validatePhoneNumber(jspPhoneNumber);
	
	User user = httpSession.getParameter(USER);
	if (user == null){
		request.setAttribute("No user found");
		view = AUTHORIZATION_JSP;
	} else ClientOrder clientOrder = clientOrderDAO.getById(user.getById());
	clientOrder.setFirstName(jspFirstName);
	clientOrder.jspPhoneNumber(jspPhoneNumber);
	
	Address address = addressDAO.getById(user.getById());

	if (address == null){
	address.setUserId(user.getById());	
	address.setStreet(jspStreet);
	address.setBuilding(jspBuilding);
	address.setApartment(jspApartment);
	addressDAO.add(address);
	}
	clientOrder.setAddressId(addressDAO.getByUserId(user.getById()));
	clientOrderDAO.update(clientOrder);
	}
	request.setAttribute("Ordered");
	return USER_MAIN_JSP;
}