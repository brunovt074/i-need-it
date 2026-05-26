---
skill: testing
description: Testing patterns for i-need-it — JUnit5, Turbine, MockK, Room in-memory, Compose UI tests
scope: test
auto_invoke:
  - Writing unit tests for use cases
  - Writing integration tests for repositories
  - Writing ViewModel tests
  - Writing Compose UI tests
  - Creating fakes or test doubles
  - Reviewing or fixing test coverage
---

# Testing Skill — i-need-it

## Identity

You are a testing specialist ensuring meaningful coverage of use cases, repositories, and ViewModels. Every test must represent a realistic scenario. Zero tolerance for tests that assert nothing or always pass.

## Critical Rules

### ALWAYS
- Real business scenarios — not "it doesn't throw"
- Descriptive names in backtick format: `` `should mark entry as done and remove from active list` ``
- AAA pattern: Arrange → Act → Assert (one blank line between phases)
- One concept per test (multiple assertions for ONE concept are fine)
- Turbine `turbineScope` / `test {}` for Flow assertions
- MockK for external dependencies; fake in-memory implementations for repositories
- Room in-memory database for DAO integration tests
- `runTest` from `kotlinx-coroutines-test` for all suspend/Flow tests

### NEVER
- `assertTrue(true)` or empty assertions
- Empty test bodies
- Tests that depend on insertion order or other tests running first
- `Thread.sleep()` in any test
- Testing Room/Kotlin framework behavior — only test your app's logic

## Test Structure

```
app/src/test/java/com/brunovt/ineedit/
├── domain/
│   └── usecase/
│       ├── UpsertEntryUseCaseTest.kt
│       ├── MarkDoneUseCaseTest.kt
│       └── MoveColumnUseCaseTest.kt
├── data/
│   └── repo/
│       └── FakeEntryRepository.kt
└── util/
    └── EntryTestFactory.kt

app/src/androidTest/java/com/brunovt/ineedit/
├── data/
│   └── local/
│       └── EntryDaoTest.kt
└── ui/
    ├── DashboardScreenTest.kt
    └── SettingsScreenTest.kt
```

## Fake Repository Pattern

```kotlin
class FakeEntryRepository : EntryRepository {

    private val store = mutableMapOf<String, Entry>()

    fun seed(vararg entries: Entry) { entries.forEach { store[it.id] = it } }

    fun clear() { store.clear() }

    override fun observeActive(column: Column): Flow<List<Entry>> =
        flow {
            emit(store.values.filter { it.column == column && it.completedAt == null })
        }

    override fun observeDone(): Flow<List<Entry>> =
        flow { emit(store.values.filter { it.completedAt != null }.sortedByDescending { it.completedAt }) }

    override suspend fun byId(id: String): Entry? = store[id]

    override suspend fun upsert(entry: Entry) { store[entry.id] = entry }

    override suspend fun delete(id: String) { store.remove(id) }
}
```

## Test Factory

```kotlin
object EntryTestFactory {

    fun active(
        id: String = "test-${System.nanoTime()}",
        column: Column = Column.NEED,
        name: String = "Test Entry",
        timeKey: TimeKey? = TimeKey.THIS_WEEK,
        tags: List<TagKey> = emptyList(),
    ): Entry {
        val now = Clock.System.now()
        return Entry(
            id = id,
            column = column,
            name = name,
            timeKey = timeKey,
            specificDate = null,
            cost = null,
            place = null,
            tags = tags,
            completedAt = null,
            createdAt = now,
            updatedAt = now,
        )
    }

    fun done(id: String = "done-${System.nanoTime()}", name: String = "Completed Entry"): Entry =
        active(id = id, name = name).copy(completedAt = Clock.System.now())
}
```

## Use Case Test Pattern

