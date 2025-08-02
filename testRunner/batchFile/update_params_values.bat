@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

:: Debug output
echo [DEBUG] BROWSER=%BROWSER%
echo [DEBUG] PARALLEL_EXECUTION=%PARALLEL_EXECUTION%
echo [DEBUG] MAX_PARALLEL_THREADS=%MAX_PARALLEL_THREADS%
echo [DEBUG] SUITE=%SUITE%

:: Set working directory
cd /d C:\Users\swap1\2025\March\OrangeHRMProject

:: Set file paths
set configFile=src\main\resources\config.properties
set pomFile=pom.xml

:: === Update config.properties ===
(
  for /F "usebackq tokens=1,* delims==" %%A in ("%configFile%") do (
    set "key=%%A"
    set "val=%%B"
    if /I "!key!"=="BROWSER" (
      echo BROWSER=%BROWSER%
    ) else if /I "!key!"=="PARALLEL_EXECUTION" (
      echo PARALLEL_EXECUTION=%PARALLEL_EXECUTION%
    ) else if /I "!key!"=="MAX_PARALLEL_THREADS" (
      echo MAX_PARALLEL_THREADS=%MAX_PARALLEL_THREADS%
    ) else (
      echo !key!=!val!
    )
  )
) > "%configFile%.tmp"

move /Y "%configFile%.tmp" "%configFile%" >nul
echo [INFO] config.properties updated

:: === Update pom.xml ===
(
    for /F "tokens=*" %%A in ('type "!pomFile!"') do (
        set "line=%%A"
        if "!line!"=="<suiteXmlFile>homePage</suiteXmlFile>" ( set "line=<suiteXmlFile>%SUITE%</suiteXmlFile>")
		if "!line!"=="<suiteXmlFile>loginPage</suiteXmlFile>" ( set "line=<suiteXmlFile>%SUITE%</suiteXmlFile>")
        echo !line!
    )
) > "!pomFile!.tmp"

move /Y "!pomFile!.tmp" "!pomFile!" >nul

echo [INFO] pom.xml updated

:: Run Maven
mvn clean test

ENDLOCAL
