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
					System.out.println("--------欢迎来到学生管理系统--------");
					System.out.println("1 查看所有学生");
					System.out.println("2 添加学生");
					System.out.println("3 删除学生");
					System.out.println("4 修改学生信息");
					System.out.println("5 退出系统");
					System.out.println("---请输入对应数字选择你的操作---");
					Scanner sc = new Scanner(System.in);  //录入键盘输入
					String  choiceNum= sc.nextLine();
					
					//用switch语句进入对应方法
					switch (choiceNum) {
					case "1":
						findAllStudent(arr); 						//对应方法，还没写
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
						System.out.println("谢谢你的使用！");
						System.exit(0);
						
					}
				}
			}
			//修改学生的实现
			private static void updateStudent(ArrayList<Student> arr) throws SQLException {
				Scanner  sc = new Scanner(System.in);
				System.out.println("请输入要修改的学生id");
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
					System.out.println("请输入新的姓名：");
					String name = sc.nextLine();
					System.out.println("请输入新的年龄：");
					String age = sc.nextLine();
					System.out.println("请输入新的学号：");
					String id = sc.nextLine();
					//System.out.println("请输入新的性别：");
					System.out.println("请输入新的地址：");
					String address = sc.nextLine();
					Student s = new Student();
					s.setAddress(address);
					s.setName(name);
					s.setAge(age);
					s.setId(id);
					arr.set(index, s);
					System.out.println("修改学生信息成功！");
					
					Connection conn = null;
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","admin");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						//System.out.println(conn);
					String sql = "insert into student (id,name,age,address) values (?,?,?,?)";		//?占位符 从1开始算
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
					System.out.println("不好意思，你输入的学生信息不存在，请重新输入！");
					return;
				}
				
			}
			//删除学生的实现
			private static void deleteStudent (ArrayList<Student> arr) {
				Scanner sc = new Scanner (System.in);
				System.out.println("请输入你要删除的学生ID:");
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
					System.out.println("删除学生成功！");
				}else {
					System.out.println("你输入的学生不存在，请重新你的选择。");
					return;
				}
				
			}
			//添加学生的实现  //  通过学号去检查学生是否存在
			private static void addStudent(ArrayList<Student> arr) throws IOException, SQLException {
				Scanner sc = new Scanner (System.in);
				String id ;
				while(true) {
					System.out.println("请输入你要新增的学号：");
					id = sc.nextLine();
					
					boolean flag =false;
					for(int x = 0 ; x<arr.size();x++) {
						Student s = arr.get(x);
						if (s.getId().equals(id)) {
							flag = true;	
						}
					}if (flag==true) {
						System.out.println("你输入的学号已经被占用请重新输入：");
						
				}else {
					break;
				}
			}
				System.out.println("请输入新的姓名：");
				String name = sc.nextLine();
				System.out.println("请输入新的年龄：");
				String age = sc.nextLine();
				//System.out.println("请输入新的学号：");
				String newid = id;												//这里不用再输入，直接赋值即可。
				//System.out.println("请输入新的性别：");
				System.out.println("请输入新的地址：");
				String address = sc.nextLine();
				Student s = new Student();
				s.setAddress(address);
				s.setName(name);
				s.setAge(age);
				s.setId(newid);
				arr.add(s);
				ArrayListToFile(arr);
				System.out.println("添加学生信息成功！");
				
			Connection conn  =  DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","admin");
				//System.out.println(conn);
			String sql = "insert into student (id,name,age,address,regtime) values (?,?,?,?,?)";		//?占位符 从1开始算
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
						 System.out.println("对不起，目前没有学生信息供你查看！");
						 return;
					 }
					 System.out.println("学号\t\t 姓名\t年龄\t\t地址");
					 System.out.println("--------------------------------------");
					 for(int x= 0 ; x<arr.size();x++) {
						 Student s = arr.get(x);
						 System.out.println(s.getId()+"\t\t"+s.getName()+"\t\t"+s.getAge()+"\t\t"+s.getAddress());
					 }
					 
			}
			
			public static void ArrayListToFile(ArrayList <Student> arr) throws IOException{
				 BufferedWriter bw = new BufferedWriter(new FileWriter("Student.txt"));
				 //遍历 得到元素 写到txt
				 for(int x = 0 ; x<arr.size();x++) {
					 Student s = arr.get(x);
					 bw.write(""+s.getName()+""+s.getAge()+s.getAddress()+s.getId());
					 bw.close();
				 }
			}
			
			
}
