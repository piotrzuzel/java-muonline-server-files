# Introduction #

You need the following tools to build and run the emulator:
|Package|Home Page| Download Page| Docs |Description |
|:------|:--------|:-------------|:-----|:-----------|
|Postgresql|[http:\\www.postgresql.org link]| [link](http://www.postgresql.org/download/) | [link](http://www.postgresql.org/docs/)|Database    |
|Java SE| [link](http://java.sun.com) | [link](http://java.sun.com/javase/downloads/index.jsp) |      |Java SE Development Kit|
|NetBeans| [link ](http://www.netbeans.org/)| [link](http://www.netbeans.org/downloads/) | [link](http://www.netbeans.org/kb/) | IDE to develop and build project. SVN client included. |
|Client 99b+|Any MU 99b+ client|              |      |            |
|Main   |         | [link](http://code.google.com/p/java-muonline-server-files/downloads/list) |      |Modified main.exe to connect|

Notes:
  * Alternatively, you can download the  [Java SE-NetBeans cobundle](http://java.sun.com/javase/downloads/widget/jdk_netbeans.jsp).
  * Experienced users can build the project without NetBeans.

# Setting up the tool chain #
Download and install all the above tools.

## Get the source code ##
Launch the NetBeans IDE and navigate to Team > Subversion > Checkout menu. If you do not have a SVN client installed, Netbeans will prompt you with a message offering to automatically download and install one for you (Netbeans will require a restart).
In the checkout window, enter the path _http://java-muonline-server-files.googlecode.com/svn/trunk/_ and make sure you check the **Anonymous** box before proceeding further. Complete the operation and Netbeans will ask to open the downloaded project, click yes.


## Create Database ##
After the Postgresql installation, a set of tools are available via command line. To set up the database, you need to use the **mu\_online.sql** included in the project you have checked out, and follow these steps:
```
cmd
drobdb mu_online -U postgres [enter]
createdb mu_online -U postgres [enter]
psql -U postgres mu_online < mu_online.sql [enter]
```
Note: After opening the command-line window, navigate to where the **mu\_online.sql** file is, or enter its full path in the commands above.

You should now have a working database which you can explore with the user-friendly **pgAdmin III** tool that ships with the Postgresql installation kit.

# How to use it #
## Compile and build ##
In Netbeans, go to the _Package Explorer_, right click on the project and select _Build_.
This will automatically create a **java-muonline-server-files.jar** file in the _project\_folder\dist_ directory.

## Configure and run ##
The server needs a set of data and configuration files. In case these files are not available in the _dist_ folder where the emulator was built, they need to be copied manually from the _project\_folder\src_ directory, specifically:
  * The **GameServer.conf** configuration file.
  * The **item.txt** file which contains all the items in game.
  * The **maps** folder which contains terrain data.

To run the server, change the current directory to distribution folder and launch the emulator as described below:
```
cd project_folder\dest
java -jar "java-muonline-server-files.jar"
```

## Play ##
If you plan on testing the server locally, make sure you have [setup loopback interface adapter](http://www.windowsnetworking.com/articles_tutorials/Install-Microsoft-Loopback-adapter-Windows-XP.html) at 128.0.0.1, because the MU client will not establish a connection to 127.0.0.1. Alternatively, you can use your WAN or LAN IP and skip the adapter installation. Launch the client with the following parameters:
```
main.exe "connect /u128.0.0.1 /p55901"
```