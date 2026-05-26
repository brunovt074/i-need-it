package com.brunovt.ineedit.ui.modal;

import androidx.lifecycle.SavedStateHandle;
import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase;
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase;
import com.brunovt.ineedit.domain.usecase.StatusRepository;
import com.brunovt.ineedit.domain.usecase.TagRepository;
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

  private final Provider<TagRepository> tagRepositoryProvider;

  private final Provider<StatusRepository> statusRepositoryProvider;

  public EntryFormViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider, Provider<EntryRepository> repositoryProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<StatusRepository> statusRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.upsertEntryProvider = upsertEntryProvider;
    this.markDoneProvider = markDoneProvider;
    this.moveColumnProvider = moveColumnProvider;
    this.repositoryProvider = repositoryProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
    this.statusRepositoryProvider = statusRepositoryProvider;
  }

  @Override
  public EntryFormViewModel get() {
    return newInstance(savedStateHandleProvider.get(), upsertEntryProvider.get(), markDoneProvider.get(), moveColumnProvider.get(), repositoryProvider.get(), tagRepositoryProvider.get(), statusRepositoryProvider.get());
  }

  public static EntryFormViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<UpsertEntryUseCase> upsertEntryProvider,
      javax.inject.Provider<MarkDoneUseCase> markDoneProvider,
      javax.inject.Provider<MoveColumnUseCase> moveColumnProvider,
      javax.inject.Provider<EntryRepository> repositoryProvider,
      javax.inject.Provider<TagRepository> tagRepositoryProvider,
      javax.inject.Provider<StatusRepository> statusRepositoryProvider) {
    return new EntryFormViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(upsertEntryProvider), Providers.asDaggerProvider(markDoneProvider), Providers.asDaggerProvider(moveColumnProvider), Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(tagRepositoryProvider), Providers.asDaggerProvider(statusRepositoryProvider));
  }

  public static EntryFormViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider, Provider<EntryRepository> repositoryProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<StatusRepository> statusRepositoryProvider) {
    return new EntryFormViewModel_Factory(savedStateHandleProvider, upsertEntryProvider, markDoneProvider, moveColumnProvider, repositoryProvider, tagRepositoryProvider, statusRepositoryProvider);
  }

  public static EntryFormViewModel newInstance(SavedStateHandle savedStateHandle,
      UpsertEntryUseCase upsertEntry, MarkDoneUseCase markDone, MoveColumnUseCase moveColumn,
      EntryRepository repository, TagRepository tagRepository, StatusRepository statusRepository) {
    return new EntryFormViewModel(savedStateHandle, upsertEntry, markDone, moveColumn, repository, tagRepository, statusRepository);
  }
}
