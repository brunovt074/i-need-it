package com.brunovt.ineedit;

import com.brunovt.ineedit.data.SeedData;
import com.brunovt.ineedit.data.prefs.LocalePrefs;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class DebugApp_MembersInjector implements MembersInjector<DebugApp> {
  private final Provider<LocalePrefs> localePrefsProvider;

  private final Provider<SeedData> seedDataProvider;

  public DebugApp_MembersInjector(Provider<LocalePrefs> localePrefsProvider,
      Provider<SeedData> seedDataProvider) {
    this.localePrefsProvider = localePrefsProvider;
    this.seedDataProvider = seedDataProvider;
  }

  public static MembersInjector<DebugApp> create(Provider<LocalePrefs> localePrefsProvider,
      Provider<SeedData> seedDataProvider) {
    return new DebugApp_MembersInjector(localePrefsProvider, seedDataProvider);
  }

  public static MembersInjector<DebugApp> create(
      javax.inject.Provider<LocalePrefs> localePrefsProvider,
      javax.inject.Provider<SeedData> seedDataProvider) {
    return new DebugApp_MembersInjector(Providers.asDaggerProvider(localePrefsProvider), Providers.asDaggerProvider(seedDataProvider));
  }

  @Override
  public void injectMembers(DebugApp instance) {
    App_MembersInjector.injectLocalePrefs(instance, localePrefsProvider.get());
    injectSeedData(instance, seedDataProvider.get());
  }

  @InjectedFieldSignature("com.brunovt.ineedit.DebugApp.seedData")
  public static void injectSeedData(DebugApp instance, SeedData seedData) {
    instance.seedData = seedData;
  }
}
