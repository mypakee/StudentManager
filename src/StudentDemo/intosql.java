package StudentDemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import com.mysql.jdbc.PreparedStatement;

public class intosql {
		public static void main(String[] args) {
			
			String a = "����";
			String b = "����";
			int  age = 25;
			String id = "001";
			//����������
			try {
				Class.forName("com.mysql.jdbc.Driver");
				long start = System.currentTimeMillis();
				//��������
				Connection conn  =  DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","admin");
					//System.out.println(conn);
				String sql = "insert into student (id,name,age,address) values (?,?,?,?)";		//?ռλ�� ��1��ʼ��
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setObject(1, id);
				ps.setObject(2, a);
				ps.setObject(3, age);
				ps.setObject(4, b);
				ps.execute();
					
				long end = System.currentTimeMillis();
				//System.out.println("��ʱ��"+(end - start)+"ms");
					
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
