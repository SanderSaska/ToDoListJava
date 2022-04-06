# ToDo list
Programm, millega on võimalik jälgida oma tegevuste nimekirja

## Töö põhimõte
Tegevused on järgmises formaadis: "sisestusAeg;nimi;pikkKirjeldus;tehtud"

*sisestusAeg* - long tüüpi rollis aeg, mis on võetud ajahetkest, kui sündmus sai lisatud faili

*nimi* - String tüüpi rollis nimi, mis näitab sündmuse/tegevuse nime

*pikkKirjeldus* - String tüüpi rollis sündmuse/tegevuse kirjeldus

*tehtud* - Boolean tüüpi rollis, kas sündmus on tehtud


**Programmi avamisel kuvatakse esmane info ning kasutaja peab sisestama failinime :grey_exclamation: .txt :grey_exclamation: vormis**

Kasutajal tekib järgmised valikud:

![Programm küsib järgmiselt:](/näide.png)

#### 0. Välju
Väljub programmist
#### 1. Vaata sündmusi
Kuvab ette kõik sündmused, mida sa oled kirjutanud antud faili
#### 2. Lisa sündmusi
Saad lisada sündmuse:
- Esiteks pead sisestame sündmuse nime
- Teiseks lisad täpsustava kirjelduse

Ja sündmus ongi lisatud!
#### 3. Kustuta sündmusi
Annab võimaluse kustutada vastaval järjekorranumbril olevat sündmust
Sulle kuvatakse ette kõik sündmused koos järjekorranumbritega ning küsitakse, millist sündmust on vaja kustutada.
#### 4. Märgi sündmus tehtuks
Annab võimaluse muuta vastaval järjekorranumbril olevat sündmust tehtuks
Sulle kuvatakse ette kõik sündmused koos järjekorranumbritega ning küsitakse, millist sündmust muuta tehtuks.
#### 5. Puhasta sündmused, mis on tehtud
Kustutab kõik sündmused (ka failist), mis on tehtud.
