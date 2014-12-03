import java.util.ArrayList;
import java.util.List;


public class UserGroup implements Component, Visitable{

	private String group_name;
	private List<Component> children;
	private long creationTime;
	
	public UserGroup(String group_name){
		creationTime = System.currentTimeMillis();
		this.group_name = group_name;
		children = new ArrayList<Component>();
	}
	
	//Adds the child of this group
	public void add(Component user){
		children.add(user);
	}
	
	public void setGroup_name(String group_name){
		this.group_name = group_name;
	}
	
	public String getName(){
		return group_name;
	}

	public List<Component> getChild() {
		return children;
	}

	//Finds the group using the group name
	public UserGroup getGroup(String group_name){
		if(this.group_name.equalsIgnoreCase(group_name)){
			return this;
		}
		
		for(Component component : children){
			if(component instanceof UserGroup){
				if(((UserGroup) component).getGroup(group_name) != null){
					return ((UserGroup) component).getGroup(group_name);
				}
			}
		}
		
		return null;
	}
	
	//Find the user
	public User getUser(String user_name){
			for(Component user : children){
				User temp = user.getUser(user_name);
				
				if(temp != null){
					return temp;
				}
		}
		
		return null;
	}

	//A boolean check to find users
	public boolean checkUser(String user_name){
		for(Component child : children){
			if(child.getName().equals(user_name)){
				return true;
			}
		}
		return false;
	}
	
	//Visitor pattern
	public void accept(Visitor visitor){
		visitor.visit(this);
	}
	
	public long getCreationTime(){
		return creationTime;
	}

	
}
