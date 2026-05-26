package com.brunovt.ineedit.ui.settings;

import com.brunovt.ineedit.data.prefs.LayoutPrefs;
import com.brunovt.ineedit.data.prefs.LocalePrefs;
import com.brunovt.ineedit.data.prefs.ThemePrefs;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<ThemePrefs> themePrefsProvider;

  private final Provider<LayoutPrefs> layoutPrefsProvider;

  private final Provider<LocalePrefs> localePrefsProvider;

  public SettingsViewModel_Factory(Provider<ThemePrefs> themePrefsProvider,
      Provider<LayoutPrefs> layoutPrefsProvider, Provider<LocalePrefs> localePrefsProvider) {
    this.themePrefsProvider = themePrefsProvider;
    this.layoutPrefsProvider = layoutPrefsProvider;
    this.localePrefsProvider = localePrefsProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(themePrefsProvider.get(), layoutPrefsProvider.get(), localePrefsProvider.get());
  }

  public static SettingsViewModel_Factory create(
      javax.inject.Provider<ThemePrefs> themePrefsProvider,
      javax.inject.Provider<LayoutPrefs> layoutPrefsProvider,
      javax.inject.Provider<LocalePrefs> localePrefsProvider) {
    return new SettingsViewModel_Factory(Providers.asDaggerProvider(themePrefsProvider), Providers.asDaggerProvider(layoutPrefsProvider), Providers.asDaggerProvider(localePrefsProvider));
  }

  public static SettingsViewModel_Factory create(Provider<ThemePrefs> themePrefsProvider,
      Provider<LayoutPrefs> layoutPrefsProvider, Provider<LocalePrefs> localePrefsProvider) {
    return new SettingsViewModel_Factory(themePrefsProvider, layoutPrefsProvider, localePrefsProvider);
  }

  public static SettingsViewModel newInstance(ThemePrefs themePrefs, LayoutPrefs layoutPrefs,
      LocalePrefs localePrefs) {
    return new SettingsViewModel(themePrefs, layoutPrefs, localePrefs);
  }
}
