public class GetAllContent implements Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	ContentDAO contentDao = new ContentDAO();
	List<Content> contentList = contentDao.getAll();
	request.setParameter(CONTENT_LIST, contentList); 
	return CATALOG_JSP;
	}
}