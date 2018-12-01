public class SignIn implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	HttpSession httpSession = request.getSession();	
	UserDAO userDao = new UserDAO();
	String jspLoginEmail = request.getParameter(LOGINEMAIL);
	String jspPassword = request.getParameter(PASSWORD);	
	String MESSAGE;
	String view;

    Validator.validateLoginEmail(jspLoginEmail);
	Validator.validatePassword(jspPassword);	
	User user = userDao.getByLogin(jspLoginEmail);

	if (user.password.equals(jspPassword)) {	
	httpSession.setParameter(USER, user);	
	view = USER_MAIN_JSP;
	} else {
		// MESSAGE = resourceBundle.getString("Incorrect LoginEmail/Password");
		// request.setAttribute("message", MESSAGE);
		view = AUTHORIZATION_JSP;
        }
	return view;
	}
}