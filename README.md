AVATAR

# ChatApp

Simple one-to-one chat demo-app built with Kotlin, Jetpack Compose, Room, and Hilt.

## 1) Problem Understanding

The app renders a chat conversation where:

- messages are shown in chronological order from bottom to top
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

## 3) Architecture

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

## 4) Key Implementation Decisions 

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

## 5) KISS Boundaries / Non-goals

- no network layer
- no retry/error UI states
- no message editing/deleting
- no pagination (small local dataset)

## 6) Testing Strategy

Current unit tests cover:

- domain model behavior (`MessageTest`)
- mapper correctness (`MessageMapperTest`)
- repository mapping/insert behavior (`MessageRepositoryImplTest`)
- use case behavior (`GetMessagesUseCaseTest`, `SendMessageUseCaseTest`)
- key chat presentation logic (`ChatViewModelTest`), including:
  - grouping within threshold
  - non-grouping at threshold boundary
  - section header insertion on long gap
  - send guard for blank input

### Theoretical future improvements

Testing: 
- same timestamp-window but different sender should not group
- exactly one hour gap should not create a new section header
- positive send path assertion for trimmed non-blank input and sender id

App Features:
- introduce an explicit message-group UI model if bubble-level metadata grows
- move date/time formatting to a dedicated formatter for easier localization testing
- add instrumentation tests for key chat UI flows (send, scroll, grouping visuals)
- accessibility can be improved by grouping avatar with message (currently read separately)
