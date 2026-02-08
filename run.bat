@echo off
@chcp 65001 > nul
if not exist bin mkdir bin
dir /s /B src\*.java > sources.txt
javac -d bin -cp "lib/gson-2.8.9.jar" @sources.txt
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
java -cp "bin;lib/gson-2.8.9.jar" Main
pause
