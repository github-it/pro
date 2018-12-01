public class SavePassword implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	String jspNowPassword = request.getParameter(PASSWORD);
	String jspNewPassword = request.getParameter(NEW_PASSWORD);
	String jspReNewPassword = request.getParameter(RE_NEW_PASSWORD);
	UserDAO userDao = new UserDAO();
	User user = userDao.getById(request.getParameter(USER).getId());
		
	if (user.getPassword().equals(jspNowPassword) && jspNewPassword.equals(jspReNewPassword)) {
	user.setPassword(jspNewPassword);
	userDao.update(user);
	}	
	}
	return CABINET_JSP;
}