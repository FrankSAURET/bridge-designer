; wpbdexejre.nsi
;
; NSIS build script for the Bridge Designer.
;
!define BD "Bridge Designer"
!define EXE "BridgeDesigner.exe"
!define RESOURCE_DIR  "..\src\bridgedesigner\resources"

; Set up multiuser privilege package
!define MULTIUSER_INSTALLMODE_INSTDIR "${BD}"
!define MULTIUSER_EXECUTIONLEVEL Highest
!define MULTIUSER_INSTALLMODE_COMMANDLINE
!define MULTIUSER_MUI

!define CANT_WRITE_INSTDIR_MSG "L'utilisateur Windows actuel n'a pas la permission d'installer des programmes sur $\r$\n$\r$\n\
$INSTDIR$\r$\n$\r$\n\
Veuillez annuler cette installation et r�essayer, soit de vous connecter en tant qu'utilisateur avec plus de droits (tel que l'administrateur) ou en modificant du dossier d'installation pour un pour lequel vous avez une autorisation d'�criture. "

!define INST_DIR_EXISTS_MSG "Le dossier $\r$\n$\r$\n\
$INSTDIR$\r$\n$\r$\n\
que vous avez s�lectionn� pour l'installation existe d�j�. Veuillez annuler cette installation et r�essayer, en choissant un dossier n'existe pas."

!define INST_DIR_IS_OLD_VERSION "Le dossier $\r$\n$\r$\n\
$INSTDIR$\r$\n$\r$\n\
que vous avez s�lectionn� pour l'installation semble �tre une version pr�c�dente de Bridge Designer. Veuillez annuler cette installation et r�essayer apr�s avoir d�sinstall� l'ancienne version."

!define OK_TO_CLOBBER_INST_DIR_MSG "Le dossier de Bride Designer et tout son contenu vont �tre supprim�s d�finitivement.$\r$\n$\r$\n\
$INSTDIR$\r$\n$\r$\n\
Vous voulez vraiment le faire ?"

!define INST_DIR_NOT_DELETED "Le dossier $\r$\n$\r$\n\
$INSTDIR$\r$\n$\r$\n\
n'a pas �t� chang�."

!include "MultiUser.nsh"
!include "MUI2.nsh"
!include "FileAssociation.nsh"
!include "FileFunc.nsh"

; Init functions needed for multi-user package.
Function .onInit
  !insertmacro MULTIUSER_INIT
FunctionEnd

Function un.onInit
  !insertmacro MULTIUSER_UNINIT
FunctionEnd

Name "${BD}"
;OutFile "../release/setupbdv${YEAR}j.exe"
OutFile "../release/BridgeDesignerSetup-20${YEAR}.${BUILD}.exe"


; Let MultiUser handle this: InstallDir "$PROGRAMFILES\${BD}"
InstallDirRegKey HKCU "Software\${BD}" ""
BrandingText "Engineering Encounters"

DirText ""

Var StartMenuFolder

; Give a warning if the user tries to stop before complete.
!define MUI_ABORTWARNING

; Set the installer icon.
!define MUI_ICON "${RESOURCE_DIR}\appicon.ico"
!define MUI_UNICON "${RESOURCE_DIR}\appicon.ico"

; Provide custom graphics and configuration information.
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "${RESOURCE_DIR}\installlogo.bmp"
!define MUI_HEADERIMAGE_UNBITMAP  "${RESOURCE_DIR}\installlogo.bmp"
!define MUI_HEADERIMAGE_RIGHT
!define MUI_WELCOMEFINISHPAGE_BITMAP "${RESOURCE_DIR}\welcomelogo.bmp"
!define MUI_UNWELCOMEFINISHPAGE_BITMAP  "${RESOURCE_DIR}\welcomelogo.bmp"

