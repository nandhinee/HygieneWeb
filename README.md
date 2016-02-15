# HygieneWeb
About the project:

The project has been developed as a web application. The user has to give any zip code pertaining to Chicago for which he needs to find the top ten hygienic restaurants. Based on the zip code, the top ten restaurants in that locality is displayed in a table with the first one being the most hygienic one. The locations of those restaurants are also shown in a google map.

The application was developed in a map reduce framework(Hadoop)

Before running the project, make sure that the system has the following
1.	Apache Tomcat Server 8
2.	MapReduce framework (Hadoop)

To preprocess the input data (optional)

1. Place the input file "input.txt" into the workspace and give its path ("input/input.txt") in the command line
2. The processed file will be generated in the workspace as output.txt which the required input file for our project


Instructions to run the project:
1. Unzip the file HygieneWeb into the workspace.
2. In the hadoop home directory create a folder called input and place the preprocessed file into it.
3. Create a folder called zip inside hadoopLocation/bin/hadoop 
3. Go to the file HygieneWeb/Java Resources/src/FoodInspec.java and change the following line 
(line number 50)
String hadoopLocation = "/home/nandhinee/Softwares/hadoop-1.0.3" to your hadoop home directory
(line number 51)
String desktop = "/home/nandhinee/Desktop"; to your Desktop path
4. Run the project on the Tomcat server
5. The index.jsp page will be displayed and enter a valid zipcode of Chicago.

Note: When running the project for the second time please execute the following commands and delete final.txt and temp.txt from your desktop
sudo ./bin/hadoop fs -rmr zip/temp.txt
sudo ./bin/hadoop fs -rmr out*
sudo ./bin/hadoop fs -rmr final.txt
 

