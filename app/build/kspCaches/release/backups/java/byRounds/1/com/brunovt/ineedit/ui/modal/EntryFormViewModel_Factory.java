package com.brunovt.ineedit.ui.modal;

import androidx.lifecycle.SavedStateHandle;
import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase;
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase;
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class EntryFormViewModel_Factory implements Factory<EntryFormViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<UpsertEntryUseCase> upsertEntryProvider;

  private final Provider<MarkDoneUseCase> markDoneProvider;

  private final Provider<MoveColumnUseCase> moveColumnProvider;

  private final Provider<EntryRepository> repositoryProvider;

  public EntryFormViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider,
      Provider<EntryRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.upsertEntryProvider = upsertEntryProvider;
    this.markDoneProvider = markDoneProvider;
    this.moveColumnProvider = moveColumnProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public EntryFormViewModel get() {
    return newInstance(savedStateHandleProvider.get(), upsertEntryProvider.get(), markDoneProvider.get(), moveColumnProvider.get(), repositoryProvider.get());
  }

  public static EntryFormViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<UpsertEntryUseCase> upsertEntryProvider,
      javax.inject.Provider<MarkDoneUseCase> markDoneProvider,
      javax.inject.Provider<MoveColumnUseCase> moveColumnProvider,
      javax.inject.Provider<EntryRepository> repositoryProvider) {
    return new EntryFormViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(upsertEntryProvider), Providers.asDaggerProvider(markDoneProvider), Providers.asDaggerProvider(moveColumnProvider), Providers.asDaggerProvider(repositoryProvider));
  }

  public static EntryFormViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider,
      Provider<EntryRepository> repositoryProvider) {
    return new EntryFormViewModel_Factory(savedStateHandleProvider, upsertEntryProvider, markDoneProvider, moveColumnProvider, repositoryProvider);
  }

  public static EntryFormViewModel newInstance(SavedStateHandle savedStateHandle,
      UpsertEntryUseCase upsertEntry, MarkDoneUseCase markDone, MoveColumnUseCase moveColumn,
      EntryRepository repository) {
    return new EntryFormViewModel(savedStateHandle, upsertEntry, markDone, moveColumn, repository);
  }
}
