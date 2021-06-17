import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class TestClient {
	private static final int commitFrequ = 100;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("start Test Client");
		try {	
			TestClient client = new TestClient();
			Connection con = client.ConnectDatabase();
			con.setAutoCommit(false);
			
			int AnzahlLieferanten = 1000;
			int AnzahlTeile = 1000;
			client.loadTeil(con, AnzahlTeile);
			client.loadLieferant(con, AnzahlLieferanten);
			client.loadAuftrag(con, AnzahlLieferanten, AnzahlTeile);
			System.out.println("end");

			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Connection ConnectDatabase() {
		Connection con = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			StringBuilder url = new StringBuilder();
			url.append("jdbc:mysql://localhost:3306/MATERIALBESCHAFFUNG?")
			   .append("user=root")
			   .append("&password=rootadmin")
			   .append("&serverTimezone=UTC")
			   .append("&allowPublicKeyRetrieval=true")
			   .append("");
			
			con = DriverManager.getConnection(url.toString());
			
			System.out.println("database is connected");
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	void selectTest(Connection con) {
		try {
			System.out.println("Eingabe Lieferantennummer: ");
			Scanner scan = new Scanner(System.in);
			int Lnr = Integer.parseInt(scan.nextLine());
			scan.close();
	
			String sqlStmt = "select * from Lieferant where LNR = ?";
			PreparedStatement pstmt;
			
			pstmt = con.prepareStatement(sqlStmt);
			pstmt.setInt(1, Lnr);
			
			ResultSet rs = pstmt.executeQuery();
			String str = pstmt.toString();
			System.out.println(str);
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			
			System.out.println("fetch started");
			while(rs.next()) {
				for(int iCol=1;iCol<=columnCount;iCol++) {
					byte [] byteChar = rs.getBytes(iCol);
					
					String s = "<null>";
					if( byteChar != null ) {
						s = new String(byteChar, "windows-1252");
						// String s = new String(rs.getBytes(iCol), "ISO-8859-1");
					}
					System.out.println(meta.getColumnLabel(iCol) + " " + s);	
				}
			}
		} catch (SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fetch finsished");

	}
	
	void loadTeil(Connection con, int AnzahlTeile ) {
		
		try {
			String sqlInsertTeilStmt = "INSERT INTO TEIL " 
				+ "(TNR, TNAME, FARBE, GEWICHT, ORT) " 
				+ "VALUES (?, ?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlInsertTeilStmt);
			
			String TeilName = "TeilName";
			String Farbe = "Farbe";
			Double Gewicht = 100.00;
			String Ort = "Ort";
			pstmt.setString(2, TeilName);
			pstmt.setString(3, Farbe);
			pstmt.setDouble(4, Gewicht);
			pstmt.setString(5, Ort);
			
			
			int Count = 0;
			for(;Count < AnzahlTeile; Count++) {
				int TNR = Count + 100;
				pstmt.setInt(1, TNR);
				pstmt.execute();
				
				if( Count % commitFrequ == 0 ) {
					con.commit();
					System.out.println("loadTeil con.commit()" + Count);
				}
				
				if( Count % 1000 == 0 ) {
					System.out.println("loadTeil COUNT="+Count);
				}
			}
			con.commit();
			System.out.println("COUNT="+Count);
			
		} catch (SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void loadLieferant(Connection con, int AnzahlLieferanten ) {
		
		try {
			String sqlInsertTeilStmt = "INSERT INTO LIEFERANT (LNR, LNAME, LSTATUS, ORT) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlInsertTeilStmt);
			
			String LieferantName = "LieferantName";
			String LieferantStatus = "LieferantStatus";
			String LieferantOrt = "LieferantOrt";
			pstmt.setString(2, LieferantName);
			pstmt.setString(3, LieferantStatus);
			pstmt.setString(4, LieferantOrt);
			
			
			int Count = 0;
			for(;Count < AnzahlLieferanten; Count++) {
				int LNR = Count + 100;
				pstmt.setInt(1, LNR);
				pstmt.execute();
				
				if( Count % commitFrequ == 0 ) {
					con.commit();
					System.out.println("loadLieferant() con.commit()" + Count);
				}
				
				if( Count % 1000 == 0 ) {
					System.out.println("COUNT="+Count);
				}
			}
			System.out.println("loadLieferant COUNT="+Count);
			con.commit();
			
		} catch (SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void loadAuftrag(Connection con, int AnzahlLieferanten, int AnzahlTeile ) {
			
		try {
			String sqlInsertTeilStmt = "INSERT INTO AUFTRAG (LNR, TNR, MENGE) VALUES (?, ?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sqlInsertTeilStmt);
			
			int Menge = 100;			
			pstmt.setInt(3, Menge);
			
			int CountLieferant = 0;
			for(;CountLieferant < AnzahlLieferanten; CountLieferant++) {
				int LNR = CountLieferant + 100;
				pstmt.setInt(1, LNR);
				
				int CountTeil = 0;
				for(;CountTeil < AnzahlTeile;CountTeil++) {
					int TNR = CountTeil + 100;
					pstmt.setInt(2, TNR);
					pstmt.execute();
				}
				
				if( CountLieferant % 1000 == 0 ) {
					System.out.println("loadAuftrag: CountLieferant =" + CountLieferant);
				}
				con.commit();
				System.out.println("loadAuftrag() con.commit()");
			}
			System.out.println("loadAuftrag: CountLieferant =" + CountLieferant);
			con.commit();
			System.out.println("loadAuftrag() con.commit()");

			
		} catch (SQLException e ) {
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		    
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

