package com.brunovt.ineedit.ui.settings;

import com.brunovt.ineedit.data.prefs.LayoutPrefs;
import com.brunovt.ineedit.data.prefs.LocalePrefs;
import com.brunovt.ineedit.data.prefs.ThemePrefs;
import com.brunovt.ineedit.domain.usecase.StatusRepository;
import com.brunovt.ineedit.domain.usecase.TagRepository;
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

  private final Provider<TagRepository> tagRepositoryProvider;

  private final Provider<StatusRepository> statusRepositoryProvider;

  public SettingsViewModel_Factory(Provider<ThemePrefs> themePrefsProvider,
      Provider<LayoutPrefs> layoutPrefsProvider, Provider<LocalePrefs> localePrefsProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<StatusRepository> statusRepositoryProvider) {
    this.themePrefsProvider = themePrefsProvider;
    this.layoutPrefsProvider = layoutPrefsProvider;
    this.localePrefsProvider = localePrefsProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
    this.statusRepositoryProvider = statusRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(themePrefsProvider.get(), layoutPrefsProvider.get(), localePrefsProvider.get(), tagRepositoryProvider.get(), statusRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      javax.inject.Provider<ThemePrefs> themePrefsProvider,
      javax.inject.Provider<LayoutPrefs> layoutPrefsProvider,
      javax.inject.Provider<LocalePrefs> localePrefsProvider,
      javax.inject.Provider<TagRepository> tagRepositoryProvider,
      javax.inject.Provider<StatusRepository> statusRepositoryProvider) {
    return new SettingsViewModel_Factory(Providers.asDaggerProvider(themePrefsProvider), Providers.asDaggerProvider(layoutPrefsProvider), Providers.asDaggerProvider(localePrefsProvider), Providers.asDaggerProvider(tagRepositoryProvider), Providers.asDaggerProvider(statusRepositoryProvider));
  }

  public static SettingsViewModel_Factory create(Provider<ThemePrefs> themePrefsProvider,
      Provider<LayoutPrefs> layoutPrefsProvider, Provider<LocalePrefs> localePrefsProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<StatusRepository> statusRepositoryProvider) {
    return new SettingsViewModel_Factory(themePrefsProvider, layoutPrefsProvider, localePrefsProvider, tagRepositoryProvider, statusRepositoryProvider);
  }

  public static SettingsViewModel newInstance(ThemePrefs themePrefs, LayoutPrefs layoutPrefs,
      LocalePrefs localePrefs, TagRepository tagRepository, StatusRepository statusRepository) {
    return new SettingsViewModel(themePrefs, layoutPrefs, localePrefs, tagRepository, statusRepository);
  }
}
