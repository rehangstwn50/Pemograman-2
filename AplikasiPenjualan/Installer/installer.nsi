!include "MUI2.nsh"
!include "LogicLib.nsh"

Name "Aplikasi Penjualan"
OutFile "Setup_AplikasiPenjualan.exe"

; ===== GANTI KE LOCALAPPDATA =====
InstallDir "$LOCALAPPDATA\AplikasiPenjualan"
InstallDirRegKey HKCU "Software\AplikasiPenjualan" "Install_Dir"

; Tidak perlu admin lagi karena pakai LOCALAPPDATA
RequestExecutionLevel user

BrandingText "Aplikasi Penjualan v1.0"

!define MUI_FINISHPAGE_RUN      "$INSTDIR\AplikasiPenjualan.exe"
!define MUI_FINISHPAGE_RUN_TEXT "Jalankan Aplikasi Penjualan sekarang"

!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_LANGUAGE "Indonesian"

Section "Install"

    SetOutPath "$INSTDIR"
    File "AplikasiPenjualan.exe"
    File "AplikasiPenjualan.jar"

    SetOutPath "$INSTDIR\jre"
    File /r "jre\*.*"

    SetOutPath "$INSTDIR\lib"
    File /r "lib\*.*"

    SetOutPath "$INSTDIR\laporan"
    File /r "laporan\*.*"

    ; Buat folder temp untuk JasperReports
    CreateDirectory "$INSTDIR\temp"

    SetOutPath "$INSTDIR"

    ; Buat file launcher bat
    FileOpen  $0 "$INSTDIR\jalankan.bat" w
    FileWrite $0 "@echo off$\r$\n"
    FileWrite $0 "cd /d $\"%~dp0$\"$\r$\n"
    FileWrite $0 "$\"%~dp0jre\bin\java.exe$\" -jar $\"%~dp0AplikasiPenjualan.jar$\"$\r$\n"
    FileClose $0

    ; Shortcut Desktop
    CreateShortcut "$DESKTOP\Aplikasi Penjualan.lnk" \
                   "$INSTDIR\AplikasiPenjualan.exe"

    ; Shortcut Start Menu
    CreateDirectory "$SMPROGRAMS\Aplikasi Penjualan"
    CreateShortcut "$SMPROGRAMS\Aplikasi Penjualan\Aplikasi Penjualan.lnk" \
                   "$INSTDIR\AplikasiPenjualan.exe"
    CreateShortcut "$SMPROGRAMS\Aplikasi Penjualan\Uninstall.lnk" \
                   "$INSTDIR\Uninstall.exe"

    WriteUninstaller "$INSTDIR\Uninstall.exe"

    ; Registry pakai HKCU bukan HKLM
    WriteRegStr HKCU \
        "Software\Microsoft\Windows\CurrentVersion\Uninstall\AplikasiPenjualan" \
        "DisplayName" "Aplikasi Penjualan"
    WriteRegStr HKCU \
        "Software\Microsoft\Windows\CurrentVersion\Uninstall\AplikasiPenjualan" \
        "UninstallString" "$INSTDIR\Uninstall.exe"
    WriteRegStr HKCU \
        "Software\Microsoft\Windows\CurrentVersion\Uninstall\AplikasiPenjualan" \
        "DisplayVersion" "1.0.0"
    WriteRegStr HKCU "Software\AplikasiPenjualan" "Install_Dir" "$INSTDIR"

SectionEnd

Section "Uninstall"

    Delete "$INSTDIR\AplikasiPenjualan.exe"
    Delete "$INSTDIR\AplikasiPenjualan.jar"
    Delete "$INSTDIR\jalankan.bat"
    Delete "$INSTDIR\Uninstall.exe"

    RMDir /r "$INSTDIR\jre"
    RMDir /r "$INSTDIR\lib"
    RMDir /r "$INSTDIR\laporan"
    RMDir /r "$INSTDIR\temp"
    RMDir    "$INSTDIR"

    Delete "$DESKTOP\Aplikasi Penjualan.lnk"
    RMDir /r "$SMPROGRAMS\Aplikasi Penjualan"

    DeleteRegKey HKCU \
        "Software\Microsoft\Windows\CurrentVersion\Uninstall\AplikasiPenjualan"
    DeleteRegKey HKCU "Software\AplikasiPenjualan"

SectionEnd