package com.brunovt.ineedit.ui.dashboard;

import com.brunovt.ineedit.data.prefs.LayoutPrefs;
import com.brunovt.ineedit.domain.usecase.MarkDoneUseCase;
import com.brunovt.ineedit.domain.usecase.MoveColumnUseCase;
import com.brunovt.ineedit.domain.usecase.ObserveBoardUseCase;
import com.brunovt.ineedit.domain.usecase.UpsertEntryUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<ObserveBoardUseCase> observeBoardProvider;

  private final Provider<UpsertEntryUseCase> upsertEntryProvider;

  private final Provider<MarkDoneUseCase> markDoneProvider;

  private final Provider<MoveColumnUseCase> moveColumnProvider;

  private final Provider<LayoutPrefs> layoutPrefsProvider;

  public DashboardViewModel_Factory(Provider<ObserveBoardUseCase> observeBoardProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider, Provider<LayoutPrefs> layoutPrefsProvider) {
    this.observeBoardProvider = observeBoardProvider;
    this.upsertEntryProvider = upsertEntryProvider;
    this.markDoneProvider = markDoneProvider;
    this.moveColumnProvider = moveColumnProvider;
    this.layoutPrefsProvider = layoutPrefsProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(observeBoardProvider.get(), upsertEntryProvider.get(), markDoneProvider.get(), moveColumnProvider.get(), layoutPrefsProvider.get());
  }

  public static DashboardViewModel_Factory create(
      javax.inject.Provider<ObserveBoardUseCase> observeBoardProvider,
      javax.inject.Provider<UpsertEntryUseCase> upsertEntryProvider,
      javax.inject.Provider<MarkDoneUseCase> markDoneProvider,
      javax.inject.Provider<MoveColumnUseCase> moveColumnProvider,
      javax.inject.Provider<LayoutPrefs> layoutPrefsProvider) {
    return new DashboardViewModel_Factory(Providers.asDaggerProvider(observeBoardProvider), Providers.asDaggerProvider(upsertEntryProvider), Providers.asDaggerProvider(markDoneProvider), Providers.asDaggerProvider(moveColumnProvider), Providers.asDaggerProvider(layoutPrefsProvider));
  }

  public static DashboardViewModel_Factory create(
      Provider<ObserveBoardUseCase> observeBoardProvider,
      Provider<UpsertEntryUseCase> upsertEntryProvider, Provider<MarkDoneUseCase> markDoneProvider,
      Provider<MoveColumnUseCase> moveColumnProvider, Provider<LayoutPrefs> layoutPrefsProvider) {
    return new DashboardViewModel_Factory(observeBoardProvider, upsertEntryProvider, markDoneProvider, moveColumnProvider, layoutPrefsProvider);
  }

  public static DashboardViewModel newInstance(ObserveBoardUseCase observeBoard,
      UpsertEntryUseCase upsertEntry, MarkDoneUseCase markDone, MoveColumnUseCase moveColumn,
      LayoutPrefs layoutPrefs) {
    return new DashboardViewModel(observeBoard, upsertEntry, markDone, moveColumn, layoutPrefs);
  }
}
