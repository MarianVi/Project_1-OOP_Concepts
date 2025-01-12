Realizează design-ul și implementarea unei clase PlayerStatus care menține actualizată starea unui Player în cadrul jocului.\
Clasa trebuie să respecte principiile OOP. De exemplu, ține cont de: vizibilitatea stării obiectelor, separarea corectă a conceptelor, modularitatea codului, refolosirea funcționalității, ascunderea detaliilor etc. 
Cu alte cuvinte, punctarea exercițiului se va realiza ținând cont atât de funcționalitate cât și de arhitectura clasei.

Starea internă

Starea internă a obiectelor de tip PlayerStatus va conține:

    1. nickname: un șir de caractere, ce semnifică numele jucătorului.
    2. score: un număr întreg, ce reprezintă scorul jucătorului.
    3. lives: un număr întreg, ce simbolizează numărul de vieți al jucătorului.
    4. health: un număr întreg, între 0 și 100, ce reprezintă procentul de viață rămas.
    5. weaponInHand: un șir de caractere ce simbolizează arma jucătorului.
    6. positionX: coordonata OX a poziției jucătorului, o valoare numerică reală.
    7. positionY: coordonata OY a poziției jucătorului, o valoare numerică reală.
    8. gameName: numele jocului.
        toți jucătorii fac parte din același joc. Prin urmare, valoarea câmpului va fi unică pentru toate obiectele.

Pe lângă aceste câmpuri, poți adăuga orice alte atribute pe care le consideri utile / necesare în implementarea funcționalității cerute și în cadrul jocului.

Reguli de joc

Regulile generale de desfășurare a jocului sunt:

    1. health:
        a. va lua valori între 0 și 100.
        b. dacă health ajunge la 0 (sau o valoare negativă) în urma unei operații, se va decrementa numărul
          de vieți (i.e. lives) cu 1 iar health va fi resetat la valoarea maximă, 100. Practic, jucătorul moare
          și se va mai consuma o viață din numărul de vieți rămase.
        c. dacă health trece de valoarea 100 valoarea sa va fi trunchiată la 100. Numărul de vieți rămâne 
          nemodificat (i.e. nu se incrementează numărul de vieți).
    2. dacă lives ajunge la valoarea 0 atunci jocul s-a terminat, și se va afișa mesajul Game Over.
    3. weaponInHand arma curentă a jucătorului.
        poate avea una dintre următoarele valori:
            - knife - cost 1000 puncte,
            - sniper - cost 10000 puncte,
            - kalashnikov - cost 20000 puncte.
        Obs: pentru a compara dacă 2 șiruri de caractere sunt identice, nu se va utiliza operatorul == ci metoda 
          equals(String) aparținând clasei String. Consultă API-ul pentru detalii de utilizare.
        Obs2: pentru a achiziționa o armă, jucătorul trebuie să aibă un scor minim egal cu valoarea armei. După 
          actualizarea armei, atributul score va fi actualizat în consecință (i.e. score -= weaponPrice).
    4. duelul între 2 jucători:
        a. dacă cei doi jucători au aceeași armă în mână:
            - se va calcula pentru fiecare o probabilitate de câștig astfel: (3 * health + score / 1000) / 4.
            - jucătorul cu probabilitate mai mare va câștiga lupta.
        b. dacă cei doi jucători au arme diferite, puterea armelor depinde de distanța între jucători:
            - distanta > 1000 → puterea armelor descrește astfel: sniper > kalashnikov > knife.
            - distanta ⇐ 1000 → puterea armelor descrește astfel: kalashnikov > sniper > knife.
            - duelul va fi câștigat de jucătorul cu o armă mai puternică, în funcție de distanța dintre cei doi jucători.
    5. distanța între 2 jucători:
        - se calculează folosind formula distanței euclidiene, astfel:
          √((positionXobj1−positionXobj2)(positionXobj1−positionXobj2)+(positionYobj1−positionYobj2)(positionYobj1−positionYobj2))

Comportament

