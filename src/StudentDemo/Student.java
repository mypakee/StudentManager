package StudentDemo;
//����ѧ����  ���� ���� ��ַ �Ա� ѧ��
public class Student {
	private String name;
	private String age;
	private String address;
	private String id;
	
	public Student () {
		
	}
	
	public Student(String name,String age,String address,String id) {
		this.name= name;
		this.address=address;
		this.age= age;
		this.id= id;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
