# Monster Hunter Freedom (PSP) Database 
Gameplay assistant for playing Monster Hunter Freedom game on Playstation Portable.
It allows you to:
 - browse items available in the game
 - enter availalbe felyne kitchen ingridients and find out what recipies you can compose, focusing on positive buffs
 - browse quests in the game
 - browse which quests have the highest ammount of honey harvestable
 - browse armors available in the game
 - browse armor sets which provide specific skill when equiped
 - create and manage your tasks to acomplish while playing game
 - history of exchanges made with 
 
 ... more features to be implemented

Monster Hunter Freedom wiki page: https://en.wikipedia.org/wiki/Monster_Hunter_Freedom
Playstation Portable wiki page: https://en.wikipedia.org/wiki/PlayStation_Portable


Compile command, using Java 17

javac  -d .\compiled\WEB-INF\classes .\src\main\java\sandura\mhdatabase\Main.java .\src\main\java\sandura\mhdatabase\item\*.java  .\src\main\java\sandura\mhdatabase\k
itchen\*.java .\src\main\java\sandura\mhdatabase\kitchen\ingredient\*.java  .\src\main\java\sandura\mhdatabase\logging\*.java  .\src\main\java\sandura\mhdatabase\servlet\*.java -classpath C:\Users\dkuwa\Documents\apache-tomcat-10.1.
23-windows-x64\apache-tomcat-10.1.23\lib\servlet-api.jar  -verbose


Package class files into a jar:

1. Navigate to compiled directory
2. run command:
jar cvf MHDB.war *