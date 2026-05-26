package com.brunovt.ineedit.di;

import com.brunovt.ineedit.data.local.AppDatabase;
import com.brunovt.ineedit.data.local.EntryDao;
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
public final class DatabaseModule_ProvideEntryDaoFactory implements Factory<EntryDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideEntryDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public EntryDao get() {
    return provideEntryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideEntryDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideEntryDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideEntryDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideEntryDaoFactory(dbProvider);
  }

  public static EntryDao provideEntryDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEntryDao(db));
  }
}
