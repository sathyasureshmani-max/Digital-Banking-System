@echo off
if not exist out mkdir out

echo Compiling Digital Banking System...

dir /s /b src\main\java\*.java > sources.txt

javac -d out @sources.txt

if errorlevel 1 (
    echo.
    echo Compilation failed.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo Starting Digital Banking System...
echo.

java -cp out main.BankingApplication

pause