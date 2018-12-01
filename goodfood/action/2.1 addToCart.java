public class AddToCart implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();		
	ContentDAO contentDAO = new ContentDAO();
	ReserveDAO reserveDAO = new ReserveDAO();
	ClientOrderDAO clientOrderDAO = new ClientOrderDAO();
	Reserve reserve = new Reserve();
	int newQuantityAll;
	
	// if guest
	if (httpSession.getParameter(USER)== null) {
		request.setAttribute("Sign in or register");
		return AUTHORIZATION_JSP;
	}
	
	int jspContentId = Integer.parseInt(request.getParameter(CONTENT_ID));
	int jspQuantityReserve = Integer.parseInt(request.getParameter(QUANTITY_RESERVE));
	ClientOrder clientOrder = = clientOrderDAO.getByUserId(httpSession.getParameter(USER).getId());
	
	if (clientOrder != null) {
	Validator.validateNumber(jspQuantityReserve);
	
	Content content = ContentDAO.getById(jspContentId);
	newQuantityAll = content.getQuantityAll() - jspQuantityReserve;
	
	if (newQuantityAll > 0) {
		content.setQuantityAll(newQuantityAll);
		contentDAO.add(content);
		reserve.setOrderId(clientOrder.getId());
		reserve.setContentId(jspContentId);
		reserve.setQuantityReserve(jspQuantityReserve);
		reserveDAO.add(reserve);
	}
	}
}
	return CATALOG_JSP;
}