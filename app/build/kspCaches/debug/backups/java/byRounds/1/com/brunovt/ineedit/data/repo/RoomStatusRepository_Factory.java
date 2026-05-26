package com.brunovt.ineedit.data.repo;

import com.brunovt.ineedit.data.local.StatusDao;
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
public final class RoomStatusRepository_Factory implements Factory<RoomStatusRepository> {
  private final Provider<StatusDao> daoProvider;

  public RoomStatusRepository_Factory(Provider<StatusDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public RoomStatusRepository get() {
    return newInstance(daoProvider.get());
  }

  public static RoomStatusRepository_Factory create(javax.inject.Provider<StatusDao> daoProvider) {
    return new RoomStatusRepository_Factory(Providers.asDaggerProvider(daoProvider));
  }

  public static RoomStatusRepository_Factory create(Provider<StatusDao> daoProvider) {
    return new RoomStatusRepository_Factory(daoProvider);
  }

  public static RoomStatusRepository newInstance(StatusDao dao) {
    return new RoomStatusRepository(dao);
  }
}
