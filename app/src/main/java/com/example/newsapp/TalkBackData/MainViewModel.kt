package com.example.newsapp.TalkBackData

import android.content.Context
import android.content.res.AssetManager
import android.speech.tts.TextToSpeech
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Locale

class MainViewModel(private val context: Context) : ViewModel() {
    var fileContent = mutableStateOf("")
    private var textToSpeech: TextToSpeech? = null
    fun readFileContent(line: String) {
        fileContent.value=line
    }

    fun readContent() {
        val content = fileContent.value
        val words = content.split(" ")
        val toRead = content
        textToSpeech = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.getDefault()
                textToSpeech?.speak(toRead.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
        println(textToSpeech)
    }

    fun stopReading() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

    fun isTalkbackEnabled(): Boolean {
        val accessibilityManager =
            context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        return accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled
    }
}