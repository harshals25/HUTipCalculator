package com.example.tipcalculator

import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainActivityTest {

    private lateinit var mockEditText: EditText

    @Mock
    lateinit var mockButton: Button

    @Mock


    private lateinit var MainActivityTest: MainActivity

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        MainActivityTest = MainActivity()
    }

    @Test
    fun testDoAfterTextChangedCalculateOnceTrue() {
        // Arrange
        val text = "Some text"
        val calculateOnce = true

        `when`(mockEditText.doAfterTextChanged(any())).thenAnswer {
            val listener = it.arguments[0] as? () -> Unit
            listener?.invoke()
        }

        `when`(mockButton.callOnClick()).thenReturn(true)

        // Act
        MainActivityTest.setupDoAfterTextChangedListener(calculateOnce)
//        mockEditText.doAfterTextChanged().afterTextChanged(mockEditable)

        // Assert
        verify(mockButton).callOnClick()
    }

    @Test
    fun testDoAfterTextChangedCalculateOncefalse() {
        // Arrange
        val text = "Some text"
        val calculateOnce = false

        `when`(mockEditText.doAfterTextChanged(any())).thenAnswer {
            val listener = it.arguments[0] as? () -> Unit
            listener?.invoke()
        }

        // Act
        MainActivityTest.setupDoAfterTextChangedListener(calculateOnce)
//        mockEditText.doAfterTextChanged.callback?.invoke()

        // Assert
        verify(mockButton, never()).callOnClick()
    }
}