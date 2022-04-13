package application.model;

public final class userHolder {
	  
	  private User user;
	  private final static userHolder INSTANCE = new userHolder();
	  
	  private userHolder() {}
	  
	  public static Object getInstance() {
	    return INSTANCE;
	  }
	  
	  public void setUser(User u) {
	    this.user = u;
	  }
	  
	  public User getUser() {
	    return this.user;
	  }
	}
