public class Reserve extends Entity {
    private static final Logger LOGGER = Logger.getLogger(Reserve.class);
	
	private int orderId;
	private Content content;
    private int quantityReseve;
	
	public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
	
	public int getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
	
	public int getQuantityReseve() {
        return quantityReseve;
    }

    public void setQuantityReseve(int quantityReseve) {
        this.quantityReseve = quantityReseve;
    }
}