import java.util.ArrayList;


public class CalculateVisitor implements Visitor {
	private ArrayList<String> user_list = new ArrayList<String>();
	private int user_total;
	private int group_total;
	private int message_total;
	private double positive_total;
	private boolean result;
	private String[] nice_words = {"positive","good", "great", "excellent",
								   "valuable", "wonderful", "superb", "marvelous",
								   "pleasing"};
	
	
	public double getPositive_total(){
		return positive_total;
	}
	
	public int getUser_total() {
		return user_total;
	}

	public int getGroup_total() {
		return group_total;
	}

	public int getMessage_total() {
		return message_total;
	}

	public boolean checkValid(){
		return result;
	}
	
	//Calculating the total users
	public void visit(User user) {
		user_total++;
		user_list.add(user.getName());
	}
	
	//Check for validation
	public boolean check(){
		result = true;
		String temp = user_list.get(0);
		for(int i = 1; i < user_list.size(); i++){
			if(user_list.contains(" ")){
				result = false;
			}
			else{
				if(temp.equals(user_list.get(i))){
					result = false;
				}
			}
		}
		
		return result;
	}
	
	
	

	//Calculating the total groups
	public void visit(UserGroup group) {
		group_total++;
	}

	//Calculate the message total and positive messages
	public void visit(String message){
		message_total++;
		
		for(int i = 0; i < nice_words.length; i++){
			if(message.contains(nice_words[i])){
				positive_total++;
				return;
			}
		}
	}
	
	//Calculate the percentage positive words
	public double getPercentage(){
		if(message_total == 0){
			return 0;
		}
		
		double percent = (positive_total / message_total) * 100;
		return percent;
	}
	
}
