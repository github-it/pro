public class EditClientOrderUser implements Action {
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();
	ClientOrderDAO clientOrderDAO = new ClientOrderDAO();
	int jspClientOrderId = Integer.parseInt(request.getParameter(CLIENT_ORDER_ID));	
	
	ClientOrder clientOrder = clientOrderDAO.getById(jspClientOrderId);
	httpSession.setParameter(CLIENT_ORDER_ID, jspClientOrderId);
	}
}