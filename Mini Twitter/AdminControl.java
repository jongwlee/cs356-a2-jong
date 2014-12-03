import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JTextArea;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;


public class AdminControl extends JFrame {

	private static UserGroup group;
	private JTree tree;
	private DefaultMutableTreeNode root;
	private String selectedNode = "Root";
	private JFrame frame;
	private JTextField username_text;
	private JTextField groupname_text;
    private OpenUser open_user;
    private CalculateVisitor calculate_visitor;
    private long temp_time;
    private List<Component> components;
    private User temp_user = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminControl window = new AdminControl();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Add user and "Root" as default group
	public void addUser(String user_name){
		addUser(user_name, "Root");
	}
	
	//Add new Users
	public boolean addUser(String user_name, String group_name){
		UserGroup temp = group.getGroup(group_name);
		if(temp != null){
			User user = new User(user_name);
			components.add(user);
			setTime(user);
			temp.add(user);
			calculate_visitor.visit(user);
		}
		
		return true;
	}
	
	
	private void setTime(User user){
		temp_time = user.getCreationTime();
	}
	
	//Add new Group with "Root" as default
	public void addGroup(String group_name){
		addGroup("Root", group_name);
	}
	
	
	//Add new Group
	public boolean addGroup(String parent_group, String group_name){
		if(group.getName().equals(group_name)){
			return false;
		}
		
		UserGroup temp = group.getGroup(parent_group);
		temp.add((new UserGroup(group_name)));
		components.add(temp);
		calculate_visitor.visit(temp);
		return true;
	}
	
	//Make tree
	private DefaultTreeModel make_model(){
		root = new DefaultMutableTreeNode("Root");
		make_tree(root, ((UserGroup) group).getChild());
		DefaultTreeModel tree = new DefaultTreeModel(root);
		return tree;
	}
	
	//Makes child components for tree
	private void make_tree(DefaultMutableTreeNode root, List<Component> child){
		for(Component component : child){
			DefaultMutableTreeNode tree_node = new DefaultMutableTreeNode(((Component) component).getName());
			root.add(tree_node);
			
			if(component instanceof UserGroup){
				make_tree(tree_node, ((UserGroup) component).getChild());
			}
		}
	}
	
	//Gets the updated user
	private String getUpdateUser(){
		String user_temp;
		long time = 0;
		
		for(Component comp : components){
			if(comp instanceof User){
				if(((User) comp).getLastUpdateTime() > time){
					time = ((User) comp).getLastUpdateTime();
					temp_user = (User) comp;
				}
			}
		}
		user_temp = "The last updated user is: " + temp_user.getName();
		return user_temp;
	}
	
	/**
	 * Create the application.
	 */
	public AdminControl() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		components = new ArrayList<Component>();
		open_user = new OpenUser();
		calculate_visitor = new CalculateVisitor();
		group = new UserGroup("Root");
		frame = new JFrame();
		frame.setBounds(100, 100, 374, 337);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "name_1953384843334100");
		panel.setLayout(null);
		
		JButton adduser_button = new JButton("Add User");
		adduser_button.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				addUser(username_text.getText(), selectedNode);
				tree.setModel(make_model());
			}
		});
		adduser_button.setBounds(228, 10, 107, 40);
		panel.add(adduser_button);
		
		JButton addgroup_button = new JButton("Add Group");
		addgroup_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addGroup(groupname_text.getText());
				tree.setModel(make_model());
			}
		});
		addgroup_button.setBounds(228, 54, 107, 40);
		panel.add(addgroup_button);
		
		JButton usertotal_button = new JButton("User Total");
		usertotal_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(new JFrame(), "Total Users: " + calculate_visitor.getUser_total());
			}
		});
		usertotal_button.setBounds(111, 148, 107, 40);
		panel.add(usertotal_button);
		
		JButton messagetotal_button = new JButton("Messages Total");
		messagetotal_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(new JFrame(), "Total Messages: " + OpenUser.getMessageTotal());
			}
		});
		messagetotal_button.setBounds(111, 199, 107, 40);
		panel.add(messagetotal_button);
		
		JButton grouptotal_button = new JButton("Group Total");
		grouptotal_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(new JFrame(), "Total Group: " + calculate_visitor.getGroup_total());
			}
		});
		grouptotal_button.setBounds(228, 148, 107, 40);
		panel.add(grouptotal_button);
		
		JButton percentage_button = new JButton("Percentage");
		percentage_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(new JFrame(), "Total Group: " + OpenUser.getPercentTotal());
			}
		});
		percentage_button.setBounds(228, 199, 107, 40);
		panel.add(percentage_button);
		
		JButton open_button = new JButton("Open User View");
		open_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("The time to create " + selectedNode + " is: " + temp_time);
				open_user.setVisible(true);
				open_user.setSelectedNode(selectedNode, group);
			}
		});
		open_button.setBounds(109, 105, 226, 32);
		panel.add(open_button);
		
		tree = new JTree(make_model());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setBounds(0, 1, 101, 261);
		
		tree.addTreeSelectionListener(new TreeSelectionListener(){
			public void valueChanged(TreeSelectionEvent e){
				selectedNode = e.getPath().getLastPathComponent().toString();
			}
		});
		tree.setBounds(0, 0, 101, 261);
		panel.add(tree);
		
		username_text = new JTextField();
		username_text.setText("New User: ");
		username_text.setBorder(BorderFactory.createLoweredBevelBorder());
		username_text.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				username_text.setText("");
			}
		});
		username_text.setColumns(10);
		username_text.setBounds(121, 20, 86, 20);
		panel.add(username_text);
		
		groupname_text = new JTextField();
		groupname_text.setText("New Group: ");
		groupname_text.setBorder(BorderFactory.createLoweredBevelBorder());
		groupname_text.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				groupname_text.setText("");
			}
		});
		groupname_text.setColumns(10);
		groupname_text.setBounds(121, 64, 86, 20);
		panel.add(groupname_text);
		
		JButton btnValidat = new JButton("Validate");
		btnValidat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(calculate_visitor.check()){
					JOptionPane.showMessageDialog(null, "All Users and Groups are Valid!");
				}
				else{
					JOptionPane.showMessageDialog(null, "Invalid: There is a User or Group with the same name");
				}
			}
		});
		btnValidat.setBounds(111, 250, 107, 33);
		panel.add(btnValidat);
		
		JButton btnUpdatedUser = new JButton("Updated User");
		btnUpdatedUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, getUpdateUser());
			}
		});
		btnUpdatedUser.setBounds(228, 250, 107, 33);
		panel.add(btnUpdatedUser);
	}
}
