package com.brunovt.ineedit.di;

import android.content.Context;
import com.brunovt.ineedit.data.prefs.LayoutPrefs;
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
public final class PrefsModule_ProvideLayoutPrefsFactory implements Factory<LayoutPrefs> {
  private final Provider<Context> contextProvider;

  public PrefsModule_ProvideLayoutPrefsFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public LayoutPrefs get() {
    return provideLayoutPrefs(contextProvider.get());
  }

  public static PrefsModule_ProvideLayoutPrefsFactory create(
      javax.inject.Provider<Context> contextProvider) {
    return new PrefsModule_ProvideLayoutPrefsFactory(Providers.asDaggerProvider(contextProvider));
  }

  public static PrefsModule_ProvideLayoutPrefsFactory create(Provider<Context> contextProvider) {
    return new PrefsModule_ProvideLayoutPrefsFactory(contextProvider);
  }

  public static LayoutPrefs provideLayoutPrefs(Context context) {
    return Preconditions.checkNotNullFromProvides(PrefsModule.INSTANCE.provideLayoutPrefs(context));
  }
}
