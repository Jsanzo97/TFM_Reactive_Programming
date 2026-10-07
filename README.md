# 📱 Reactive vs Imperative Programming in Android (TFM)

![Kotlin](https://img.shields.io/badge/Kotlin-1.5.30-grey?style=flat&logo=kotlin&logoColor=white&labelColor=7F52FF)
![Android](https://img.shields.io/badge/Android-SDK%2029-grey?style=flat&logo=android&logoColor=white&labelColor=green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-21-grey?style=flat&labelColor=green)
![Coroutines](https://img.shields.io/badge/Coroutines-1.3.2-grey?style=flat&logo=kotlin&logoColor=white&labelColor=3264ff)
![Koin](https://img.shields.io/badge/Koin-2.0.1-grey?style=flat&logo=kotlin&logoColor=white&labelColor=3E0C59)
![Arrow](https://img.shields.io/badge/Arrow-0.10.0-grey?style=flat&labelColor=000000)

This project is the Master's Thesis (TFM) for the Master's Degree in Mobile Computing at Universidad Pontificia de Salamanca (October 2022). The primary goal is to analyze and compare the performance, readability, and maintainability of **Reactive Programming** versus **Imperative/Functional Programming** in Android application development.

> 📄 The full thesis report is written in Spanish: [`docs/TFM_JorgeSanzoHernando.pdf`](docs/TFM_JorgeSanzoHernando.pdf)

---

## 🏛️ Architecture

The project follows **Clean Architecture** principles and the **MVVM** presentation pattern, organized into independent Gradle modules to ensure decoupling:

```
app         → domain, data, database, common, sensors, collections
domain      → (no dependencies)
data        → domain, common
database    → domain, common
sensors     → domain, common
collections → domain, common
common      → (shared utilities)
```

### Module Responsibility
| Module | Responsibility |
|---|---|
| `:app` | UI layer (Activities, Fragments, ViewModels) and DI Graph (Koin). |
| `:domain` | Pure business logic: Use Cases (Reactive vs Functional), Entities, and Repository Interfaces. |
| `:data` | Repository implementations and data source orchestration. |
| `:database` | Local persistence with Room (DAOs, DB Entities). |
| `:sensors` | Device sensor management (Accelerometer, Brightness, Orientation). |
| `:collections` | Processing algorithms for large-scale data volumes. |

---

## 🧪 Comparison Scenarios

The project evaluates both paradigms across three critical technical areas:

### 1. Data Management (Database)
This scenario compares how the UI stays synchronized with the underlying data source.
- **Reactive Model**: Implementation using **Room + Flow**. The UI observes a continuous data stream. Any change in the database (insert, update, or delete) is automatically propagated to the UI without manual intervention, ensuring a "single source of truth".
- **Imperative/Functional Model**: Implementation using one-shot **suspend functions** that return a static `List`. The UI must explicitly re-request the data or implement manual refresh logic whenever an update is expected.

### 2. Real-Time Sensors
Evaluation of high-frequency data streams originating from hardware sensors.
- **Reactive Model**: Uses **`callbackFlow`** to wrap the `SensorEventListener`. This transforms the traditional callback-based API into a cold Stream (Flow), allowing the application to use powerful operators like `filter`, `debounce`, or `combine` directly on the hardware events.
- **Imperative Model**: Follows the traditional **Listener pattern**. State management, registration, and synchronization are handled manually within the UI controller or helper classes, often leading to more boilerplate and complex lifecycle management.

### 3. Processing Large Collections
Analysis of execution time during heavy data transformations.
- **Reactive/Lazy Model**: Utilizes **Kotlin Sequences**. Operations are performed lazily, meaning elements are processed through the transformation chain one by one, without creating intermediate collections.
- **Imperative/Eager Model**: Utilizes standard **Kotlin Collection functions** (`map`, `filter`). These operations are eager and create intermediate collections for every step of the chain, which can lead to performance bottlenecks.

---

## 🛠️ Tech Stack

- **Coroutines & Flow** — Core engine for asynchrony and the reactive paradigm.
- **Koin** — Pragmatic and lightweight Dependency Injection.
- **Arrow** — Functional programming library for Kotlin (using `Either`, `Option`).
- **Room** — Persistence library with native support for reactive streams.
- **Navigation Component** — Fragment-based navigation management with `SafeArgs`.
- **MPAndroidChart** — Real-time visualization of sensor data and performance metrics.
- **ViewModel & LiveData** — Lifecycle-aware components for the presentation layer.

---

## 📊 Results Visualization

The application includes built-in tools to monitor the behavior of each paradigm. Through interactive charts (MPAndroidChart), users can observe in real-time how data flows through the system and compare execution times and data-capture effectiveness between approaches.

---

## 🏁 Conclusions

The research conducted in this project highlights several key findings:
1. **Database operations**: No notable difference in efficiency or execution time between Flow + coroutines and lists + async tasks. The reactive code is simpler, since threads and data are handled automatically, and the UI always reflects the current state of the database. It pays off when data changes frequently or many queries are needed; otherwise, either approach works. With plain lists, the view only updates when data is requested again, so it can show stale data.
2. **Real-time sensors**: The imperative approach needs a polling interval. A long interval loses values (effectiveness dropped to about 10–16% in the test runs), while a very short one makes redundant queries (above 190%). The reactive approach receives every value emitted by the sensor (100%) with no interval to tune.
3. **Large collections**: With heavy chains of operations, **Sequences** were clearly faster and more stable than **Lists**, and their cost depends on the number of elements processed rather than the total size. For small collections or few operations, plain lists are faster, so the choice should depend on the workload.

Memory and resource usage were not measured and are listed as future work.

---

## ⚙️ Project Setup

### Requirements
- Android Studio Bumblebee or higher.
- JDK 11.
- Android SDK 29.

### Installation
```bash
git clone https://github.com/Jsanzo97/TFM_Reactive_Programming.git
cd TFM_Reactive_Programming
./gradlew assembleDebug
```

---

**Author:** Jorge Sanzo  
**Master's Degree:** Mobile Computing  
**Final Grade:** 9.5 / 10
