# 📱 Reactive vs Imperative Programming in Android (TFM)

![Kotlin](https://img.shields.io/badge/Kotlin-1.5.30-grey?style=flat&logo=kotlin&logoColor=white&labelColor=7F52FF)
![Android](https://img.shields.io/badge/Android-SDK%2029-grey?style=flat&logo=android&logoColor=white&labelColor=green)
![Min SDK](https://img.shields.io/badge/Min%20SDK-21-grey?style=flat&labelColor=green)
![Coroutines](https://img.shields.io/badge/Coroutines-1.3.2-grey?style=flat&logo=kotlin&logoColor=white&labelColor=3264ff)
![Koin](https://img.shields.io/badge/Koin-2.0.1-grey?style=flat&logo=kotlin&logoColor=white&labelColor=FFD600)
![Arrow](https://img.shields.io/badge/Arrow-0.10.0-grey?style=flat&labelColor=000000)

This project is the Master's Thesis (TFM) for the Master's Degree in Mobile Engineering. The primary goal is to analyze and compare the performance, readability, and maintainability of **Reactive Programming** versus **Imperative/Functional Programming** in Android application development.

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
Analysis of computational efficiency and memory footprint during heavy data transformations.
- **Reactive/Lazy Model**: Utilizes **Kotlin Sequences** and **Flows**. Operations are performed lazily, meaning elements are processed through the transformation chain one by one. This significantly reduces memory overhead when dealing with thousands of items.
- **Imperative/Eager Model**: Utilizes standard **Kotlin Collection functions** (`map`, `filter`). These operations are eager and create intermediate collections for every step of the chain, which can lead to performance bottlenecks and higher GC (Garbage Collector) pressure in memory-constrained environments.

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

The application includes built-in tools to monitor the behavior of each paradigm. Through interactive charts (MPAndroidChart), users can observe in real-time how data flows through the system and the resulting impact on UI responsiveness and resource consumption.

---

## 🏁 Conclusions

The research conducted in this project highlights several key findings:
1. **Maintainability**: The **Reactive** approach, while having a steeper learning curve, drastically reduces boilerplate code in complex synchronization scenarios, leading to more robust and less error-prone codebases.
2. **Performance**: For simple, one-shot operations, the **Imperative** model remains slightly more performant due to lower abstraction overhead. However, the **Reactive/Lazy** model scales significantly better when processing large volumes of data or high-frequency events (sensors).
3. **Developer Experience**: The choice between paradigms should be driven by the data's nature. "State-heavy" applications benefit more from the Reactive model, whereas "Action-heavy" or simple utility apps may find the Imperative/Functional approach more intuitive and straightforward.

---

## ⚙️ Project Setup

### Requirements
- Android Studio Bumblebee or higher.
- JDK 11.
- Android SDK 29.

### Installation
```bash
git clone https://github.com/your-username/TFM-Jorge-Sanzo.git
cd TFM-Jorge-Sanzo
./gradlew assembleDebug
```

---

**Author:** Jorge Sanzo  
**Master's Degree:** Mobile Engineering
