# 🎩 Wokulski Dashboard - Projekt "Lalka" 

Witaj w zespole programistycznym firmy **Wokulski i Spółka**. 
Twoim zadaniem jest cyfryzacja XIX-wiecznych ksiąg rachunkowych przy użyciu **Jetpack Compose**.

## 🎯 Cel Projektu
Budowa aplikacji mobilnej do zarządzania finansami Stanisława Wokulskiego, z zachowaniem najlepszych praktyk inżynierii oprogramowania (SDLC).

## 🛠 Wymagania Techniczne (MoSCoW)
- **Must Have:**
  - Wyświetlanie aktualnego salda (Balance).
  - Formularz dodawania transakcji (TextField, Button).
  - Wybór typu transakcji: **Zysk ze sprzedaży** vs **Koszt (Wydatki)**.
  - Implementacja **State Hoisting** (stan trzymany w `MainScreen`, komponenty są bezstanowe).
- **Should Have:**
  - Walidacja danych (nie można dodać pustej nazwy lub ujemnej kwoty).
  - Kategorie transakcji (np. Sklep, Kamienica, Wydatki Osobiste).
- **Could Have:**
  - Lista ostatnich transakcji (LazyColumn).

## 👥 Symulacja SDLC (Zasady współpracy)
Projekt realizowany jest w parach. Podzielcie się rolami:
1. **Developer A (Feature: Income)**: Odpowiada za logikę i UI przychodów.
2. **Developer B (Feature: Expenses)**: Odpowiada za logikę i UI kosztów i kategorii.

### Git Flow:
1. Stwórzcie branch `feature/your-part-name`.
2. Po zakończeniu prac otwórzcie **Pull Request** do gałęzi `main`.
3. **Code Review:** Druga osoba z pary musi przejrzeć kod, zostawić co najmniej 2 komentarze i zatwierdzić zmiany przed mergem.

## 🏗 Struktura Projektu
Zapoznaj się z architekturą przed rozpoczęciem pisania kodu (patrz: poniżej).

```
app/src/main/java/com/example/wokulskidashboard/
│
├── ui/
│   ├── theme/              # Wygenerowane przez Android Studio (Type, Color, Theme)
│   │
│   ├── components/         # Mniejsze komponenty (Atomic Design)
│   │   ├── TransactionCard.kt
│   │   ├── BalanceHeader.kt
│   │   ├── CommonInputs.kt # Tu obaj deweloperzy mogą brać gotowe TextFieldy
│   │   │
│   │   ├── IncomeForm.kt   # 🟢 PLIK DEVELOPERA A (Tylko on tu pracuje)
│   │   └── ExpenseForm.kt  # 🔴 PLIK DEVELOPERA B (Tylko on tu pracuje)
│   │
│   └── MainScreen.kt       # 🟣 PLIK WSPÓLNY (Tu deweloperzy łączą swoją pracę - Merge Point)
│                           
│
├── model/                  # Klasy danych
│   └── Transaction.kt      
│
└── MainActivity.kt         # Punkt wejścia (odpala MainScreen)

```

## 📂 Opis Komponentów Systemu (Architektura Aplikacji)
Aplikacja "Wokulski Dashboard" została podzielona na małe, reużywalne klocki zgodnie z zasadami Atomic Design.
Poniżej znajdziecie mapę drogową po plikach projektu:

### 📦 Warstwa Danych (Model)
`Transaction` (*Data Class*): 
- Serce naszej logiki biznesowej.
- To model danych reprezentujący pojedynczy wpis w księdze rachunkowej Wokulskiego.
- Przechowuje informacje takie jak:
  - nazwa towaru/celu,
  - kwota oraz
  - flaga określająca, czy to wydatek, czy przychód.

### 🎨 Warstwa Widoku (*UI Components*)
`BalanceHeader` (*Stateless Composable*): 
- Komponent nagłówka.
- Jego jedynym zadaniem jest ładne wyświetlenie aktualnego stanu kasy (Salda).
- Otrzymuje gotową kwotę "z góry" (od rodzica) i nie wykonuje żadnych obliczeń.


`TransactionCard` (*Reusable Component*): 
- Karta reprezentująca pojedynczą transakcję na liście (jeśli zdecydujecie się ją zaimplementować).
- Odpowiada za ułożenie tekstu i kwoty w estetycznej ramce (np. zielonej dla zysku, czerwonej dla straty).


`CommonInputs` (*Shared UI*): 
- Zbiór gotowych, ostylowanych elementów wejściowych (np.`WokulskiTextField`, `WokulskiButton`).
- Zamiast pisać standardowego TextFielda od nowa, obaj deweloperzy powinni korzystać z tych gotowców, aby aplikacja wyglądała spójnie (utrzymanie jednego Design Systemu).


