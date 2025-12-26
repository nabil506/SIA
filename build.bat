@echo off
setlocal

REM =====================================================
REM   MULAI SCRIPT
REM =====================================================
cls
REM =====================================================
REM   Banner
REM =====================================================
echo ======================================================
echo.
echo           F R A M E W O R K - B E T A
echo             J A V A - C L I / W E B
echo               By TryCatch Wariors
echo.
echo ======================================================

echo 1. WEB   - Compile + Restart Tomcat
echo 2. CLI   - Compile & Run Main.java
echo ------------------------------------------------------
echo.

set "choice="
set /p "choice=Pilih mode (1/2): "
echo.

REM =====================================================
REM   VALIDASI INPUT
REM =====================================================
if "%choice%"=="" (
    echo [ERROR] Tidak ada pilihan yang dimasukkan.
    goto end
)
if "%choice%" neq "1" if "%choice%" neq "2" (
    echo [ERROR] Pilihan tidak valid. Harap masukkan 1 atau 2.
    goto end
)

REM =====================================================
REM   LOGIKA UTAMA
REM =====================================================
if "%choice%"=="1" goto web_mode
if "%choice%"=="2" goto cli_mode


:web_mode
    echo [INFO] Mode WEB dipilih. Memulai kompilasi...
    dir /s /b *.java > sources.txt
    javac -cp "WEB-INF\lib\*" -d WEB-INF\classes @sources.txt

    if %errorlevel% equ 0 (
        echo [OK] Kompilasi berhasil.
        echo ------------------------------------------------------
        echo [INFO] Merestart server Tomcat...
        call ..\..\bin\shutdown.bat
        call ..\..\bin\startup.bat
    ) else (
        echo [ERROR] Kompilasi gagal. Silakan cek error di atas.
    )
    if exist sources.txt del sources.txt
    goto end

:cli_mode
    echo [INFO] Mode CLI dipilih. Memulai kompilasi dan eksekusi...
    dir /s /b *.java > sources.txt
    javac -cp "WEB-INF\lib\*" -d WEB-INF\classes @sources.txt

    if %errorlevel% equ 0 (
        echo [OK] Kompilasi berhasil.
        echo ------------------------------------------------------
        echo [INFO] Menjalankan Main.java...
        java -cp "WEB-INF\classes;WEB-INF\lib\*" Main
    ) else (
        echo [ERROR] Kompilasi gagal. Silakan cek error di atas.
    )
    if exist sources.txt del sources.txt
    goto end

:end
echo.
pause
endlocal
