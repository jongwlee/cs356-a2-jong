//Visitor pattern
public interface Visitor {

	public void visit(User user);
	
	public void visit(UserGroup group);
	
	public void visit(String message);
}