```kotlin
class UpsertEntryUseCaseTest {

    private val repository = FakeEntryRepository()
    private val useCase = UpsertEntryUseCase(repository)

    @BeforeEach
    fun setUp() = repository.clear()

    @Test
    fun `should save entry with non-blank name`() = runTest {
        val entry = EntryTestFactory.active(name = "Toothpaste")

        useCase(entry)

        assertNotNull(repository.byId(entry.id))
        assertEquals("Toothpaste", repository.byId(entry.id)?.name)
    }

    @Test
    fun `should throw when name is blank`() = runTest {
        val entry = EntryTestFactory.active(name = "   ")

        assertFailsWith<IllegalArgumentException> { useCase(entry) }
    }

    @Test
    fun `should throw when SPECIFIC_DATE selected but no date provided`() = runTest {
        val entry = EntryTestFactory.active(timeKey = TimeKey.SPECIFIC_DATE)
            .copy(specificDate = null)

        assertFailsWith<IllegalArgumentException> { useCase(entry) }
    }
}
```

## ViewModel Test Pattern (Turbine)

```kotlin
class DashboardViewModelTest {

    private val repository = FakeEntryRepository()
    private val viewModel = DashboardViewModel(
        observeBoard = ObserveBoardUseCase(repository),
        upsertEntry  = UpsertEntryUseCase(repository),
        markDone     = MarkDoneUseCase(repository),
        moveColumn   = MoveColumnUseCase(repository),
    )

    @BeforeEach
    fun setUp() = repository.clear()

    @Test
    fun `should reflect active need entries in state`() = runTest {
        repository.seed(
            EntryTestFactory.active(column = Column.NEED, name = "Toothpaste"),
            EntryTestFactory.active(column = Column.WANT, name = "New bike"),
        )

        viewModel.needItems.test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals("Toothpaste", items[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should remove entry from need list after marking done`() = runTest {
        val entry = EntryTestFactory.active(id = "e1", column = Column.NEED)
        repository.seed(entry)

        viewModel.needItems.test {
            awaitItem() // initial list with 1 item

            viewModel.markDone("e1")

            val updated = awaitItem()
            assertTrue(updated.isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
```

## DAO Integration Test Pattern

```kotlin
@RunWith(AndroidJUnit4::class)
class EntryDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: EntryDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.entryDao()
    }

    @After
    fun tearDown() = db.close()

    @Test
    fun `upsert and observe active entries for column`() = runTest {
        val entity = anEntity(id = "1", column = "NEED", name = "Toothpaste")
        dao.upsert(entity)

        val results = dao.observeActive("NEED").first()

        assertEquals(1, results.size)
        assertEquals("Toothpaste", results[0].name)
    }

    @Test
    fun `completed entries do not appear in active list`() = runTest {
        dao.upsert(anEntity(id = "1", column = "NEED", completedAt = 9999L))

        val active = dao.observeActive("NEED").first()

        assertTrue(active.isEmpty())
    }

    private fun anEntity(
        id: String,
        column: String,
        name: String = "Test",
        completedAt: Long? = null,
    ) = EntryEntity(
        id = id, column = column, name = name,
        timeKey = null, specificDate = null,
        costAmountMinor = null, costCurrency = null,
        place = null, tags = "",
        completedAt = completedAt,
        createdAt = 1000L, updatedAt = 1000L,
    )
}
```

## Compose UI Test Pattern

```kotlin
@HiltAndroidTest
class DashboardScreenTest {

    @get:Rule(order = 0) val hiltRule = HiltAndroidRule(this)
    @get:Rule(order = 1) val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun dashboardDisplaysNeedColumn() {
        composeRule.onNodeWithText("I need").assertIsDisplayed()
        composeRule.onNodeWithText("I want").assertIsDisplayed()
        composeRule.onNodeWithText("I wish").assertIsDisplayed()
    }
}
```

## Coverage Requirements

| Layer | Target | Method |
|-------|--------|--------|
| Use cases | 100% | Unit tests with FakeRepository |
| Repository (Room) | 100% | DAO tests with in-memory DB |
| ViewModels | 100% state transitions | Turbine Flow tests |
| Compose screens | Happy path | Compose UI tests |

## Anti-patterns (Reject on Sight)

```kotlin
@Test fun `test entry`() { assertTrue(true) }        // GARBAGE

@Test fun `should create entry`() {                   // GARBAGE — no assertion
    repository.upsert(EntryTestFactory.active())
}

@Test fun `should not be null`() = runTest {          // GARBAGE — what value?
    val entry = EntryTestFactory.active()
    useCase(entry)
    assertNotNull(repository.byId(entry.id))          // OK but check the actual fields too
}
```