Comportamentul unui jucător va include:

    1. 3 metode de inițializare, cu diverse seturi de parametri:
        - initPlayer(nickname): va inițializa doar numele jucătorului (restul câmpurilor vor avea valoarea implicită a tipului).
        - initPlayer(nickname, lives): va inițializa câmpurile aferente: nickname respectiv lives(restul câmpurilor vor avea valoarea implicită a tipului).
        - initPlayer(nickname, lives, score): comportamentul va fi unul asemănător, inițializând câmpurile cu același nume ca parametrii.
    2. findArtifact(int artifactCode): metoda va actualiza starea jucătorului în funcție de artifactul găsit, astfel:
        a. dacă artifactCode este număr perfect:
            - score se incrementează cu 5000 puncte.
            - lives crește cu 1 viață.
            - health devine 100.
        b. dacă artifactCode este număr prim:
            - score se incrementează cu 1000 puncte.
            - lives crește cu 2 vieți.
            - health crește cu 25, dar nu poate depăși valoarea 100.
        c. dacă artifactCode este număr par și suma cifrelor sale este divizibilă cu 3 atunci obiectul respectiv este o capcană.
           Prin urmare, situația jucătorului se modifică astfel:
            - score se decrementează cu 3000 puncte.
            - health se decrementează cu 25.
                dacă health ajunge la 0, atunci lives va fi decrementat cu 1 și health resetat la 100.
        d. orice alte numere nu prezintă „puteri magice“, prin urmare, singura actualizare a stării este:
            - score va fi incrementat cu valoarea artifactCode.
        Observație: criteriile anterioare se vor testa pe rând (i.e. în ordinea dată) și se va actualiza starea conform primei condiții validate.
            De exemplu: dacă un artifactCode este atât „număr perfect“ cât și „număr par având suma cifrelor divizibilă cu 3“ 
              se va actualiza starea în conformitate cu „număr perfect“ deoarece acest criteriu precede pe celălalt.
    3. setWeaponInHand(String weaponInHand): acțiunea va schimba arma folosită de jucător în joc:
        - metoda va verifica inițial dacă arma primită ca argument este o armă validă, conform regulilor jocului.
         -se verifică apoi dacă jucătorul are suficiente puncte acumulate în joc pentru a plăti arma.
        - dacă arma este validă și jucătorul are suficiente puncte pentru achiziție, arma va fi actualizată. 
            Odată cu aceasta, se va actualiza și scorul, pentru a reflecta plata armei cumpărate.
        - metoda returnează true dacă schimbarea armei a avut loc cu succes și false în orice alt caz.
    4. getWeaponInHand(): returnează arma curentă a jucătorului.
    5. transformă câmpurile positionX și positionY în proprietăți ale obiectelor clasei PlayerStatus.
    6. implementează metodele necesare pentru ca atributul gameName să devină o proprietate:
        - va fi acesta o proprietate a fiecărui obiect în parte, sau o proprietate a clasei?
        - implementează corespunzător metodele getter & setter.
        - oricine ar trebui să aibă drept de accesare a proprietății gameName, însă doar „codul“ aparținând
          aceluiași pachet ar trebui să aibă acces la actualizarea numelui jocului. Actualizează în consecință
          modificatorii de acces a celor două metode.
    7. movePlayerTo(double positionX, double positionY).
        - intuitiv, metoda actualizează poziția jucătorului, prin actualizarea poziției acestuia.
    8. numele jucătorului nu ar trebui să poată fi modificat din exterior, însă ar trebui să poată fi citit. 
       Cu alte cuvinte, transformă câmpul nickname într-o proprietate read-only (i.e. care poate fi doar citită public).
          Hint: Care dintre cele două metode speciale trebuie implementată în acest caz?
    9. shouldAttackOpponent(PlayerStatus opponent): metoda simulează o bătălie între jucătorul curent 
       și un adversar, pentru a sfătui jucătorul dacă ar trebui să atace (i.e. ar câștiga o eventuală luptă):
        - metoda returnează true dacă jucătorul curent ar câștiga lupta și false altfel.
        - regulile după care se stabilește învingătorul sunt detaliate în secțiunea de Reguli Generale.
        - OOP-hint: utilizați principiul separării logice a conceptelor implementate în toate metodele implementate.
            Exemplu: distanța dintre 2 jucători ar trebui calculată într-o metodă separată. Acea metodă trebuie
            apelată la nevoie, oriunde în cod. Deoarece metoda va fi folosită doar în cadrul implementării clasei,
            deci intern, ce modificator de acces este cel mai adecvat?

Pe lângă acestea, poți implementa orice alte metode (publice sau private) pe care le consideri necesare / utile în cadrul jocului sau în implementarea acțiunilor detaliate anterior.
