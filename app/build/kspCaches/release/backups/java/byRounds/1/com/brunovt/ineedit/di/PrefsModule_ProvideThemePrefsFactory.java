package com.brunovt.ineedit.di;

import android.content.Context;
import com.brunovt.ineedit.data.prefs.ThemePrefs;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class PrefsModule_ProvideThemePrefsFactory implements Factory<ThemePrefs> {
  private final Provider<Context> contextProvider;

  public PrefsModule_ProvideThemePrefsFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ThemePrefs get() {
    return provideThemePrefs(contextProvider.get());
  }

  public static PrefsModule_ProvideThemePrefsFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new PrefsModule_ProvideThemePrefsFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static PrefsModule_ProvideThemePrefsFactory create(Provider<Context> contextProvider) {
    return new PrefsModule_ProvideThemePrefsFactory(contextProvider);
  }

  public static ThemePrefs provideThemePrefs(Context context) {
    return Preconditions.checkNotNullFromProvides(PrefsModule.INSTANCE.provideThemePrefs(context));
  }
}
