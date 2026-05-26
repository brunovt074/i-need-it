package com.brunovt.ineedit.data.repo;

import com.brunovt.ineedit.data.local.EntryDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class RoomEntryRepository_Factory implements Factory<RoomEntryRepository> {
  private final Provider<EntryDao> daoProvider;

  public RoomEntryRepository_Factory(Provider<EntryDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public RoomEntryRepository get() {
    return newInstance(daoProvider.get());
  }

  public static RoomEntryRepository_Factory create(javax.inject.Provider<EntryDao> daoProvider) {
    return new RoomEntryRepository_Factory(Providers.asDaggerProvider(daoProvider));
  }

  public static RoomEntryRepository_Factory create(Provider<EntryDao> daoProvider) {
    return new RoomEntryRepository_Factory(daoProvider);
  }

  public static RoomEntryRepository newInstance(EntryDao dao) {
    return new RoomEntryRepository(dao);
  }
}
