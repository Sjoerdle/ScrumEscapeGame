@echo off

chcp 65001 > nul
set JAVA_OPTS=-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8
set JAVA_PATH=C:\Users\lunsh\.jdks\openjdk-24\bin\java.exe
set CLASSPATH=C:\Users\lunsh\Documents\GitHub\ScrumEscapeGame\src\ScrumGame\target\classes;C:\Users\lunsh\.m2\repository\org\jline\jline\3.25.0\jline-3.25.0.jar

"%JAVA_PATH%" --enable-preview %JAVA_OPTS% -classpath "%CLASSPATH%" org.game.Main

pause