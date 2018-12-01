public class ClientOrder extends Entity {
	
	private static final Logger LOGGER = Logger.getLogger(Order.class);
	
	private int userId;
	private int addressId;
	private String firstName;
	private String phoneNumber;
	private int totalSum;
	private Date orderDate = new Date();
	private Status status;
	
	public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
	}
	
	public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
	}
	
	public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
	
    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }
	
	public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
	
	public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

	public enum Status {
        CANCELLED, CART, CONFIRMED, DONE
    }
}