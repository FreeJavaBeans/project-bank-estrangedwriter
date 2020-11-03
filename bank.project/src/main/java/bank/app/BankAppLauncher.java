package bank.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import bank.resources.ConnectionUtility;
import bank.resources.UserAuthenticationDAO;

import bank.UserInterface.*;

public class BankAppLauncher {
	
	public static void main(String[] args) {
//		ConnectionUtility cu = ConnectionUtility.getConnectionUtility();
//		Connection conn = cu.getConnection();
		
		UserInterfaceLogin UI = new UserInterfaceLogin();
		System.out.println("============================================");
		System.out.println("Hello and welcome to the banking application");		
		System.out.println("============================================\n");
		
		UI.LoginMenu();
		
	}
}