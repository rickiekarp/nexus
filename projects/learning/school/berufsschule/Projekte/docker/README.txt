# Docker auf Linux

Diese README beschreibt die Nutzung von Docker auf Linux Systemen.

## Getting Started

Diesem Archiv liegt der Ordner "setup" bei, welcher alle nötigen Daten beinhaltet, um eine Docker Umgebung auf Linux bereitzustellen.


### Prerequisites

 - Ubuntu 16.04
    ODER
 - Zentyal Development Edition 5.0

Eine vorbereitete Zentyal VirtualBox Appliance zum Testen liegt dem "virtualbox" Ordner bereits bei und
kann mit den Login Daten
                            User:       administrator
                            Passwort:   admin
zugegriffen werden.


### Installation

Folgende Installationsschritte beziehen sich auf die zur Verfügung gestellten Zentyal VirtualBox Appliance.
Hierbei liegen alle benötigten Installationsdateien bereits auf der Maschine bereit.

- Öffne das Terminal und gehe in den "setup" Ordner, in unserem Fall: 
    cd docker/setup/

- Starte das Bash Script "install-docker.sh" mit dem Befehl: 
    ./install-script.sh

- Wähle Option 2 aus, damit die Docker Umgebung auf unserem Zentyal System insalliert wird.


### Ausführung

Nachdem die Installation vollständig ist, starte das Bash Script erneut über:
    ./install-script.sh

Nun kann die Docker Installation einmal getestet (Option 8) und der Webshop (Option 3) gestartet werden.
Nach erfolgreichem Starten des Webshops ist dieser nun über die IP 10.14.142.171 erreichbar.


### Probleme 

Beim Aufrufen des Webshops über 10.14.142.171 werden zunächst keine Bilder angezeigt.
Dies liegt daran, dass der Webshop so konfiguriert ist, das alle Daten über localhost abgefragt werden.
Dies kann sehr leicht behoben werden, indem man die config.php (/var/lib/docker/volumes/opencart_opencart_data/_data) so anpasst,
dass die Server Adresse (z.B. 10.14.142.171) genutzt wird.


Nach dem ersten Neustart des Zentyal Services, lässt sich der Docker Container zunächst nicht starten.
Dies liegt daran, dass der Docker Daemon früher als die Firewall gestartet wird und somit nicht korrekt von der Firewall erkannt wird.
Dies lässt sich jedoch mit einem Neustart des Docker services lösen:
    sudo systemctl restart docker.service


## Autoren

* **Rickie Karp** - *Dieses Dokument*



