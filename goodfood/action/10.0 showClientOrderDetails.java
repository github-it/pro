public class ShowReserveDetails implements Action {
	
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();
	ReserveDAO reserveDAO = new ReserveDAO();
	int jspClientOrderId = Integer.parseInt(request.getParameter(CLIENT_ORDER_ID));	
	
	List<Reserve> reserveList = ReserveDAO.getAllByClientOrderId(jspClientOrderId);
	httpSession.setParameter(RESERVE_LIST, reserveList);
	}
}