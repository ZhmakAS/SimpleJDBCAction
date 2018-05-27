package ua.nure.zhmaka.Practice8.dto;

public class Group {

	private int id;
	private String name;
	
	
	public static Group createGroup(String name) {
		Group group = new Group();
		group.setName(name);
		return group;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
