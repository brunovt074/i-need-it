package com.brunovt.ineedit.di;

import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.ObserveBoardUseCase;
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
public final class UseCaseModule_ProvideObserveBoardUseCaseFactory implements Factory<ObserveBoardUseCase> {
  private final Provider<EntryRepository> repoProvider;

  public UseCaseModule_ProvideObserveBoardUseCaseFactory(Provider<EntryRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ObserveBoardUseCase get() {
    return provideObserveBoardUseCase(repoProvider.get());
  }

  public static UseCaseModule_ProvideObserveBoardUseCaseFactory create(
      javax.inject.Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideObserveBoardUseCaseFactory(Providers.asDaggerProvider(repoProvider));
  }

  public static UseCaseModule_ProvideObserveBoardUseCaseFactory create(
      Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideObserveBoardUseCaseFactory(repoProvider);
  }

  public static ObserveBoardUseCase provideObserveBoardUseCase(EntryRepository repo) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideObserveBoardUseCase(repo));
  }
}
