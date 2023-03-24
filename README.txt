=============================
=== Appointment Scheduler ===
=============================

-----------------------------

== BASIC INFORMATION ==

App Title: "Appointment Scheduler"

Manage appointments and customers in a database with a GUI-based Java application. 
Customers and appointments may be added, updated, or removed through this application. 
The appointments view in this application provides a current month and current week filter (Sunday through Saturday) for the appointments as well by switching the respective tabs. 
There is a reports screen to view three different types of reports in the UI. Program also records any log in attempts into a text file.
The time format for this app is in the 24-hour format.
The login form provides support for the French language.

Author: Leanna Garner
Contact: lgarn67@wgu.edu
App Version: v1.0
Date: 2023 March 23

-----------------------------

== VERSION INFORMATION ==

> IDE Version Number: IntelliJ IDEA Community Edition 2021.1.3
> JDK Version: Java SE 17.0.6
> JavaFX Version: OpenJFX 17.0.2
	> SceneBuilder uses version 19 of JavaFX and will not have issues using version 19. The FXML files have been set to use version 17.0.2.
> MySQL Connector Driver Version: mysql-connector-java-8.0.25

-----------------------------

== RUNNING THE PROGRAM ==

1. 	Please ensure JavaFX and the MySQL Connector Driver have been added to Project Settings > Modules > Dependencies. 
	If they are not there (if the list on that screen does not contain them), please add them. This uses IntelliJ.
		- On the Dependencies screen, click the plus sign and click "Library". 
		  Select everything under the the Project Libraries in the "Choose Libraries" window that pops up.
			- The program cannot be run if the JavaFX modules are not found.

		- On the Dependencies screen, click the plus sign and click "JARs or Directories" and navigate to the folder containing the MySQL connector, 
		  in this case, version 8.0.25. Click on the .jar file titled "mysql-connector-java-8.0.25", then press okay.
			- The program cannot connect to the database if the driver is not in the project's list of dependencies
			  and will produce an error in the UI if the application cannot connect to the database.

2. 	Once the JavaFX and database driver has been added to dependencies, you may log in. 
	Enter the username and password and press the log in button. 
	Acceptable combinations of username and password are:
		- admin / admin
		- test / test

3. 	Upon successful login, you are sent to the customer view screen. 
	The sidebar on the left has the views for the customer, appointment, and reports view screens, as well as a button to exit the app (and will log you out).

4. 	The Customer and Appointment screens both have add, edit, and remove buttons. 
	Clicking add on either of the screens will send you to the form responsible for creating a new entry for an appointment or a customer; 
	all fields must be filled out correctly for it to be saved. 
	There are cancel buttons on both forms. 
	The edit forms will load the selected entry from the table and present a form with prepopulated values that can be edited. 
	The remove button will ask for a confirmation of delete and warn the user that when deleting a customer, 
	it will effectively delete the customer's associated appointments as well.

5. 	The Reports screen has three different reports to view; the contact schedule report requires selection of a contact before its table is populated.

-----------------------------

== THE ADDITIONAL REPORT ==

This report tallies the number of customers per first-level division, for all first-level divisions that have customers in them. 
It includes three columns: the number of customers, the name of the first-level division, and the country that the first-level division is in. 
By default, it is ordered by the division with the most customers to the one with the least.