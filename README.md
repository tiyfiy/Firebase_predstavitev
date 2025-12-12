# Firebase za Android - Kolokvijska naloga

## Avtоr
**Ime:** Timotej Maučec
**Študijsko leto:** 2005  
**Predmet:** Platformno odvisni razvoj aplikacij (PORA)

---

## Uvod

Firebase je platforma Backend-as-a-Service (BaaS), ki jo je razvil Google in omogoča razvijalcem hitro razvijanje mobilnih in spletnih aplikacij brez potrebe po vzdrževanju lastne backendne infrastrukture. Firebase ponuja celoten ekosistem storitev, ki pokrivajo avtentikacijo, shranjevanje podatkov, gostovanje, analitiko, push obvestila in še več.

V tem projektu sem demonstriral štiri ključne Firebase funkcionalnosti:
- **Firebase Authentication** - sistem za prijavo in registracijo uporabnikov
- **Firestore Database** - NoSQL podatkovna baza v oblaku
- **Realtime Database** - podatkovna baza s sinhronizacijo v realnem času
- **Firebase Cloud Messaging (FCM)** - sistem za pošiljanje push obvestil

---

## Utemeljitev izbire

### Zakaj Firebase?

1. **Hitrejši razvoj**: Firebase omogoča razvijalcem, da se osredotočijo na frontend razvoj, medtem ko backend infrastrukturo upravlja Google.

2. **Integracija z Android ekosistemom**: Firebase je tesno integriran z Android Studio in Google Play Store, kar omogoča brezhibno delovanje.

3. **Skalabilnost**: Aplikacije lahko začnejo majhne in rastejo brez skrbi za infrastrukturo.

4. **Realni čas**: Realtime Database omogoča sinhronizacijo podatkov v realnem času med vsemi povezanimi odjemalci.

5. **Brezplačen začetek**: Firebase ponuja velikodušen brezplačen paket (Spark plan), ki je primeren za manjše projekte in učenje.

6. **Enostavna implementacija**: Z dobro dokumentacijo in SDK-ji za različne platforme je implementacija hitra.

---

## Prednosti in slabosti

### Prednosti

| Prednost | Opis |
|----------|------|
| **Hiter razvoj** | Zmanjšanje časa razvoja za do 30-40% zaradi že pripravljene backendne infrastrukture |
| **Varnost** | Vgrajeni varnostni mehanizmi in pravila za dostop do podatkov |
| **Analitika** | Brezplačna in natančna analitika uporabnikov |
| **Multi-platforma** | Podpora za Android, iOS, Web, Flutter |
| **Realni čas** | Sinhronizacija podatkov v realnem času brez dodatne konfiguracije |
| **Brezplačen tier** | Spark plan omogoča brezplačno uporabo za manjše projekte |
| **Enostavna integracija** | Plugin za Android Studio, dependency management |
| **Push obvestila** | FCM omogoča zanesljiva push obvestila |

### Slabosti

| Slabost | Opis |
|---------|------|
| **Stroški** | Pri večjih aplikacijah lahko stroški hitro narastejo |
| **Vendor lock-in** | Težka migracija na drugo platformo zaradi specifične arhitekture |
| **Omejena fleksibilnost** | Omejitve pri kompleksnih poizvedbah in relacijskih podatkih |
| **Odvisnost od interneta** | Brez povezave aplikacija deluje omejeno (kljub offline podpori) |
| **Debugging** | Težje razhroščevanje v primerjavi z lastnim backendom |
| **Omejitve Firestore** | Maksimalno 1 MB na dokument, omejene poizvedbe |

---
