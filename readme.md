# MotorcycleManager - Maintenance Tracker for Motocross (and Motorcycles)

**MotorcycleManager** is an inspiring project for anyone who want a small architecture and a long term easy updatable project   
This architecture works well for small project only, if you want a more reusable and modular approach, you should consider use module by features and keep the current layer approch inside them (presentation, viewmodel, domain, data, etc..)   
By splitting code in layer like this, you can easily add unit test and UI test easily inside project   

This project is also a simple and efficient Android app to manage the maintenance of your motocross bikes (and motorcycles in general).      
No more paper notebooks, forgotten oil changes, or manual checklists: everything is centralized, automatic, and accessible with a single tap.   

---

## Features

### Automatic Ride Hours Tracking
- Simply enter the hours ridden after each session.
- The app automatically calculates wear on consumables.

### Custom Consumables
- Create your own items: oil change, piston, filter, chain, etc.
- Set an interval in hours (e.g., oil change every 15h).
- Visualize wear with a **color-coded progress bar**.

### Pre-Ride Checklist
- Personalized safety checklist per bike.
- Checkboxes that auto-reset after each session.
- Examples: grease chain, WD40 on engine, blow out bike, check torque…

### Multi-Bike Support
- Manage multiple machines in one app.
- Each bike has its own hours, consumables, and checklists.

---

## App Screens

| Screen | Description |
|--------|-------------|
| **Home** | List of all your bikes + add button |
| **BikeAdd** | Add a bike with current hours |
| **BikeDetails** | Bike details: total hours, consumables, checklist |
| **ConsumableAdd / Update** | Create or edit a consumable |
| **CheckAdd / Update** | Add or edit a pre-ride check |

---

## Architecture & Tech Stack

Single-module project structured with **Clean Architecture**, every layer are split inside packages (presentation, domain, data, ..)

### Key Libraries

| Library | Purpose |
|---------|---------|
| **Jetpack Compose** | Modern, declarative UI |
| **Navigation Compose** | Type-safe screen navigation |
| **Room** | Local SQLite database |
| **Hilt** | Dependency injection |
| **Kotlin Serialization** | JSON handling |
| **Firebase Crashlytics & Analytics** | Crash reporting & usage stats |
| **Material3 + Extended Icons** | Consistent modern design |

> Only essential dependencies are listed. See `gradle/libs.versions.toml` for the full list.

---

## Build & Run

### Requirements
- Android Studio (Iguana or later)
- JDK 17
- Emulator or Android device (API 24+)

### Steps

```bash
git clone https://github.com/your-username/MotoTrack.git
cd MotoTrack
./gradlew build
```

Launch directly from Android Studio.

## Contributing

Contributions welcome!   
Fork → create feature/your-feature branch → PR with clear description.    

**MIT License - free to use, modify, and share.**