package com.brunovt.ineedit.data;

import android.content.Context;
import com.brunovt.ineedit.domain.usecase.EntryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class SeedData_Factory implements Factory<SeedData> {
  private final Provider<Context> appContextProvider;

  private final Provider<EntryRepository> repositoryProvider;

  public SeedData_Factory(Provider<Context> appContextProvider,
      Provider<EntryRepository> repositoryProvider) {
    this.appContextProvider = appContextProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SeedData get() {
    return newInstance(appContextProvider.get(), repositoryProvider.get());
  }

  public static SeedData_Factory create(javax.inject.Provider<Context> appContextProvider,
      javax.inject.Provider<EntryRepository> repositoryProvider) {
    return new SeedData_Factory(Providers.asDaggerProvider(appContextProvider), Providers.asDaggerProvider(repositoryProvider));
  }

  public static SeedData_Factory create(Provider<Context> appContextProvider,
      Provider<EntryRepository> repositoryProvider) {
    return new SeedData_Factory(appContextProvider, repositoryProvider);
  }

  public static SeedData newInstance(Context appContext, EntryRepository repository) {
    return new SeedData(appContext, repository);
  }
}
