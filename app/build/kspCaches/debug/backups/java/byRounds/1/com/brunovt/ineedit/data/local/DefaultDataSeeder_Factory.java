package com.brunovt.ineedit.data.local;

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
public final class DefaultDataSeeder_Factory implements Factory<DefaultDataSeeder> {
  private final Provider<TagDao> tagDaoProvider;

  private final Provider<StatusDao> statusDaoProvider;

  public DefaultDataSeeder_Factory(Provider<TagDao> tagDaoProvider,
      Provider<StatusDao> statusDaoProvider) {
    this.tagDaoProvider = tagDaoProvider;
    this.statusDaoProvider = statusDaoProvider;
  }

  @Override
  public DefaultDataSeeder get() {
    return newInstance(tagDaoProvider.get(), statusDaoProvider.get());
  }

  public static DefaultDataSeeder_Factory create(javax.inject.Provider<TagDao> tagDaoProvider,
      javax.inject.Provider<StatusDao> statusDaoProvider) {
    return new DefaultDataSeeder_Factory(Providers.asDaggerProvider(tagDaoProvider), Providers.asDaggerProvider(statusDaoProvider));
  }

  public static DefaultDataSeeder_Factory create(Provider<TagDao> tagDaoProvider,
      Provider<StatusDao> statusDaoProvider) {
    return new DefaultDataSeeder_Factory(tagDaoProvider, statusDaoProvider);
  }

  public static DefaultDataSeeder newInstance(TagDao tagDao, StatusDao statusDao) {
    return new DefaultDataSeeder(tagDao, statusDao);
  }
}