; Welcome page settings.
!define MUI_WELCOMEPAGE_TITLE "${BD}"
!define MUI_WELCOMEPAGE_TEXT "Bienvenue dans l'installateur de ${BD}.$\r$\n$\r$\nBridge Designer est con�u pour fonctionner \
sur n'importe quel ordinateur capable d'ex�cuter Java. Ce programme d'installation comprend une copie du runtime Java.$\r$\n$\r$\n\
Si vous avez d'autres programmes en cours d'ex�cution, veuillez les fermer avant de proc�der � cette \
installation.$\r$\n$\r$\nCliquer sur Suivant pour continuer."
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "${RESOURCE_DIR}\license.txt"
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MULTIUSER_PAGE_INSTALLMODE
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuFolder
!insertmacro MUI_PAGE_INSTFILES
; Finish page settings.
!define MUI_FINISHPAGE_TITLE "L'installation de ${BD} est termin�e"
!define MUI_FINISHPAGE_TEXT "L'installation est termin�e! Merci d'avoir choisi d' \
utiliser notre logiciel. Aller sur http://bridgecontest.org pour avoir des information \
sur la version originale de Bridge designer et https://github.com/FrankSAURET/bridge-designer pour cette version."
!define MUI_FINISHPAGE_RUN "$INSTDIR\${EXE}"
!define MUI_FINISHPAGE_RUN_TEXT "Lancer ${BD}."
!define MUI_FINISHPAGE_RUN_NOTCHECKED
!insertmacro MUI_PAGE_FINISH

!define MUI_WELCOMEPAGE_TITLE "D�sinstallateur de ${BD} "
!insertmacro MUI_UNPAGE_WELCOME
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES
!define MUI_FINISHPAGE_TITLE "${BD} est d�sinstall�"
!define MUI_FINISHPAGE_TEXT "La d�sinstallation est termin�e. Aller sur http://bridgecontest.org pour avoir des information sur la version originale de Bridge designer et https://github.com/FrankSAURET/bridge-designer pour cette version."
!insertmacro MUI_UNPAGE_FINISH

!insertmacro MUI_LANGUAGE "French"

!verbose 3

Section "Bridge Designer" SectionBD

    ; Ensure the installation directory doesn't exist because we clobber
    ; it recursively when we uninstall. Allow re-installation, though.
    IfFileExists "$INSTDIR\${EXE}" CreateAndSet
    IfFileExists $INSTDIR InstDirExistsError

    ; Create (if necessary) and set the installation directory.
  CreateAndSet:
    ClearErrors
    SetOutPath $INSTDIR
    IfErrors CantWriteInstallDirectory

    ; Check writability of installation directory.
    FileOpen $0 "$INSTDIR\writecheck.txt" w
    FileWrite $0 "write check"
    IfErrors CantWriteInstallDirectory
    FileClose $0
    Delete "$INSTDIR\writecheck.txt"

    ; Copy all the system files to the install directory.
    File ${RESOURCE_DIR}\*.ico
    File /r /x WPBD.jar /x README.TXT /x detectjvm.exe ..\dist\*.* 
    File /r ..\jre7
    File ..\src\bridgedesigner\build.number
    ; Since we are using a 32-bit jre, always get the 32-bit dll.
    ;File ..\lib-sup\jogamp-all-platforms\lib\windows-i586\*.dll

    ; Create the uninstaller executable.
    WriteUninstaller "$INSTDIR\uninstall.exe"

    ; Write shortcuts in Start menu.
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
        CreateDirectory "$SMPROGRAMS\$StartMenuFolder"
        CreateShortCut "$SMPROGRAMS\$StartMenuFolder\${BD}.lnk" "$INSTDIR\${EXE}" "" "$INSTDIR\appicon.ico"
        ;CreateShortCut "$SMPROGRAMS\$StartMenuFolder\${BD} for older computers.lnk" "$INSTDIR\${EXE}" "-legacygraphics" "$INSTDIR\appicon.ico"
        CreateShortCut "$SMPROGRAMS\$StartMenuFolder\Uninstall ${BD}.lnk" "$INSTDIR\uninstall.exe" 
    !insertmacro MUI_STARTMENU_WRITE_END

    ; Save the Start menu folder path.
    FileOpen $0 "$INSTDIR\uninstall.dat" w
    FileWrite $0 "$SMPROGRAMS\$StartMenuFolder"
    FileClose $0

    ; Write registry with uninstall info for the programs manager.
    StrCpy $0 "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\${BD}"
    WriteRegStr HKLM $0 "DisplayName" "${BD} (remove only)"
    WriteRegStr HKLM $0 "UninstallString" "$INSTDIR\uninstall.exe"

    ; Clear old session data, if any.
    ExpandEnvStrings $0 "%APPDATA%"
    Delete "$0\EngineeringEncounters\WPBD\*.*"
    goto Done

  CantWriteInstallDirectory:
    MessageBox MB_OK|MB_ICONEXCLAMATION "${CANT_WRITE_INSTDIR_MSG}" 
    Abort

  InstDirExistsError:
    IfFileExists "$INSTDIR\Bridge Designer.exe" ElseShowUninstallMsg
      MessageBox MB_OK|MB_ICONEXCLAMATION "${INST_DIR_EXISTS_MSG}"
      Abort
    ElseShowUninstallMsg:
      MessageBox MB_OK|MB_ICONEXCLAMATION "${INST_DIR_IS_OLD_VERSION}"
      Abort

    Done:
