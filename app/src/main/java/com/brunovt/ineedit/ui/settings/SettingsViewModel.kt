package com.brunovt.ineedit.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunovt.ineedit.data.prefs.LayoutPrefs
import com.brunovt.ineedit.data.prefs.LayoutStyle
import com.brunovt.ineedit.data.prefs.LocalePrefs
import com.brunovt.ineedit.data.prefs.ThemeFamily
import com.brunovt.ineedit.data.prefs.ThemeMode
import com.brunovt.ineedit.data.prefs.ThemePrefs
import com.brunovt.ineedit.domain.model.Status
import com.brunovt.ineedit.domain.model.Tag
import com.brunovt.ineedit.domain.usecase.StatusRepository
import com.brunovt.ineedit.domain.usecase.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePrefs: ThemePrefs,
    private val layoutPrefs: LayoutPrefs,
    private val localePrefs: LocalePrefs,
    private val tagRepository: TagRepository,
    private val statusRepository: StatusRepository,
) : ViewModel() {

    val themeFamily: StateFlow<ThemeFamily> = themePrefs.family
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ThemeFamily.PAPER)

    val themeMode: StateFlow<ThemeMode> = themePrefs.mode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ThemeMode.SYSTEM)

    val layoutStyle: StateFlow<LayoutStyle> = layoutPrefs.layout
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), LayoutStyle.ACCORDION)

    val locale: StateFlow<String> = localePrefs.locale
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")

    val tags: StateFlow<List<Tag>> = tagRepository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val statuses: StateFlow<List<Status>> = statusRepository.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _newTagText = MutableStateFlow("")
    val newTagText: StateFlow<String> = _newTagText.asStateFlow()

    private val _newStatusText = MutableStateFlow("")
    val newStatusText: StateFlow<String> = _newStatusText.asStateFlow()

    fun setThemeFamily(family: ThemeFamily) {
        viewModelScope.launch { themePrefs.setFamily(family) }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { themePrefs.setMode(mode) }
    }

    fun setLayoutStyle(style: LayoutStyle) {
        viewModelScope.launch { layoutPrefs.setLayout(style) }
    }

    fun setLocale(tag: String) {
        viewModelScope.launch { localePrefs.setLocale(tag) }
    }

    fun onNewTagTextChange(text: String) {
        _newTagText.value = text
    }

    fun addTag() {
        val name = _newTagText.value.trim()
        if (name.isBlank()) return
        viewModelScope.launch {
            tagRepository.upsert(Tag(id = UUID.randomUUID().toString(), name = name))
            _newTagText.value = ""
        }
    }

    fun deleteTag(id: String) {
        viewModelScope.launch { tagRepository.delete(id) }
    }

    fun onNewStatusTextChange(text: String) {
        _newStatusText.value = text
    }

    fun addStatus() {
        val name = _newStatusText.value.trim()
        if (name.isBlank()) return
        viewModelScope.launch {
            statusRepository.upsert(Status(id = UUID.randomUUID().toString(), name = name))
            _newStatusText.value = ""
        }
    }

    fun deleteStatus(id: String) {
        viewModelScope.launch { statusRepository.delete(id) }
    }
}
