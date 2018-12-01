public class EditAddress implements Action {
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();
	int jspAddressId = Integer.parseInt(request.getParameter(ADDRESS_ID));	
	AddressDAO addressDAO = new AddressDAO();
	
	Address address = addressDAO.getById(jspAddressId);
	request.setParameter(ADDRESS, address);
	}
	return ADDRESS_JSP;
}