SectionEnd

Section "Enregistrer l'extension de fichier" SectionRegExt
    ${FileAssociation_VERBOSE} 1
    ${RegisterExtension} "$INSTDIR\${EXE}" ".bdc" "WP Bridge Design File"
    ${RefreshShellIcons}
SectionEnd

; Set up the description blocks for mouseovers of the page selection checkboxes.
LangString DESC_SectionBD ${LANG_FRENCH} \
   "installer ${BD} et ses fichiers d'aide."
LangString DESC_SectionRegExt ${LANG_FRENCH} \
   "Enregistrer l'extension.bdc pour l'ouvrir � partir de l'explorateur."

!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
    !insertmacro MUI_DESCRIPTION_TEXT ${SectionBD} $(DESC_SectionBD)
    !insertmacro MUI_DESCRIPTION_TEXT ${SectionRegExt} $(DESC_SectionRegExt)
!insertmacro MUI_FUNCTION_DESCRIPTION_END

Section "Uninstall"

    ; Unregister the bridge file type.
    ${UnregisterExtension} ".bdc" "WP Bridge Design File"
    ${RefreshShellIcons}

    ; Retrieve the start menu folder stored in a file during installation.
    ClearErrors
    FileOpen $0 "$INSTDIR\uninstall.dat" r
    IfErrors LinkRemovalProblem
    FileRead $0 $1
    FileClose $0

    ; Delete the start menu entries and folder using path in $1.
    Delete "$1\${BD}.lnk"
    ;Delete "$1\${BD} for older computers.lnk"
    Delete "$1\Uninstall ${BD}.lnk"
    RMDir "$1"
    IfErrors LinkRemovalProblem

    Goto DoneDeleteLinks

    ; Handle any problems with link removal.
  LinkRemovalProblem:
    MessageBox MB_OK "D�sol�, impossible d'effacer les entr�es du menu de d�marrage. Essayez manuellement."

  DoneDeleteLinks:

    ; Delete uninstall and start menu registry keys.
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\${BD}"

    ; Clear session data, if any.
    ExpandEnvStrings $0 "%APPDATA%"
    RMDir /r "$0\EngineeringEncounters"

  ; Unless in silent mode, make double sure the user wants to clobber the dir.
  MessageBox MB_YESNO|MB_ICONEXCLAMATION "${OK_TO_CLOBBER_INST_DIR_MSG}" /SD IDYES IDNO ShowNotDeletedMsg 

    ; Delete the installation files. Do this last so we don't clobber uninstall.dat before use.
    RMDir /r /REBOOTOK $INSTDIR
    Goto UninstallComplete

  ShowNotDeletedMsg:
    MessageBox MB_OK "${INST_DIR_NOT_DELETED}"

  UninstallComplete:

SectionEnd