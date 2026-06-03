# Armenia Travel Hub (Multi-Module Android Application)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.x-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Compose-2024.x-green.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Architecture](https://img.shields.io/badge/Architecture-MVVM%20+%20Clean-orange.svg?style=flat)]()
[![Platform](https://img.shields.io/badge/Platform-Android-brightgreen.svg?style=flat&logo=android)](https://developer.android.com)
[![Google Play](https://img.shields.io/badge/Google%20Play-Published-green.svg?style=flat&logo=googleplay)](https://play.google.com/store/apps/details?id=am.mojtaba.armengo)

A production-ready, highly scalable, and enterprise-grade multi-module Android application designed specifically for tourists visiting Armenia. The project is engineered with strict adherence to **Clean Architecture** principles, **MVVM (Model-View-ViewModel)** pattern, and reactive programming.

---

## 🚀 Production Release

The production version of the user-facing application is officially published and available on the Google Play Store:

👉 **[Download on Google Play Store](https://play.google.com/store/apps/details?id=am.mojtaba.armengo)**

---

## 🏗 System Architecture & Design Philosophy

This repository showcases a decoupled, maintainable, and test-driven architecture optimized for team scalability and parallel development.

### 📐 Clean Architecture Layers
The project enforces a strict unidirectional data flow and separation of concerns across three core structural pillars:
1. **Presentation Layer:** Powering the UI completely via **Jetpack Compose** (declarative UI framework) and managing UI state deterministically using **MVVM** patterns backed by `StateFlow` and `SharedFlow`.
2. **Domain Layer:** The pure, framework-agnostic core containing business logic, entity definitions, and highly atomic **Use Cases (Interactors)**.
3. **Data Layer:** The single source of truth managing data operations. It abstracts data sourcing using the **Repository Pattern**, seamlessly interacting with **Cloud Firestore** and local persistence.

```
[Presentation (Compose / ViewModel)] ──> [Domain (Use Cases / Entities)] <── [Data (Repositories / Firestore)]
```

### 🧱 Modular Structure (Multi-Module Gradle Setup)
To guarantee fast compilation times (via Gradle remote build caching) and strict separation of API boundaries, the project leverages a highly optimized **Layered and Feature-Based Modularization** strategy:

- `:app:user` - The primary application entry point tailored with user-centric modules (Itinerary planner, tour booking, interactive maps, dynamic currency converter).
- `:app:admin` - The administrative control plane allowing content moderation, tour creation, analytics dashboard, and real-time support management.
- `:core:database` - Unified reactive data client abstraction over **Firebase Firestore** and local storage wrappers.
- `:core:network` - Global networking configurations, real-time sync listeners, and offline resilience strategies.
- `:core:designsystem` - Shared Atom-based design tokens, custom Material 3 UI primitives, and unified themes ensuring absolute design consistency.
- `:core:domain` - Shared enterprise models and base interactors.

---

## 🛠 Advanced Tech Stack & Libraries

- **Language:** [Kotlin](https://kotlinlang.org/) (100% Coroutines & Asynchronous Flow execution)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with Semantic Material 3 design tokens.
- **Asynchronous Flow:** Kotlin Coroutines (`Structured Concurrency`) and reactive `Cold/Hot Flows` (`StateFlow`, `SharedFlow`).
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) (Dagger-based compile-time safe DI engine).
- **Backend Infrastructure:** [Firebase Cloud Firestore](https://firebase.google.com/docs/firestore) utilizing offline persistence, multi-indexed queries, and real-time reactive snapshots.
- **Navigation:** Compose Navigation with strict type-safe arguments encapsulation.
- **Image Loading:** Coil (Coroutines-backed performance-optimized asynchronous image decoding pipeline).

---

## 🧪 Quality Assurance & Test Engineering

The core architecture relies heavily on predictable behavior and robust validation.

- **Unit Testing (Fully Implemented):** Powered by `JUnit5` and `MockK`. Business logic inside the Domain layer (Use Cases) and state emissions inside the Presentation layer (ViewModels) are fully covered by unit tests to assert correct state transitions under simulated coroutine dispatchers.

---

## 🗺️ Work in Progress & Future Roadmap

The repository is under continuous integration, with the following advanced engineering phases currently being developed and merged:

- [ ] **Advanced Test Suites:** Integration of comprehensive repository layer testing and UI Assertion Automation using the [Compose UI Test] framework.
- [ ] **Automated CI/CD Pipeline:** Setting up GitHub Actions workflows to automate code analysis (`Detekt`/`Ktlint`), execute test matrices programmatically, and streamline continuous deployment to the Google Play Console tracks using Fastlane.
- [ ] **Architecture Validation:** Implementing `ArchUnit` rules to programmatically enforce dependency boundaries across feature modules.

---

## 📂 Project Directory Breakdown

```text
├── app/
│   ├── admin/                # Admin Management Control Plane
│   └── user/                 # Tourist Facing Client Application
├── core/
│   ├── database/             # Firestore Client Framework & Mappers
│   ├── designsystem/         # Shared Jetpack Compose Material 3 Component Library
│   ├── network/              # Real-time WebSocket / Firebase Synchronization Hooks
│   └── common/               # Shared Utilities, Extensions & Custom Annotations
└── buildSrc/                 # Centralized Kotlin DSL Dependency Management
```

---

## ⚙️ Engineering Prerequisites

- **IDE:** Android Studio Ladybug (or newer)
- **JDK:** Version 17+
- **Minimum SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 34 (Android 14)
- **Build System:** Gradle Kotlin DSL with Version Catalogs (`libs.versions.toml`)

---

## ▶️ Execution Pipeline

```bash
# Clone the enterprise repository
git clone https://github.com/your-username/armenia-travel-hub.git

# Initialize environment configurations and run baseline checks
./gradlew clean testDebugUnitTest

# Assemble and execute the Tourist Application
./gradlew :app:user:installDebug

# Assemble and execute the Admin Control Panel
./gradlew :app:admin:installDebug
```
