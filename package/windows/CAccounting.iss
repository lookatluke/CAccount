;This file will be executed next to the application bundle image
;I.e. current directory will contain folder CAccounting with application files
[Setup]
AppId={{com.e.caccount}}
AppName=CAccounting
AppVersion=1.4
AppVerName=CAccounting 1.4
AppPublisher=Luke
AppComments=CAccounting
AppCopyright=Copyright (C) 2019
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName=C:\Program Files\CAccount
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=Luke
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=CAccounting-1.4
Compression=lzma
SolidCompression=yes
PrivilegesRequired=admin
SetupIconFile=CAccounting\CAccounting.ico
UninstallDisplayIcon={app}\CAccounting.ico
UninstallDisplayName=CAccounting
UsePreviousAppDir=no
WizardImageStretch=No
WizardSmallImageFile=CAccounting-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=x64


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "CAccounting\CAccounting.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "CAccounting\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\CAccounting"; Filename: "{app}\CAccounting.exe"; IconFilename: "{app}\CAccounting.ico"; Check: returnTrue()
Name: "{commondesktop}\CAccounting"; Filename: "{app}\CAccounting.exe";  IconFilename: "{app}\CAccounting.ico"; Check: returnFalse()


[Run]
Filename: "{app}\CAccounting.exe"; Parameters: "-Xappcds:generatecache"; Check: returnFalse()
Filename: "{app}\CAccounting.exe"; Description: "{cm:LaunchProgram,CAccounting}"; Flags: runascurrentuser nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\CAccounting.exe"; Flags: runascurrentuser; Parameters: "-install -svcName ""CAccounting"" -svcDesc ""null"" -mainExe ""CAccounting.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\CAccounting.exe "; Parameters: "-uninstall -svcName CAccounting -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
