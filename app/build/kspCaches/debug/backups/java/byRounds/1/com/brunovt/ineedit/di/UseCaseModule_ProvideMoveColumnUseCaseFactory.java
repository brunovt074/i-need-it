package com.brunovt.ineedit.di;

import com.brunovt.ineedit.domain.usecase.EntryRepository;
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase;
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
public final class UseCaseModule_ProvideMoveColumnUseCaseFactory implements Factory<MoveColumnUseCase> {
  private final Provider<EntryRepository> repoProvider;

  public UseCaseModule_ProvideMoveColumnUseCaseFactory(Provider<EntryRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public MoveColumnUseCase get() {
    return provideMoveColumnUseCase(repoProvider.get());
  }

  public static UseCaseModule_ProvideMoveColumnUseCaseFactory create(
      javax.inject.Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideMoveColumnUseCaseFactory(Providers.asDaggerProvider(repoProvider));
  }

  public static UseCaseModule_ProvideMoveColumnUseCaseFactory create(
      Provider<EntryRepository> repoProvider) {
    return new UseCaseModule_ProvideMoveColumnUseCaseFactory(repoProvider);
  }

  public static MoveColumnUseCase provideMoveColumnUseCase(EntryRepository repo) {
    return Preconditions.checkNotNullFromProvides(UseCaseModule.INSTANCE.provideMoveColumnUseCase(repo));
  }
}
