
import static java.lang.System.out;

import java.sql.*;
import java.util.Scanner;

/**
 * Framework for JDBC, Referred to JDBC official website from MySQL
 * 
 * @author Yang Shi
 *
 */
public class jdbcDemo {
	/**
	 * The implementation print function
	 * 
	 * @author Lei Xian
	 *  
	 * @param stmt statement1 for query 1
	 */
	public static void stmt1(Statement stmt) {
		ResultSet rs;
		try {
			System.out.println("Desired Result of Question 1:\n");
			// Printing the name of the employee without duplications made by inner join.
			rs = stmt.executeQuery(
					"select d.dept_name,de.dept_no,COUNT(*) AS emp_count FROM dept_emp as de join departments as d on de.dept_no = d.dept_no GROUP BY d.dept_name ORDER BY emp_count  ASC LIMIT 15");

			print(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author Rana Alshemali
	 *
	 * @param stmt statement for query 2
	 *            
	 */
	public static void stmt2(Statement stmt) {
		ResultSet rs;
		try {
			System.out.println("Desired Result of Question 2:\n");
			// List department(s) with maximum ratio of average female salaries to average
			// men salaries
			rs = stmt.executeQuery("select s1.dept_name,s1.dept_no,s1.f_sal/s2.m_sal as ration from ( "
					+ "(select dept_name, d.dept_no, avg(salary) as f_sal from employees e "
					+ "join dept_emp de on e.emp_no = de.emp_no " + "join departments d on d.dept_no= de.dept_no "
					+ "join salaries s on de.to_date = s.to_date and s.emp_no = e.emp_no " + "where e.gender='f' "
					+ "group by dept_name " + "order by dept_name asc)  s1, "
					+ "(select dept_name,d.dept_no, avg(salary) as m_sal from employees e "
					+ "join dept_emp de on e.emp_no = de.emp_no " + "join departments d on d.dept_no= de.dept_no "
					+ "join salaries s on de.to_date = s.to_date and s.emp_no = e.emp_no " + "where e.gender='m' "
					+ "group by dept_name " + "order by dept_name asc)  s2 " + ") where s1.dept_name =  s2.dept_name "
					+ "order by ration desc limit 15");
			// print the resulted query
			print(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The implementation of Question 3.
	 * 
	 * @author Lei Xian
	 *
	 * @param stmt statement for query 3
	 * 
	 */
	public static void stmt3(Statement stmt) {
		ResultSet rs;
		try {
			System.out.println("Desired Result of Question 3:\n");
			// Printing the name of the employee without duplications made by inner join.
			rs = stmt.executeQuery("select first_name,last_name,if(to_date<curdate(),timestampdiff(hour,from_date,to_date),timestampdiff(hour,from_date,curdate())) as duration "
					+ "from employees join dept_manager on employees.emp_no=dept_manager.emp_no "
				
					+ "order by duration desc limit 15");

			print(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The implementation of Question 4.
	 * 
	 * @author Rana Alshemali
	 *
	 * @param stmt statement for query 4
	 *            
	 */
	public static void stmt4(Statement stmt) {
		ResultSet rs;

		try {
			System.out.println("Desired Result of Question 4:\n");
			// Printing the name of the employee without duplications made by inner join.
			rs = stmt.executeQuery(

					"select  d.dept_name, floor(year(e.birth_date)/10)*10  as decade, "
							+ "count(e.emp_no) as emp_count,avg(s.salary) as avg_salary " 
							+ "from employees e "
							+ "join dept_emp de on e.emp_no =de.emp_no and de.to_date <= '9999-12-31'"
							+ "join departments d on d.dept_no = de.dept_no "
							+ "join salaries s on de.to_date = s.to_date and s.emp_no =de.emp_no  and s.to_date <= '9999-12-31'"
							+ "group by d.dept_name , (floor(year(e.birth_date)/10)*10)" 
							+ "order by d.dept_name asc limit 15"

			);

			// print the resulted query
			print(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The implementation of Question 5.
	 * 
	 * @author Yang Shi
	 *
	 * @param stmt statement for query 5
	 *            
	 */
	public static void stmt5(Statement stmt) {
		ResultSet rs;
		try {
			System.out.println("Desired Result of Question 5:\n");
			// Printing the name of the employee without duplications made by inner join.
			rs = stmt.executeQuery("Select distinct first_name,last_name "
					+ "from ((employees inner join titles on employees.emp_no = titles.emp_no) "
					+ "inner join salaries on employees.emp_no = salaries.emp_no) " + "where gender='F' "
					+ "and birth_date < '1990-01-01' " + "and salary > 80000 " + "and title = 'Manager'" + "limit 15");

			print(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The implementation print function
	 * 
	 * @author Rana Alshemali
	 *
	 * @param rs resulted from the query
	 * 
	 * @throws IllegalArgumentException     
	 *         
	 */
	public static void print(ResultSet rs) throws SQLException {
		// Retrieve the properties of the columns ResultSet
		ResultSetMetaData rs_md = rs.getMetaData();
		// get number of columns
		int columnsNumber = rs_md.getColumnCount();
		// get number of rows
		rs.last();
		int size = rs.getRow();
		rs.beforeFirst();
		for (int i = 0; i < columnsNumber; i++)
			out.print("-------------------");
		out.println(" ");
		for (int j = 1; j <= columnsNumber; j++)
			// print column names
			System.out.format("%18s", rs_md.getColumnName(j));
		out.println(" ");
		for (int i = 0; i < columnsNumber; i++)
			out.print("-------------------");
		out.println(" ");
		// System.out.println(rsmd.getColumnName(j)+" ");
		// for each row
		for (int i = 0; i < size || i < 100; i++) {
			if (rs.next()) {
				// print every column
				for (int j = 1; j <= columnsNumber; j++)
					System.out.format("%18s", rs.getString(j));
			} else if (i == 0) {
				System.out.println(" Not exsiting satisfying result!");
			} else
				break;
			System.out.println(" ");
		}
		System.out.println("\n\n____________________( Query Finished )____________________\n\n");

	}

	public static void main(String[] args) {
		Connection conn = null;
		try {
			// 1 Import Drivers
			Class.forName("com.mysql.jdbc.Driver");
			// 2 Connect to DB
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees?"
					+ "user=root&password=########&useUnicode=true&characterEncoding=UTF8&useSSL=false");
			// 3 Claim statements
			Statement stmt = conn.createStatement();
			while (true) {
				// 4 Execution of statements
				System.out.println("1. Query 1: List departments with minimum number of employees");
				System.out.println(
						"2. Query 2: List departments with maximum ratio of average female salaries to average men salaries");
				System.out.println("3. Query 3: List managers who holds office for the longest duratiuon");
				System.out.println(
						"4. Query 4: For each department, list number of employees born in each decade and their average salaries");
				System.out.println(
						"5. Query 5: List employees, who are female, born before Jan 1, 1990, makes more than 80K annually and hold a manager position");
				Scanner input = new Scanner(System.in);
				String s = input.nextLine();
				switch (s) {
				case "1":
					stmt1(stmt); // Question 1
					break;
				case "2":
					stmt2(stmt); // Question 2
					break;
				case "3":
					stmt3(stmt); // Question 3
					break;
				case "4":
					stmt4(stmt); // Question 4
					break;
				case "5":
					stmt5(stmt); // Question 5
					break;
				default:
					System.out.println("Query not existed, please select again!");

				}
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5 Exit and close
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
