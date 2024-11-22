@echo off
setlocal enabledelayedexpansion

:: Check if a version argument is provided
if "%~1"=="" (
    echo Usage: release.bat ^<version^>
    echo Example: release.bat v1.0.0
    exit /b 1
)

:: Set the version from the first argument
set VERSION=%~1

:: Confirm the version
echo Creating release for version: %VERSION%
echo Press Ctrl+C to cancel or any key to continue...
pause >nul

:: Create a Git tag
git tag %VERSION%

:: Push the tag to the repository
git push origin %VERSION%

:: Done
echo Tag %VERSION% has been created and pushed.
