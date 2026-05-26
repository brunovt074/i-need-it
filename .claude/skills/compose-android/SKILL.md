---
skill: compose-android
description: Jetpack Compose + ViewModel + Hilt patterns for i-need-it Android screens
scope: ui
auto_invoke:
  - Creating Compose screens or components
  - Creating ViewModels
  - Wiring Hilt dependency injection in UI layer
  - Navigation changes
  - Bottom sheets and dialogs
  - Managing UI state
---

# Compose Android Skill — i-need-it

## Identity

You are a Jetpack Compose specialist building an Android application with Hilt DI and ViewModel-based state management. You keep business logic in use cases and ViewModels, never in composables.

## Critical Rules

### ALWAYS
- `@HiltViewModel` on every ViewModel; inject with `hiltViewModel()`
- `StateFlow` for UI state — never `LiveData`
- `collectAsStateWithLifecycle()` in composables (not `collectAsState()`)
- Sealed interface for screen states: `Loading`, `Content`, `Error`
- One ViewModel per screen — no shared mutable ViewModel state between screens
- All strings via `stringResource(R.string.…)` — no hardcoded text
- Touch targets ≥ 48.dp for all interactive elements

### NEVER
- Business logic in composables
- `remember { mutableStateOf() }` for data that belongs in ViewModel
- Manual ViewModel instantiation — always inject via Hilt
- Database or network calls on the main thread
- Hardcoded strings in composable files

## ViewModel Pattern

```kotlin
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val observeBoard: ObserveBoardUseCase,
    private val upsertEntry: UpsertEntryUseCase,
    private val markDone: MarkDoneUseCase,
    private val moveColumn: MoveColumnUseCase,
) : ViewModel() {

    private val _needItems = MutableStateFlow<List<Entry>>(emptyList())
    val needItems: StateFlow<List<Entry>> = _needItems.asStateFlow()

    private val _wantItems = MutableStateFlow<List<Entry>>(emptyList())
    val wantItems: StateFlow<List<Entry>> = _wantItems.asStateFlow()

    private val _wishItems = MutableStateFlow<List<Entry>>(emptyList())
    val wishItems: StateFlow<List<Entry>> = _wishItems.asStateFlow()

    init {
        viewModelScope.launch {
            launch { observeBoard(Column.NEED).collect { _needItems.value = it } }
            launch { observeBoard(Column.WANT).collect { _wantItems.value = it } }
            launch { observeBoard(Column.WISH).collect { _wishItems.value = it } }
        }
    }

    fun markDone(entryId: String) {
        viewModelScope.launch {
            runCatching { markDone(entryId) }
                .onFailure { /* log */ }
        }
    }
}
```

## Form ViewModel Pattern

```kotlin
data class EntryFormState(
    val name: String = "",
    val timeKey: TimeKey? = null,
    val specificDate: LocalDate? = null,
    val costAmount: String = "",
    val costCurrency: String = "ARS",
    val place: String = "",
    val selectedTags: Set<TagKey> = emptySet(),
    val nameError: String? = null,
    val isSaving: Boolean = false,
    val savedSuccessfully: Boolean = false,
)

@HiltViewModel
class EntryFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val upsertEntry: UpsertEntryUseCase,
    private val repository: EntryRepository,
) : ViewModel() {

    private val entryId: String? = savedStateHandle["entryId"]
    private val column: Column = Column.valueOf(savedStateHandle["column"] ?: "NEED")

    private val _state = MutableStateFlow(EntryFormState())
    val state: StateFlow<EntryFormState> = _state.asStateFlow()

    init {
        entryId?.let { id ->
            viewModelScope.launch {
                repository.byId(id)?.let { entry -> populateForm(entry) }
            }
        }
    }

    fun onNameChange(value: String) {
        _state.update { it.copy(name = value, nameError = null) }
    }

    fun onTimeKeySelect(key: TimeKey) {
        _state.update {
            it.copy(
                timeKey = key,
                specificDate = if (key != TimeKey.SPECIFIC_DATE) null else it.specificDate
            )
        }
    }

    fun onTagToggle(tag: TagKey) {
        _state.update { s ->
            val tags = if (tag in s.selectedTags) s.selectedTags - tag else s.selectedTags + tag
            s.copy(selectedTags = tags)
        }
    }

    fun save() {
        val s = _state.value
        if (s.name.isBlank()) {
            _state.update { it.copy(nameError = "required") }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val now = Clock.System.now()
            val entry = Entry(
                id = entryId ?: uuid4().toString(),
                column = column,
                name = s.name.trim(),
                timeKey = s.timeKey,
                specificDate = s.specificDate,
                cost = s.costAmount.toLongOrNull()?.let { Money(it, s.costCurrency) },
                place = s.place.trim().ifBlank { null },
                tags = s.selectedTags.toList(),
                completedAt = null,
                createdAt = if (entryId == null) now else /* keep original */ now,
                updatedAt = now,
            )
            runCatching { upsertEntry(entry) }
                .onSuccess { _state.update { it.copy(isSaving = false, savedSuccessfully = true) } }
                .onFailure { _state.update { it.copy(isSaving = false) } }
        }
    }
}
```

