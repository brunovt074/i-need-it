package com.brunovt.ineedit.di;

import com.brunovt.ineedit.data.local.AppDatabase;
import com.brunovt.ineedit.data.local.StatusDao;
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
public final class DatabaseModule_ProvideStatusDaoFactory implements Factory<StatusDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideStatusDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public StatusDao get() {
    return provideStatusDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideStatusDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStatusDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideStatusDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStatusDaoFactory(dbProvider);
  }

  public static StatusDao provideStatusDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStatusDao(db));
  }
}
