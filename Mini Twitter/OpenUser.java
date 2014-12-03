import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


public class OpenUser extends JFrame{

	private JTextArea user_id;
	private JTextArea tweet_message = new JTextArea();	
	private JTextArea list_message = new JTextArea();	
	private JTextArea newsFeed_message = new JTextArea();
	private String selectedNode;
	private UserGroup group;
	private User user;
    private static CalculateVisitor calculate_visitor;
    private static double temp_msg_total;
    private static double temp_percent_total;
	
	public OpenUser() {
		calculate_visitor = new CalculateVisitor();
		initialize();
	}	
	
	//Setting the selected node from the tree
	public void setSelectedNode(String selectedNode, UserGroup group){
		this.selectedNode = selectedNode;
		this.group = group;
	}
	
	//Adding the followers/observers
	private void addFollowers(String selectedNode, String user_name){
		User temp = group.getUser(selectedNode);
		User user_temp = group.getUser(user_name);
		temp.add_following(user_temp);
	}
	
	//Method to retrieve the followers
	private void get_Followers(){
		String str = "";
		User temp = group.getUser(selectedNode);
		for(User u : temp.getFollowings()){
			str += "- " + u.getName() + "\n";
		}
		list_message.setText("List View (Current Following): \n" + str);
		str = "";
	}
	
	//Set the messages for news feed for each user
	private void setMessage(String message){
		User temp = group.getUser(selectedNode);
		temp.sendMessages(message);
		calculate_visitor.visit(message);
	}
	
	//Retrieve messages from news feed
	private void get_messages(){
		String str = "";
		User temp = group.getUser(selectedNode);
		for(String messages : temp.getNewsFeed()){
			str += "- " + messages + "\n";
		}
		newsFeed_message.setText("List View (Current Following): \n" + str);
		str = "";
	}
	
	public static double getMessageTotal(){
		temp_msg_total = calculate_visitor.getMessage_total();
		return temp_msg_total;
	}
	
	public static double getPercentTotal(){
		temp_percent_total = calculate_visitor.getPercentage();
		return temp_percent_total;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 436, 491);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JPanel open_user_panel = new JPanel();
		open_user_panel.setLayout(null);
		open_user_panel.setBounds(0, 0, 426, 432);
		this.getContentPane().add(open_user_panel);
		
		user_id = new JTextArea();
		user_id.setText("Enter a user name:");
		user_id.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				user_id.setText("");
			}
		});
		user_id.setFont(new Font("Cambria", Font.PLAIN, 12));
		user_id.setBorder(BorderFactory.createLoweredBevelBorder());
		user_id.setBounds(10, 35, 173, 37);
		open_user_panel.add(user_id);
		
		JButton follow_user_button = new JButton("Follow User");
		follow_user_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addFollowers(selectedNode, user_id.getText());
				get_Followers();
			}
		});
		
		follow_user_button.setBounds(207, 35, 173, 37);
		open_user_panel.add(follow_user_button);
		list_message.setFont(new Font("Cambria", Font.PLAIN, 12));
		list_message.setText("List View (Current Following):\n");
		

		list_message.setBorder(BorderFactory.createLoweredBevelBorder());
		list_message.setBounds(10, 100, 372, 134);
		open_user_panel.add(list_message);
		
		tweet_message.setText("Enter a message.....");
		tweet_message.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				tweet_message.setText("");
			}
		});
		tweet_message.setFont(new Font("Cambria", Font.PLAIN, 12));
		tweet_message.setBorder(BorderFactory.createLoweredBevelBorder());
		tweet_message.setBounds(10, 245, 245, 37);
		open_user_panel.add(tweet_message);
		
		JButton post_tweet = new JButton("Post Tweet");
		post_tweet.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setMessage(tweet_message.getText());
				get_messages();
			}
		});
		post_tweet.setBounds(265, 245, 117, 37);
		open_user_panel.add(post_tweet);
		newsFeed_message.setFont(new Font("Cambria", Font.PLAIN, 12));
		newsFeed_message.setText("List View (News Feed):\n");
		
		newsFeed_message.setBorder(BorderFactory.createLoweredBevelBorder());
		newsFeed_message.setBounds(10, 293, 372, 169);
		open_user_panel.add(newsFeed_message);
	}
}
