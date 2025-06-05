@echo off
chcp 65001 > nul
set JAVA_OPTS=-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
set JAVA_PATH=C:\Users\stefa\.jdks\openjdk-24.0.1\bin\java.exe
set AGENT_PATH=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.2.1\lib\idea_rt.jar
set CLASSPATH=C:\Users\stefa\IdeaProjects\ScrumEscapeGame\src\ScrumGame\target\classes;C:\Users\stefa\.m2\repository\org\jline\jline\3.25.0\jline-3.25.0.jar

"%JAVA_PATH%" --enable-preview "-javaagent:%AGENT_PATH%=52856:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2024.2.1\bin" %JAVA_OPTS% -classpath "%CLASSPATH%" org.game.Main

pause