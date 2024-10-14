# Notepad-Android-App
This project demonstrates step by step Android development principles and best practices. It's built with **Kotlin**, adhering to the **Waterfall** development model. To ensure clean and maintainable code, it leverages **Clean Architecture**, **MVVM** design pattern and **Hilt** dependency injection. Notes are efficiently stored and retrieved by using **Room database** while **Coroutine** and **Flow** effectively manage concurrent operations. For developing user interface, it utilizes **Jetpack Compose**. To guaranty the code quality and correctness, the project incorporates **Unit test** for data layer APIs and use cases, **Instrumentation test** for UI components, and **Integration test** to verify the entire app flow.


## Contents
- [Functional Requirements](#functional-requirements)
- [UI Design](#ui-design)
- [App Architecture](#app-architecture)
- [Commits](#commits)



## Functional requirements
- Create note
- Edit note
- Delete note
- Show single note in one screen
- Show all the notes in a list (with last edited item in the top)



## UI design
![Figma UI design for Notepad app](extra_resources/figma_notepad_screens.png)



## App Architecture
![Notepad App Architecture](extra_resources/notepad_app_architecture_design.png)



## Commits
- [Adding empty Android Studio project](https://github.com/gobinda1547/Notepad-Android-App/pull/3)
- [Configured Hilt dependency injection library](https://github.com/gobinda1547/Notepad-Android-App/pull/4)
- [Completed data layer implementation with unit test case](https://github.com/gobinda1547/Notepad-Android-App/pull/5)
- [Completed domain layer implementation with unit test cases](https://github.com/gobinda1547/Notepad-Android-App/pull/6)
- [Note list screen implementation done](https://github.com/gobinda1547/Notepad-Android-App/pull/7)
- [Add or edit note screen implementation done](https://github.com/gobinda1547/Notepad-Android-App/pull/8)
- [Show note screen implementation done](https://github.com/gobinda1547/Notepad-Android-App/pull/9)
- [Changed colors for some components & applied proguard for release build](https://github.com/gobinda1547/Notepad-Android-App/pull/10)
- [Completed writing instrumentation tests for all the UI components](https://github.com/gobinda1547/Notepad-Android-App/pull/11)
- [Completed writing Integration tests](https://github.com/gobinda1547/Notepad-Android-App/pull/12)