## Screen Composable Pattern

```kotlin
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onOpenEntry: (entryId: String?, column: Column) -> Unit,
) {
    val needItems by viewModel.needItems.collectAsStateWithLifecycle()
    val wantItems by viewModel.wantItems.collectAsStateWithLifecycle()
    val wishItems by viewModel.wishItems.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { DashboardTopBar() },
        bottomBar = { AppBottomNav() },
    ) { padding ->
        AccordionLayout(
            modifier = Modifier.padding(padding),
            needItems = needItems,
            wantItems = wantItems,
            wishItems = wishItems,
            onItemTap = { entry -> onOpenEntry(entry.id, entry.column) },
            onAddTap = { column -> onOpenEntry(null, column) },
        )
    }
}
```

## Navigation Pattern

```kotlin
@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen(
                onOpenEntry = { id, col ->
                    navController.navigate("entry_form?entryId=$id&column=${col.name}")
                }
            )
        }
        composable(
            "entry_form?entryId={entryId}&column={column}",
            arguments = listOf(
                navArgument("entryId") { nullable = true; defaultValue = null },
                navArgument("column") { defaultValue = "NEED" },
            )
        ) {
            EntryFormSheet(onDismiss = { navController.popBackStack() })
        }
        composable("done") { DoneScreen() }
        composable("settings") { SettingsScreen() }
    }
}
```

## Bottom Sheet Pattern

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryFormSheet(
    onDismiss: () -> Unit,
    viewModel: EntryFormViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.savedSuccessfully) {
        if (state.savedSuccessfully) onDismiss()
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        ItemForm(
            state = state,
            onNameChange = viewModel::onNameChange,
            onTimeKeySelect = viewModel::onTimeKeySelect,
            onTagToggle = viewModel::onTagToggle,
            onSave = viewModel::save,
            onCancel = onDismiss,
        )
    }
}
```

## Hilt Module — UseCases

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideObserveBoardUseCase(repo: EntryRepository): ObserveBoardUseCase =
        ObserveBoardUseCase(repo)

    @Provides
    fun provideUpsertEntryUseCase(repo: EntryRepository): UpsertEntryUseCase =
        UpsertEntryUseCase(repo)

    @Provides
    fun provideMarkDoneUseCase(repo: EntryRepository): MarkDoneUseCase =
        MarkDoneUseCase(repo)

    @Provides
    fun provideMoveColumnUseCase(repo: EntryRepository): MoveColumnUseCase =
        MoveColumnUseCase(repo)
}
```

## Shared UI Conventions

### Empty State
```kotlin
@Composable
fun ColumnEmptyState(text: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().padding(vertical = 24.dp), contentAlignment = Alignment.Center) {
        Text(text, style = AppTypography.hand, color = LocalAppTokens.current.inkFaded)
    }
}
```

### Chip Row (time keys, tags)
- Single-row, horizontally scrollable when items overflow
- Selected chip: filled with column color; unselected: outlined
- Minimum tap target: 48.dp height

### Touch Targets
Every `clickable` or `Modifier.clickable` element must be at least 48.dp in both dimensions. Use `Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)` on icon buttons.

### Content Description
Every icon-only button must have `contentDescription = stringResource(R.string.…)`.
