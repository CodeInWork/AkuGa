# AkuGa
Akustic Games

Beschreibung

Ähnlich einem interaktiven Hörspiel bewegt man sich innerhalb der Welt nur Anhand von Beschreibungen der Umwelt und Erzählungen der Ereignisse.
Anders als bei einem Hörspiel kann jedoch direkt Einfluss auf die Umgebung genommen werden. Derzeit noch per Auswahl (Button-Klick), später 
jedoch auch über Spracherkennung können Aktionen durchgeführt werden. 
Dies Geschieht in Abstimmung mit Hintergrundgeräuschen und Musik unter Einbezugnahme der Geräuschrichtung (Mixing Links-Rechts) um ein 
eindrückliches Gefühl der Umwelt zu generieren.

Aufbau

Das Spiel liegt im Wesentlichen eine Graphenstruktur zu Grunde. Jeder Ort enthält eine Zahl von Zuständen (Bsp.: Licht an/Licht aus, 
Tür auf/Tür zu, etc.) und jeder Zustand (Knoten) verfügt über eine Zahl von Aktionsmöglichkeiten oder Kanten (Bsp.: Untersuchen, Öffnen, etc.).
Je nach Wahl wird ein neuer Ort in seinem Zustand aufgesucht und/oder aktualisiert. Um das Abspielen und die Lautstärkekontrolle nach Bedarf 
zu steuern, laufen neben dem main-Thread der die Benutzeroberfläche sowie die Graphenverwaltung übernimmt noch je ein Thread für die 
Vordergrund- und Hintergrundgeräusche.

Entwicklungsstand

Es ist bereits spielbar, aber noch nicht komplett. So fehlen noch grundsätzliche Funktionen wie Pausieren und Speichern, sowie eine 
ansprechende Benutzeroberfläche. Letztere existiert derzeit nur auf dem Papier. 
Hintergrundgeräuschen und -musik werden von Mort Ohnesorg (Künstlername, SoundCloud: https://soundcloud.com/mort-ohnesorg) designed
und befinden sich ebenfalls in der Entwicklung.
