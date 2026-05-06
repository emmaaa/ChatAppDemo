# ChatApp

Simple one-to-one chat demo-app built with Kotlin, Jetpack Compose, Room, and Hilt.

## 1) Problem Understanding

The app renders a chat conversation where:

- messages are shown in chronological order with newer messages at the bottom
- consecutive messages under 20s from the same sender are grouped into one bubble
- section headers are inserted when there is a gap over 1 hour between message groups
- sending blank input is blocked (nobody likes pointless spamming!)
- a simulated reply is shown after the current user sends a message

## 2) Assumptions

1. Authentication/login is not required
2. The app shows a single conversation with fixed demo participants
3. Persistence is local-only (Room), no backend sync
4. Real-time delivery is out of scope, and the other-user replies are simulated
5. Message editing/deleting, delivery/read receipts, and attachments are out of scope
6. Pagination is not required for the expected local dataset size

## 3) Tech Stack

- Kotlin
- Jetpack Compose (Material3)
- Room
- Hilt (DI)
- Coroutines + Flow
- JUnit + MockK + kotlinx-coroutines-test (unit tests)

## 4) Architecture

The project follows a lightweight clean layering approach:

- Presentation
  - Compose UI + `ChatViewModel`
  - UI models: `ChatUiState`, `ChatListItem`
  
- Domain
  - core model (`Message`)
  - use cases (`GetMessagesUseCase`, `SendMessageUseCase`)
  - repository contract (`MessageRepository`)
  
- Data
  - Room entities/DAO/database
  - repository implementation + mapper
  
- DI
  - Hilt modules for database and repository bindings

Dependency direction is inward:

- presentation -> domain
- data -> domain
- domain knows nothing about presentation/data frameworks

Key Kotlin/Android techniques used:

- `StateFlow` + `combine` + `stateIn` for single-source UI state
- extension functions for transformation readability
- sealed UI model (`ChatListItem`) 
- immutable data models with copy semantics
- coroutine structured concurrency (`viewModelScope`)
- DI via Hilt with constructor injection

## 5) Key Implementation Decisions

1. **Thin useCase layer**
   - Business rules live close to domain behavior (e.g. trimming input in `SendMessageUseCase`)
   - Keeps ViewModel focused on orchestration and UI state

2. **UI list shaping done in ViewModel**
   - Raw messages are transformed into display items (`SectionHeader` + `MessageItem`) before reaching Compose.
   - UI stays mostly declarative rendering.

3. **Room + Flow for reactive updates**
   - Database emits updates automatically; ViewModel combines message flow with local UI state (input + typing).

4. **Hilt for composition root**
   - Reduces manual wiring and keeps constructor dependencies explicit.

## 6) Trade-offs

- **Local-only persistence (Room) over backend sync**
  - Keeps the app deterministic, fast to run, and easy to test.
  - Trade-off: no real-time multi-device sync/conflict handling.

- **ViewModel handles list shaping (grouping + headers)**
  - Keeps composables mostly dumb/render-only and easier to reason about.
  - Trade-off: presentation logic is concentrated in one place and can grow quickly.

- **Simulated replies/typing instead of network events**
  - Lets me demonstrate chat UX behaviour easily without network complexity.
  - Trade-off: not representative of production socket/push events

- **Unit-test-heavy approach over broad UI/instrumentation coverage**
  - Fast feedback on core message logic
  - Trade-off: less end-to-end UI guarantees

## 7) KISS Boundaries / Non-goals

- no network layer
- no retry/error UI states
- no message editing/deleting
- no pagination (small local dataset)

## 8) Cheeky Bonus Points
- Added an app icon
- Started Theming with Material3 and dark mode support
- Simulated "typing..." indicator for the other person during the delay before their reply is shown
- Toast feedback on back and more options buttons with suggestions of what they would do
- Moved date/time formatting to a dedicated formatter for easier testing

## 9) Testing Strategy

Current unit tests cover:

- Domain model behavior (`MessageTest`)
- Mapper correctness (`MessageMapperTest`)
- Repository mapping/insert behavior (`MessageRepositoryImplTest`)
- Use case behavior (`GetMessagesUseCaseTest`, `SendMessageUseCaseTest`)
- Key chat presentation logic (`ChatViewModelTest`), including:
  - grouping within threshold
  - non-grouping at threshold boundary
  - section header insertion on long gap
  - send guard for blank input
- One basic UI test (`ChatScreenUiTest`) launches the app with a simple assertion
- Manual accessibility testing of adaptive text sizes and content descriptions

## 10) Theoretical future improvements / "If I had more time..."

Testing: 
- There is only one basic UI test, this can be expanded to cover more AC
- Same timestamp-window but different sender should not group
- Snapshot tests, PACT tests, e2e tests...
- As UI tests grow, add a test framework to improve readability and maintainability

App Features:
- Continue Material3 colour scheme theming (secondary/error/surface roles etc)
- Back button could go back to the messages list
- More options button can have features like block, mute, and search message history
- Avatar and username is hardcoded, this can be added to data model 
- Accessibility can be improved by grouping avatar with message (currently read separately)
- Ignoring the lack of back-end and assuming this is always just a demo app, it could be modified 
  with a developer menu to toggle between different UI modes for POC purposes for designers or stakeholders
