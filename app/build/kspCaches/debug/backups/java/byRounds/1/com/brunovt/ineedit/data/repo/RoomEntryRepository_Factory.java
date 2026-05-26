package com.brunovt.ineedit.data.repo;

import com.brunovt.ineedit.data.local.EntryDao;
import com.brunovt.ineedit.data.local.StatusDao;
import com.brunovt.ineedit.data.local.TagDao;
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

  private final Provider<TagDao> tagDaoProvider;

  private final Provider<StatusDao> statusDaoProvider;

  public RoomEntryRepository_Factory(Provider<EntryDao> daoProvider,
      Provider<TagDao> tagDaoProvider, Provider<StatusDao> statusDaoProvider) {
    this.daoProvider = daoProvider;
    this.tagDaoProvider = tagDaoProvider;
    this.statusDaoProvider = statusDaoProvider;
  }

  @Override
  public RoomEntryRepository get() {
    return newInstance(daoProvider.get(), tagDaoProvider.get(), statusDaoProvider.get());
  }

  public static RoomEntryRepository_Factory create(javax.inject.Provider<EntryDao> daoProvider,
      javax.inject.Provider<TagDao> tagDaoProvider,
      javax.inject.Provider<StatusDao> statusDaoProvider) {
    return new RoomEntryRepository_Factory(Providers.asDaggerProvider(daoProvider), Providers.asDaggerProvider(tagDaoProvider), Providers.asDaggerProvider(statusDaoProvider));
  }

  public static RoomEntryRepository_Factory create(Provider<EntryDao> daoProvider,
      Provider<TagDao> tagDaoProvider, Provider<StatusDao> statusDaoProvider) {
    return new RoomEntryRepository_Factory(daoProvider, tagDaoProvider, statusDaoProvider);
  }

  public static RoomEntryRepository newInstance(EntryDao dao, TagDao tagDao, StatusDao statusDao) {
    return new RoomEntryRepository(dao, tagDao, statusDao);
  }
}
