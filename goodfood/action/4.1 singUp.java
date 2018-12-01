public class SignUp implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		HttpSession httpSession = request.getSession();
		String newUserBalance = NEW_USER_BALANCE;
        String jspLoginEmail = request.getParameter(LOGINEMAIL);
		String jspNewPassword = request.getParameter(NEW_PASSWORD);
		String jspReNewPassword = request.getParameter(RE_NEW_PASSWORD);
		
		Integer newOrderAddress = ZERO;
		String newOrderFirstName = EMPTY_STRING;
		String newOrderPhoneNumber = EMPTY_STRING;
		Integer newOrderTotalSum = ZERO;
		Date newOrderDate = null;
		String view;

        Validator.validateLoginEmail(jspLoginEmail);
        Validator.validatePassword(jspNewPassword);
		Validator.validatePassword(jspReNewPassword);
		
		UserDAO userDao = new UserDAO(jspLoginEmail);
		User user = userDao.getByLogin(jspLoginEmail);
		
		if (user != null) {
			request.setAttribute("Login is busy");
			view = AUTHORIZATION_JSP;
		} else if (!jspNewPassword.equals(jspReNewPassword)) {
			request.setAttribute("NewPassword and ReNewPassword - not equals");
			view = AUTHORIZATION_JSP;
        } else {
			user.setLoginEmail(jspLoginEmail);
			user.setPassword(password);
			user.setStatus(User.Status.USER);
			user.setBalance(newUserBalance);
			userDao.add(user);
			user = userDao.getByLogin(jspLoginEmail);
            httpSession.setAttribute(USER, user);
			
			ClientOrderDAO clientOrderDao = new ClientOrderDAO();
			ClientOrder clientOrder = new ClientOrder();
			
			clientOrder.setUserId(user.getId);
			clientOrder.setAddressId(newOrderAddress);
			clientOrder.setFirstName(newOrderFirstName);
			clientOrder.setPhoneNumber(newOrderPhoneNumber);
			clientOrder.setTotalSum(newOrderTotalSum);
			clientOrder.setDate(newOrderDate);
			clientOrder.setStatus(ClientOrder.Status.CART);
			httpSession.setParameter(CLIENT_ORDER, clientOrder);
			view = USER_MAIN_JSP;
			}
			return view;
        }
    }