package com.brunovt.ineedit.ui.history;

import com.brunovt.ineedit.domain.usecase.EntryRepository;
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
public final class DoneViewModel_Factory implements Factory<DoneViewModel> {
  private final Provider<EntryRepository> repositoryProvider;

  private final Provider<UpsertEntryUseCase> upsertEntryProvider;

  public DoneViewModel_Factory(Provider<EntryRepository> repositoryProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider) {
    this.repositoryProvider = repositoryProvider;
    this.upsertEntryProvider = upsertEntryProvider;
  }

  @Override
  public DoneViewModel get() {
    return newInstance(repositoryProvider.get(), upsertEntryProvider.get());
  }

  public static DoneViewModel_Factory create(
      javax.inject.Provider<EntryRepository> repositoryProvider,
      javax.inject.Provider<UpsertEntryUseCase> upsertEntryProvider) {
    return new DoneViewModel_Factory(Providers.asDaggerProvider(repositoryProvider), Providers.asDaggerProvider(upsertEntryProvider));
  }

  public static DoneViewModel_Factory create(Provider<EntryRepository> repositoryProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider) {
    return new DoneViewModel_Factory(repositoryProvider, upsertEntryProvider);
  }

  public static DoneViewModel newInstance(EntryRepository repository,
      UpsertEntryUseCase upsertEntry) {
    return new DoneViewModel(repository, upsertEntry);
  }
}
