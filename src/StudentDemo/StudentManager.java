package StudentDemo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManager {
			public static void main(String[] args) throws IOException, SQLException {
				ArrayList<Student> arr = new ArrayList<Student>();
				while (true) {
					System.out.println("--------��ӭ����ѧ������ϵͳ--------");
					System.out.println("1 �鿴����ѧ��");
					System.out.println("2 ���ѧ��");
					System.out.println("3 ɾ��ѧ��");
					System.out.println("4 �޸�ѧ����Ϣ");
					System.out.println("5 �˳�ϵͳ");
					System.out.println("---�������Ӧ����ѡ����Ĳ���---");
					Scanner sc = new Scanner(System.in);  //¼���������
					String  choiceNum= sc.nextLine();
					
					//��switch�������Ӧ����
					switch (choiceNum) {
					case "1":
						findAllStudent(arr); 						//��Ӧ��������ûд
						break;
					case "2":
						addStudent(arr);
						break;
					case "3":
						deleteStudent(arr);
						break;
					case"4":
						updateStudent(arr);
						break;
					case"5":
						System.out.println("лл���ʹ�ã�");
						System.exit(0);
						
					}
				}
			}
			//�޸�ѧ����ʵ��
			private static void updateStudent(ArrayList<Student> arr) throws SQLException {
				Scanner  sc = new Scanner(System.in);
				System.out.println("������Ҫ�޸ĵ�ѧ��id");
				String stuId= sc.nextLine();
				//boolean index = false;
				int  index = -1;
				for (int i=0 ; i<arr.size();i++) {
					Student s = arr.get(i);
					if (s.getId().equals(stuId)) {
						index = i;
						break;
					}
				}
				if (index!=-1) {
					System.out.println("�������µ�������");
					String name = sc.nextLine();
					System.out.println("�������µ����䣺");
					String age = sc.nextLine();
					System.out.println("�������µ�ѧ�ţ�");
					String id = sc.nextLine();
					//System.out.println("�������µ��Ա�");
					System.out.println("�������µĵ�ַ��");
					String address = sc.nextLine();
					Student s = new Student();
					s.setAddress(address);
					s.setName(name);
					s.setAge(age);
					s.setId(id);
					arr.set(index, s);
					System.out.println("�޸�ѧ����Ϣ�ɹ���");
					
					Connection conn = null;
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","admin");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						//System.out.println(conn);
					String sql = "insert into student (id,name,age,address) values (?,?,?,?)";		//?ռλ�� ��1��ʼ��
					PreparedStatement ps = null;
					try {
						ps = conn.prepareStatement(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ps.setObject(1, id);
					ps.setObject(2, name);
					ps.setObject(3, age);
					ps.setObject(4, address);
					ps.execute();
					
					
				}else {
					System.out.println("������˼���������ѧ����Ϣ�����ڣ����������룡");
					return;
				}
				
			}
			//ɾ��ѧ����ʵ��
			private static void deleteStudent (ArrayList<Student> arr) {
				Scanner sc = new Scanner (System.in);
				System.out.println("��������Ҫɾ����ѧ��ID:");
				String stuId = sc.nextLine();
				int index = -1;
				for (int x = 0 ; x<arr.size();x++) {
					Student s = arr.get(x);
					if(s.getId().equals(stuId)) {
						index = x;
						break;
						}
				}
				if (index != -1) {
					arr.remove(index);
					System.out.println("ɾ��ѧ���ɹ���");
				}else {
					System.out.println("�������ѧ�������ڣ����������ѡ��");
					return;
				}
				
			}
			//���ѧ����ʵ��  //  ͨ��ѧ��ȥ���ѧ���Ƿ����
			private static void addStudent(ArrayList<Student> arr) throws IOException, SQLException {
				Scanner sc = new Scanner (System.in);
				String id ;
				while(true) {
					System.out.println("��������Ҫ������ѧ�ţ�");
					id = sc.nextLine();
					
					boolean flag =false;
					for(int x = 0 ; x<arr.size();x++) {
						Student s = arr.get(x);
						if (s.getId().equals(id)) {
							flag = true;	
						}
					}if (flag==true) {
						System.out.println("�������ѧ���Ѿ���ռ�����������룺");
						
				}else {
					break;
				}
			}
				System.out.println("�������µ�������");
				String name = sc.nextLine();
				System.out.println("�������µ����䣺");
				String age = sc.nextLine();
				//System.out.println("�������µ�ѧ�ţ�");
				String newid = id;												//���ﲻ�������룬ֱ�Ӹ�ֵ���ɡ�
				//System.out.println("�������µ��Ա�");
				System.out.println("�������µĵ�ַ��");
				String address = sc.nextLine();
				Student s = new Student();
				s.setAddress(address);
				s.setName(name);
				s.setAge(age);
				s.setId(newid);
				arr.add(s);
				ArrayListToFile(arr);
				System.out.println("���ѧ����Ϣ�ɹ���");
				
			Connection conn  =  DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","admin");
				//System.out.println(conn);
			String sql = "insert into student (id,name,age,address,regtime) values (?,?,?,?,?)";		//?ռλ�� ��1��ʼ��
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setObject(1, newid);
			ps.setObject(2, name);
			ps.setObject(3, age);
			ps.setObject(4, address);
			ps.setObject(5, new Date(System.currentTimeMillis()));
			ps.execute();
				
				
					
			}
			
			private static void findAllStudent(ArrayList<Student> arr) {
					 if (arr.size()==0){
						 System.out.println("�Բ���Ŀǰû��ѧ����Ϣ����鿴��");
						 return;
					 }
					 System.out.println("ѧ��\t\t ����\t����\t\t��ַ");
					 System.out.println("--------------------------------------");
					 for(int x= 0 ; x<arr.size();x++) {
						 Student s = arr.get(x);
						 System.out.println(s.getId()+"\t\t"+s.getName()+"\t\t"+s.getAge()+"\t\t"+s.getAddress());
					 }
					 
			}
			
			public static void ArrayListToFile(ArrayList <Student> arr) throws IOException{
				 BufferedWriter bw = new BufferedWriter(new FileWriter("Student.txt"));
				 //���� �õ�Ԫ�� д��txt
				 for(int x = 0 ; x<arr.size();x++) {
					 Student s = arr.get(x);
					 bw.write(""+s.getName()+""+s.getAge()+s.getAddress()+s.getId());
					 bw.close();
				 }
			}
			
			
}
