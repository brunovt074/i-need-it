package com.brunovt.ineedit.di;

import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class UseCaseModule_ProvideUpsertEntryUseCaseFactory implements Factory<UpsertEntryUseCase> {
  private final Provider<EntryRepository> repoProvider;

  public UseCaseModule_ProvideUpsertEntryUseCaseFactory(Provider<EntryRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public UpsertEntryUseCase get() {
    return provideUpsertEntryUseCase(repoProvider.get());
  }

  public static UseCaseModule_ProvideUpsertEntryUseCaseFactory create(
      javax.inject.Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideUpsertEntryUseCaseFactory(Providers.asDaggerProvider(repoProvider));
  }

  public static UseCaseModule_ProvideUpsertEntryUseCaseFactory create(
      Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideUpsertEntryUseCaseFactory(repoProvider);
  }

  public static UpsertEntryUseCase provideUpsertEntryUseCase(EntryRepository repo) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideUpsertEntryUseCase(repo));
  }
}
