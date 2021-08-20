# Readme 

# Packaging
The main class is Main.java

Packaging for classes:
 - main.controller
 - main.model
 - main.ui

## How to clone the project using intelliJIDEA and RUN the application
1- Download IntelliJ IDEA Ultimate Version (You had to apply for student license)

2- Open IntelliJ IDEA, select "File" from the top menu, select "New" and select "Project from Version Control"  

3- Copy your Github classroom repository and paste into URL, click on "Clone".
 Your project will be cloned and open in your IntelliJ IDEA window.
 
 However, you still need to add the SQLite jar file to your project so you can have access to your database. Follow next steps for adding the Jar file:
 
1- Download the SQLite JDBC jar file from week 7 Canvas module.

2- In your project under project root, make a new directory called lib and move the jar file into lib folder

3- Open IntelliJ IDEA, click on "File", open "Project Structure"

4- Under "Project Setting", select "Libraries"

5- Click + button, choose Java, and navigate to your project folder, then Lib folder, choose "sqlite-jdbc-3.34.0.jar", and click on "open"

6- Click on Apply and then OK to close the window

Now you are ready to Run the Application.

Simply right click on Main.java and choose Run.
Congratulations!

Login info:

Username: admin

Password: admin

Login info:

Username: employee	

Password: employee


## Prepare other content

Project: Hotdesking System using MVC, Java

This project is about a booking table system application where the users are employee and admin and each user can book a table
only once untill their current or previous booking has been accepted and checked in or cancelled b that user. The admin of the system
is allowed to perform higher task compared to the employee. admin can cancel or reject a booking and when admin books a table their table gets automatically accepted
as they are admin and has more privillege. As an admin you can also add,update,delete other admin or user. You can also generate 
a report for booking which shows previoous and current booking as well who checkedin and who didnt, and employee report shows all employee 
informatin such as their username, id, name, lastname.

	
- Table of content
	- Login 
	- Register 
	- Employee
		-Mange Booking -> cancel/checkin once accepted by admin 
	- Admin
		-Manage Book -> confirm/Reject booking made by employee, Cancel/Checkin booking made by the specific admin
		-Manage Account-> Add user, update user, delete user
		-Desk detail -> show desk detail, such as pending, accepted, cancelled, checkin bookings
		-Report-> generate report for booking and employee.

The intitial design found in wireframe folder has changed as while developing the application variety of different technique 
was discovered. Singleton design pattern was used for retrieiving and accessing user information between different scenes.
Also a retrieveModel is used for retrieving all necessary data from database for employee(RetrieveUserDetailModel.java) and similarly a 
retrieving model for booking (BookingModel.java) is also used for retrieving necessary data from book table for different situations,
such as get table id using status or username, Or check status depending on given a status or a table,
depedning on such situations different method is used. tableview of javafx is used for checking desk details, or managing book or account
for users. for updating a user you need to click on the specific user and click on the specific coloumn you want to make changes to and
press enter and then press the update button to update for that specific user.

Note: Wireframe design can be found in the wireframe forlder

This following application can be further extended to include a lockdown end time where after that time the table can go 
back to normal or social disntacing condition depending on the user/admin choice



