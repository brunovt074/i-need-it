package com.brunovt.ineedit.data.repo;

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
public final class RoomTagRepository_Factory implements Factory<RoomTagRepository> {
  private final Provider<TagDao> daoProvider;

  public RoomTagRepository_Factory(Provider<TagDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public RoomTagRepository get() {
    return newInstance(daoProvider.get());
  }

  public static RoomTagRepository_Factory create(javax.inject.Provider<TagDao> daoProvider) {
    return new RoomTagRepository_Factory(Providers.asDaggerProvider(daoProvider));
  }

  public static RoomTagRepository_Factory create(Provider<TagDao> daoProvider) {
    return new RoomTagRepository_Factory(daoProvider);
  }

  public static RoomTagRepository newInstance(TagDao dao) {
    return new RoomTagRepository(dao);
  }
}
