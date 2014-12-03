import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class User extends Observable implements Component, Observer, Visitable {
	private String user_name;
	private List<User> followers;
	private List<User> followings;
	private List<String> newsFeed;
	private int message_counter;
	private long creationTime;
	private long lastUpdateTime;

	public User(String user_name){
		this.user_name = user_name;
		followers = new ArrayList<User>();
		followings = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		message_counter = 0;
		creationTime = System.currentTimeMillis();
	}


	//Add following
	public void add_following(User user_name){
		if(this.user_name == user_name.user_name || followings.contains(user_name)){
			return;
		}
		
		user_name.addObserver(this);
		followings.add(user_name);
		this.addObserver(user_name);
	}
	
	public void add_followers(User user_name){
		if(this.user_name == user_name.user_name || followers.contains(user_name)){
			return;
		}
		
		followers.add(user_name);
	}
	
	public String getName() {
		return user_name;
	}
	
	public void setUser_name(String user_name){
		this.user_name = user_name;
	}


	public List<User> getFollowers() {
		return followers;
	}


	public List<User> getFollowings() {
		return followings;
	}


	public List<String> getNewsFeed() {
		return newsFeed;
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	
	public long getCreationTime() {
		return creationTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	//Send messages and notify the observers or followers
	public void sendMessages(String messages){
		lastUpdateTime = System.currentTimeMillis();
		newsFeed.add("[" + lastUpdateTime + "]" + user_name + ": " + messages);
		message_counter++;
		setChanged();
		notifyObservers(messages);
	}
	
	public int getMessage_counter() {
		return message_counter;
	}

	//Updates to the news feed list
	public void update(Observable observable, Object object) {
		newsFeed.add("[" + lastUpdateTime + "] " + ((User)observable).getName() + ": " + (String) object);
	}
	
	//Finds the user by finding their string name
	public User getUser(String user_name) {
		if(this.user_name.equals(user_name)){
			return this;
		}
		else{
			return null;
		}
	}
	
}
