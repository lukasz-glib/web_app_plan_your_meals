# Web-app_Plan_Your_Meals
Web application Plan Your Meals (with account registration) to creating your own diet schedule ! 

This Web app enables:
- registration
- logging
- addition, modification and removing your meal recipes
- displaying recipes list
- addition, modification and removing your meal schedule
- reviewing and blocking users by admin
- meal recipes management by logging on users

## Data to connection with database:

File is located in project directory **src/main/webapp/META-INF**.

`Modify data concerning connection with database`

### If you have this error:
````
javax.naming.NameNotFoundException: Name [jdbc/scrumlab] is not bound in this Context. Unable to find [jdbc].
````
do following steps:

Choose from menu File –> Project Structure...

In window click on the left side Facets –> **Web**.

In window which will appear choose **Add Application Server specific descriptor...**.

![Screenshot](readme-img/context_03.png)

Choose from server list **Tomcat** and accept **OK**.

![Screenshot](readme-img/context_04.png)

On list will appear a file **Tomcat Context Descriptor**. Confirm using **OK**.

![Screenshot](readme-img/context_05.png)
