package com.brunovt.ineedit;

import com.brunovt.ineedit.data.prefs.ThemePrefs;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<ThemePrefs> themePrefsProvider;

  public MainActivity_MembersInjector(Provider<ThemePrefs> themePrefsProvider) {
    this.themePrefsProvider = themePrefsProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<ThemePrefs> themePrefsProvider) {
    return new MainActivity_MembersInjector(themePrefsProvider);
  }

  public static MembersInjector<MainActivity> create(
      javax.inject.Provider<ThemePrefs> themePrefsProvider) {
    return new MainActivity_MembersInjector(Providers.asDaggerProvider(themePrefsProvider));
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectThemePrefs(instance, themePrefsProvider.get());
  }

  @InjectedFieldSignature("com.brunovt.ineedit.MainActivity.themePrefs")
  public static void injectThemePrefs(MainActivity instance, ThemePrefs themePrefs) {
    instance.themePrefs = themePrefs;
  }
}
