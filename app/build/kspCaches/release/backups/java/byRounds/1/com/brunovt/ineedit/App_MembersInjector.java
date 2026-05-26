package com.brunovt.ineedit;

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
public final class App_MembersInjector implements MembersInjector<App> {
  private final Provider<LocalePrefs> localePrefsProvider;

  public App_MembersInjector(Provider<LocalePrefs> localePrefsProvider) {
    this.localePrefsProvider = localePrefsProvider;
  }

  public static MembersInjector<App> create(Provider<LocalePrefs> localePrefsProvider) {
    return new App_MembersInjector(localePrefsProvider);
  }

  public static MembersInjector<App> create(
      javax.inject.Provider<LocalePrefs> localePrefsProvider) {
    return new App_MembersInjector(Providers.asDaggerProvider(localePrefsProvider));
  }

  @Override
  public void injectMembers(App instance) {
    injectLocalePrefs(instance, localePrefsProvider.get());
  }

  @InjectedFieldSignature("com.brunovt.ineedit.App.localePrefs")
  public static void injectLocalePrefs(App instance, LocalePrefs localePrefs) {
    instance.localePrefs = localePrefs;
  }
}
