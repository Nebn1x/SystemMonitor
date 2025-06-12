[Setup]
AppName=System Monitor
AppVersion=1.0
DefaultDirName={pf}\SystemMonitor
OutputDir=.\target
OutputBaseFilename=SystemMonitorSetup
SetupIconFile=src/main/resources/icons/app_icon.ico
Compression=lzma2
SolidCompression=yes

[Files]
Source: "target\cursova-1.0-SNAPSHOT-jar-with-dependencies.jar"; DestDir: "{app}";  Flags: ignoreversion; Attribs: hidden
Source: "target\launch.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Program Files\Java\javafx-sdk-24.0.1\lib\*"; DestDir: "{app}\lib"; Flags: recursesubdirs
Source: "C:\Program Files\Java\javafx-sdk-24.0.1\bin\*"; DestDir: "{app}\bin"; Flags: recursesubdirs
Source: "src\main\resources\icons\app_icon.ico"; DestDir: "{app}\icons"; Flags: ignoreversion

[Icons]
Name: "{group}\System Monitor"; Filename: "{app}\launch.bat"; IconFilename: "{app}\icons\app_icon.ico"
Name: "{commondesktop}\System Monitor"; Filename: "{app}\launch.bat"; IconFilename: "{app}\icons\app_icon.ico"