Um diese Scripte ordnungsgemäß ausführen zu lassen müssen folgende aspekte konfiguriert werden:

Im "Starte Webcontainer" Script:
	Zeile 1: "C:\Program Files\Docker Toolbox" durch den installationspfad von "Docker Toolbox" ersetzten.
	Zeile 2: "C:\Program Files\Git\bin\bash.exe" durch den installationspfad von "Bash" ersetzten.
	Zeile 3: "C:\Users\Sebastian\Desktop\dockerstart.sh" durch den installationspfad von "Dockerstart" ersetzten.

Im "helferscript_Starte Webcontainer" Script:
	Zeile 1: "C:\Users\Sebastian\Desktop\dockerstart.sh" durch den installationspfad von "Dockerstart" ersetzten.
	Zeile 1: "C:/Share/setup-docker/setup/opencart/docker-compose.yml" durch den Pfad der "docker-compose.yml" Datei ersetzen. Auf Linux Pfadangabe achten ('\' durch '/' ersetzen)