### 🛠️ Warstwa Funkcjonalna (*Forms*)
`IncomeForm` (*Feature Component*): 
- Królestwo Developera A (**Subiekta**).
- Komponent formularza służący wyłącznie do księgowania zysków ze sklepu.
- Zawiera pola tekstowe i przycisk, a po jego kliknięciu wysyła zdarzenie (`Event`/`Callback`) w górę do `MainScreen`.


`ExpenseForm` (*Feature Component*): 
- Królestwo Developera B (**Zarządcy**).
- Formularz do księgowania kosztów. 
- Różni się od formularza przychodów tym, że może zawierać dodatkowe przełączniki (np. Switch do oznaczania wydatków "Zbytecznych").
- Również komunikuje się z MainScreen za pomocą `Callback`ów.

-------
# 📖 Scenariusze Użytkownika (*User Stories*) i Przepływ Aplikacji
Zanim zaczniemy kodować, musimy zrozumieć, jak użytkownicy będą korzystać z naszej aplikacji. 
W inżynierii oprogramowania używamy do tego tzw. *User Stories*. 


Zobaczmy, czego oczekuje personel sklepu Wokulskiego:
- Ignacy Rzecki (Subiekt) – zarządza codziennymi zyskami ze sklepu.
- Stanisław Wokulski (Szef) – zarządza dużymi wydatkami i sprawdza ogólne saldo.


## 📝 Historyjki Użytkownika (Agile)
- US1 (Przychody): Jako subiekt Rzecki, chcę wpisać nazwę towaru (np. "Paryskie rękawiczki") i jego cenę w rublach, a następnie kliknąć przycisk zapisu, aby zysk natychmiast powiększył kapitał sklepu.

- US2 (Wydatki): Jako Stanisław Wokulski, chcę wpisać cel wydatku (np. "Kareta dla panny Izabeli") oraz kwotę, a także zaznaczyć przełącznikiem (Switch), czy wydatek był konieczny, aby dokładnie śledzić moje koszty.

- US3 (Saldo): Jako właściciel sklepu, chcę zawsze widzieć na samej górze ekranu aktualne, zsumowane saldo, aby jednym rzutem oka ocenić kondycję finansową spółki.

## ⚙️ Workflow Aplikacji (Krok po kroku)
Zobaczmy, jak powyższe historie przekładają się na działanie naszej aplikacji i magię Jetpack Compose:

- Krok 1: Otwarcie Księgi (Inicjalizacja)
  - Co widzi użytkownik: Aplikacja się uruchamia. Saldo wynosi 0.00 Rubli. Pola formularzy są puste. Lista transakcji jest pusta.
  - Co robi Compose:
    - Zmienna transactions (nasz stan) to pusta lista [].
    - Interfejs generuje się po raz pierwszy (Initial Composition).

- Krok 2: Subiekt księguje zysk (Akcja i Zdarzenie)
  - Co widzi użytkownik: Rzecki w sekcji "Przychody" wpisuje "Parasol" i kwotę "15". Klika przycisk "Zapisz".
  - Co robi Compose:
    - Przycisk w `IncomeForm` uruchamia funkcję `onClick`.
    - Zgłasza zdarzenie wyżej (Callback) do `MainScreen`.
    - Pola tekstowe w formularzu Rzeckiego automatycznie się czyszczą, gotowe na kolejny wpis.

- Krok 3: Przeliczenie majątku (Aktualizacja Stanu)
  - Co widzi użytkownik: Ułamek sekundy później saldo na górze ekranu zmienia się na 15.00 Rubli, a na dole pojawia się zielona karta z napisem "Parasol - 15 Rubli".
  - Co robi Compose:
    -  Nowy obiekt Transaction("Parasol", 15.0, false) trafia do listy `transactions`.
    -  Lista jest obserwowana (mutableStateListOf), więc Compose natychmiast zauważa zmianę.

- Krok 4: Szef księguje wydatek (Rekompozycja)
  - Co widzi użytkownik: Wokulski w sekcji "Wydatki" wpisuje "Kwiaty", kwotę "10", zaznacza Switch "Wydatek zbyteczny" i klika "Zapisz".
  - Co robi Compose: Analogiczny Callback z `ExpenseForm` trafia do `MainScreen`.
  - Obiekt `Transaction("Kwiaty", 10.0, true)` trafia na listę.

- Finał:
  - Compose ponownie rysuje (Rekomponuje) tylko te elementy, które zależą od listy.
  - Saldo aktualizuje się do 5.00 Rubli, a na liście pojawia się nowa, czerwona karta.

