# Firebase za Android ![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=white) ![Firebase Hosting](https://img.shields.io/badge/Hosting-Firebase-FFCA28?logo=firebase&logoColor=white) ![Firestore](https://img.shields.io/badge/Firestore-Firebase-FFCA28?logo=firebase&logoColor=white) ![Firebase Auth](https://img.shields.io/badge/Auth-Firebase-FFCA28?logo=firebase&logoColor=white)



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

## Licenca in uporabniki

### Licenca
Firebase uporablja **Google APIs Terms of Service** in **Firebase Terms of Service**. 

- **Licenčni tip**: Proprietary (lastniška licenca)
- **SDK licenca**: Apache License 2.0 (za Firebase Android SDK)
- **Uporaba**: Brezplačna za Spark plan, plačljiva za Blaze plan

**Firebase SDK GitHub:** https://github.com/firebase/firebase-android-sdk

### Število uporabnikov

- **Več kot 3 milijone aplikacij** uporablja Firebase
- **Firebase Android SDK** ima preko **40,000+ stars** na GitHubu
- **Maven downloads**: Več kot 100M+ prenosov mesečno
- **Top uporabniki**: Duolingo, The New York Times, Trivago, Alibaba, Lyft

---

## Časovna in prostorska zahtevnost

### Firebase Authentication

| Operacija | Časovna zahtevnost | Prostorska zahtevnost |
|-----------|-------------------|----------------------|
| Sign Up (Email/Password) | O(1) - 500-1500ms | O(1) - ~50KB RAM |
| Sign In | O(1) - 300-1000ms | O(1) - ~30KB RAM |
| Sign Out | O(1) - instant | O(1) - minimal |
| Password Reset | O(1) - 1-3s | O(1) - ~20KB RAM |

### Firestore Database

| Operacija | Časovna zahtevnost | Prostorska zahtevnost |
|-----------|-------------------|----------------------|
| Read single document | O(1) - 50-200ms | O(n) - velikost dokumenta |
| Write single document | O(1) - 100-300ms | O(n) - velikost dokumenta |
| Query collection | O(n + k) | O(k) - k = število rezultatov |
| Batch write | O(n) - n = št. operacij | O(n) - velikost vseh dok. |

### Realtime Database

| Operacija | Časovna zahtevnost | Prostorska zahtevnost |
|-----------|-------------------|----------------------|
| Read | O(1) - 20-100ms | O(n) - velikost podatkov |
| Write | O(1) - 50-200ms | O(n) - velikost podatkov |
| Listener update | O(1) - 20-50ms | O(n) - spremenjeni podatki |
| Query | O(n) | O(k) - št. rezultatov |

### Firebase Cloud Messaging

| Operacija | Časovna zahtevnost | Prostorska zahtevnost |
|-----------|-------------------|----------------------|
| Token registration | O(1) - 500ms-2s | O(1) - ~5KB |
| Receive notification | O(1) - instant | O(1) - ~10KB |
| Send via API | O(1) - 100-500ms | O(1) - velikost payload |

### Omejitve

- **Firestore**: Maksimalno 1 MB na dokument, 10,000 write operacij/sekundo
- **Realtime Database**: Maksimalno 200,000 concurrent connections (Blaze plan)
- **FCM**: Maksimalno 4 KB payload velikost
- **Bandwidth**: 10 GB/mesec (Spark plan), unlimited (Blaze plan)

---

## Vzdrževanje

### Razvojni tim

Firebase vzdržuje **Google Firebase Team** z več kot **150+ contributors** na GitHubu.

### Zadnje posodobitve (December 2024)

```
Firebase Android SDK:
- Zadnja verzija: v33.7.0 (November 2024)
- Frekvenca posodobitev: Mesečno
- Kritični popravki: V 24 urah
- Major releases: 3-4x letno
```

### Aktivnost repozitorija

- **GitHub**: https://github.com/firebase/firebase-android-sdk
- **Stars**: 2,300+
- **Forks**: 570+
- **Issues**: ~150 odprtih, ~3,500 zaprth
- **Pull requests**: Aktivno sodelovanje skupnosti
- **Commit frequency**: 50-100+ commitov mesečno

### Podpora

- **Google Cloud Support**: 24/7 za Blaze plan
- **Firebase Console**: Real-time monitoring in alerting
- **Stack Overflow**: 250,000+ vprašanj z tag "firebase"
- **Dokumentacija**: Neprestano posodobljena
- **Community**: Firebase Slack, Discord, Reddit

### Release cycle

```
- Patch updates: Tedensko
- Minor updates: Mesečno
- Major updates: Kvartalno
- Security patches: Takoj ob odkritju
```

---

## Funkcionalnosti projekta

Ta projekt demonstrira štiri ključne Firebase komponente:

### 1. Firebase Authentication
- **Registracija** uporabnikov z email/password
- **Prijava** obstoječih uporabnikov
- **Odjava** iz aplikacije
- **Pozabljeno geslo** (email reset)
- **Validacija** email naslovov in gesel

### 2. Firestore Database
- **CRUD operacije** (Create, Read, Update, Delete)
- **Shranjevanje** strukturiranih podatkov
- **Poizvedbe** s filtriranjem in sortiranjem
- **Real-time posodobitve** z listeners
- **Batch operacije**

### 3. Realtime Database
- **Sinhronizacija v realnem času** med napravami
- **Offline podpora** z avtomatsko sinhronizacijo
- **Chat funkcionalnost** kot demo
- **Listeners** za spremljanje sprememb
- **Optimistično posodabljanje**

### 4. Firebase Cloud Messaging
- **Push obvestila** na napravo
- **Token management**
- **Foreground obvestila**
- **Background obvestila**
- **Custom payload** podatki

---

## Avtоr
**Ime:** Timotej Maučec
**Študijsko leto:** 2005  
**Predmet:** Platformno odvisni razvoj aplikacij (PORA)
