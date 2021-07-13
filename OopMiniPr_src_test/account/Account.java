package account;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Account{

public static void main(String[] args) throws Exception {
	// 계좌 이체 프로그램 제작 실습
	
	Scanner s=new Scanner(System.in);
	boolean run=true;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "hr";
	String password = "tiger";

	Class.forName(driver);
	Connection con=DriverManager.getConnection(url, user, password);
	
	while(run) {
		System.out.println("----------------------------------");
		System.out.println("1.계좌생성|2.계좌목록|3.예금|4.출금|5.종료");
		//insert update update
		System.out.println("----------------------------------");
		
		System.out.print("선택>");
		int menu=s.nextInt();
		
		
		switch(menu) {
		case 1://계좌생성
			System.out.println("-----------------");
			System.out.println("계좌생성");
			System.out.println("-----------------");
			
			System.out.print("계좌번호:");
			String ano=s.next();
			System.out.print("계좌주:");
			String aname=s.next();
			System.out.print("초기입금액:");
			int balance=s.nextInt();
			
			String sql="insert into tbl_account (ano, aname, balance) values (?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setString(1, ano);
			ps.setString(2, aname);
			ps.setInt(3, balance);
			ps.execute();
			System.out.println("결과: 계좌가 생성되었습니다.");
			System.out.println();
			break;
			
		case 2://계좌목록
			sql="select * from tbl_account";
			ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			System.out.println("-----------------");
			System.out.println("계좌번호\t계좌주\t잔액");
			System.out.println("-----------------");
			System.out.println();
			
			while(rs.next()) {
				System.out.println();
				ano=rs.getString("ano");
				aname=rs.getString("aname");
				balance=rs.getInt("balance");
				System.out.print(ano+"\t");
				System.out.print(aname+"\t");
				System.out.print(balance+"\t");
				
				System.out.println();
			}//while
			System.out.println();
			break;
		case 3://예금
			System.out.print("계좌번호>");
			ano=s.next();
			sql="select * from tbl_account where ano=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, ano);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				aname=rs.getString("aname");
				balance=rs.getInt("balance");
				System.out.println("계좌주:"+aname);
				System.out.println("잔액:"+balance);
				System.out.print("입금액>");
				int input=s.nextInt();
				
				sql="update tbl_account set balance=balance+? where ano=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, input);
				ps.setString(2, ano);
				ps.execute();
				
				System.out.println("결과: 예금이 성공되었습니다.");
				
			}else {
				System.out.println("입력된 계좌가 없습니다.");
			}
			break;
		case 4://출금
			System.out.print("계좌번호>");
			ano=s.next();
			sql="select * from tbl_account where ano=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, ano);
			rs=ps.executeQuery();
			//test
			if(rs.next()) {
				aname=rs.getString("aname");
				balance=rs.getInt("balance");
				System.out.println("계좌주: "+aname);
				System.out.println("잔액: "+balance);
				System.out.println("출금액>");
				int output=s.nextInt();
				
				sql="update tbl_account set balance=balance-? where ano=?";
				ps=con.prepareStatement(sql);
				ps.setInt(1, output);
				ps.setString(2, ano);
				ps.execute();
				
				System.out.println("결과: 출금이 성공되었습니다.");
			}else {
				System.out.println("계좌를 잘못 입력하셨습니다."); 
			}
			break;
		case 5://종료
			run=false;
			System.out.println("프로그램 종료");
			
			}//switch
		}//while
	}
}