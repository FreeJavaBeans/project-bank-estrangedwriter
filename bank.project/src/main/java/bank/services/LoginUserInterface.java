package bank.services;

import bank.util.ScannerUtility;
import java.util.Date;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;

import bank.resources.LoginDAO;

public class LoginUserInterface {
	public static Logger LOG = LogManager.getLogger("bankproject");
	Scanner scanner = ScannerUtility.getScanner();
	
	// main login menu
	public void LoginMenu() {
		
		char option = '\0';
		do {
			this.LoginOptions();
			System.out.print("Enter an option: ");
			option = scanner.next().charAt(0);		
			
			switch(option) {
				case 'A':
					this.LoginMethod(scanner);
					break;
					
				case 'B':
					this.NewCustomerAccount(scanner);
					break;
					
				case 'Q':
					break;
					
				default:
					System.out.println("Invalid option; please try again");
			}
		}while (option != 'Q');
		this.ExitService();
	}
	
	// Case A method to Login
	private void LoginMethod(Scanner scanner) {
		System.out.println("\n*****Log In*****");
		System.out.print("Enter a username: ");		
		String username = scanner.next().toLowerCase();
		System.out.print("Enter a password: ");
		String password = scanner.next();				
		// Creating new user session
		LoginDAO Login = new LoginDAO(username,password);
		System.out.println("\n*****Authenticating*****");
		System.out.println("........................");
		System.out.println("........................");
		// Testing for User Authentication
		boolean UserAuth = Login.Login();					
		// If Login Successful and account type is customer then return customer menu
		if ( (UserAuth == true) && (Login.getCurrentUser().isAccountType() == true)) {
			LOG.info("Customer username: " + username + " has logged in.");
			System.out.println("****Login successful****\n" );
			CustomerUserInterface UICustomer = new CustomerUserInterface(Login.getCurrentUser().getKeyID(), Login.getCurrentUser());
			UICustomer.CustomerMenu();
		}					
		// If Login Successful and account type is employee then return employee menu
		else if ( (UserAuth == true) && (Login.getCurrentUser().isAccountType() == false) ) {
			LOG.info("Employee username: " + username + " has logged in.");
			System.out.println("****Login successful****\n" );
			EmployeeUserInterface UIEmployee = new EmployeeUserInterface();
			UIEmployee.EmployeeMenu();
		}
		else 
			System.out.println("****Incorrect Username or Password, Login Failed****\n");
	}
	
	// Case B private method to accept user input for the Create New Customer Account form
	private void NewCustomerAccount(Scanner scanner) {
		System.out.println("\n*****Sign Up for an Account*****");
		System.out.print("Enter a username: ");
		String username1 = scanner.next().toLowerCase();
		if (username1.length() > 20 || username1.length() < 6) {
			System.out.println("\n**Username must be 6-20 characters in length.**\n");
			return;
		}
		System.out.print("Enter a password: ");
		String password1 = scanner.next();
		if (password1.length() > 20 || password1.length() < 6) {
			System.out.println("\n**Password must be 6-20 characters in length.**\n");
			return;
		}
		// creating new user authentication session
		LoginDAO CreateAccount = new LoginDAO(username1,password1);
		
		// if the user account is created successfully then add customer details
		if (CreateAccount.NewUserAccount() == true) { 
			System.out.println("\n*****Creating Customer Account*****\n");
			System.out.println("All fields required");
			System.out.print("Enter your first name: ");
			String firstname= scanner.next();
			System.out.print("Enter your last name: ");
			String lastname= scanner.next();
			System.out.print("Enter your email address: ");
			String email= scanner.next();
			System.out.print("Enter your home address: ");
			scanner.nextLine();
			String address = scanner.nextLine();
			Date date = new Date();  
			Timestamp ts=new Timestamp(date.getTime());  
      
			if (CreateAccount.NewCustomerAccount(firstname, lastname, email, address, ts) == true) { 
				System.out.println("****Account created successfully****\n");
			}
			else { 
				System.out.println("Error: Account with email address already exists.");
				System.out.println("Creating a new user account failed.\n");
			}
		}
		else {
			System.out.println("Error: Account with username already exists.");
			System.out.println("Creating a new user account failed.\n");
		}
	}
	
	// private method to display different Login Options
	private void LoginOptions() {
		System.out.println("****Login Screen****");
		System.out.println("Press 'A' to Log in");
		System.out.println("Press 'B' to create a new customer account");
		System.out.println("Press 'Q' to exit\n");
	}
	// private method that shows when user exits service
	private void ExitService() {
		System.out.println("***************");
		System.out.println("Thank you for using our services. Exiting Application");
		System.out.println("***************\n");
	}
}
