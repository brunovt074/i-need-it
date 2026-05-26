package com.brunovt.ineedit.di;

import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase;
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
public final class UseCaseModule_ProvideMarkDoneUseCaseFactory implements Factory<MarkDoneUseCase> {
  private final Provider<EntryRepository> repoProvider;

  public UseCaseModule_ProvideMarkDoneUseCaseFactory(Provider<EntryRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public MarkDoneUseCase get() {
    return provideMarkDoneUseCase(repoProvider.get());
  }

  public static UseCaseModule_ProvideMarkDoneUseCaseFactory create(
      javax.inject.Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideMarkDoneUseCaseFactory(Providers.asDaggerProvider(repoProvider));
  }

  public static UseCaseModule_ProvideMarkDoneUseCaseFactory create(
      Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideMarkDoneUseCaseFactory(repoProvider);
  }

  public static MarkDoneUseCase provideMarkDoneUseCase(EntryRepository repo) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideMarkDoneUseCase(repo));
  }
}
