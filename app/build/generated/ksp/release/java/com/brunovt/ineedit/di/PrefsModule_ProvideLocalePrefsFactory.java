package com.brunovt.ineedit.di;

import android.content.Context;
import com.brunovt.ineedit.data.prefs.LocalePrefs;
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
public final class PrefsModule_ProvideLocalePrefsFactory implements Factory<LocalePrefs> {
  private final Provider<Context> contextProvider;

  public PrefsModule_ProvideLocalePrefsFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LocalePrefs get() {
    return provideLocalePrefs(contextProvider.get());
  }

  public static PrefsModule_ProvideLocalePrefsFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new PrefsModule_ProvideLocalePrefsFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static PrefsModule_ProvideLocalePrefsFactory create(Provider<Context> contextProvider) {
    return new PrefsModule_ProvideLocalePrefsFactory(contextProvider);
  }

  public static LocalePrefs provideLocalePrefs(Context context) {
    return Preconditions.checkNotNullFromProvides(PrefsModule.INSTANCE.provideLocalePrefs(context));
  }
}
