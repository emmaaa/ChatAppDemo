package com.example.chatapp.presentation.chat

import com.example.chatapp.domain.time.TimeProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.TimeZone
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MessageTimeFormatterTest {

    private lateinit var originalTimeZone: TimeZone

    @Before
    fun setUp() {
        originalTimeZone = TimeZone.getDefault()
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @After
    fun tearDown() {
        TimeZone.setDefault(originalTimeZone)
    }

    @Test
    fun `Given timestamp on current day, When formatted, Then uses Today label`() {
        // Given
        val formatter = MessageTimeFormatter(FakeTimeProvider(now = NOON_JAN_2_2026))

        // When
        val label = formatter.formatSectionLabel(NOON_JAN_2_2026)

        // Then
        assertEquals("Today 12:00", label)
    }

    @Test
    fun `Given timestamp on previous day, When formatted, Then uses Yesterday label`() {
        // Given
        val formatter = MessageTimeFormatter(FakeTimeProvider(now = NOON_JAN_2_2026))

        // When
        val label = formatter.formatSectionLabel(NOON_JAN_1_2026)

        // Then
        assertEquals("Yesterday 12:00", label)
    }

    @Test
    fun `Given older timestamp, When formatted, Then uses weekday label`() {
        // Given
        val formatter = MessageTimeFormatter(FakeTimeProvider(now = NOON_JAN_2_2026))

        // When
        val label = formatter.formatSectionLabel(NOON_DEC_30_2025)

        // Then
        assertTrue(label.endsWith(" 12:00"))
        assertTrue(!label.startsWith("Today"))
        assertTrue(!label.startsWith("Yesterday"))
    }

    private class FakeTimeProvider(private val now: Long) : TimeProvider {
        override fun currentTimeMillis(): Long = now
    }

    private companion object {
        const val NOON_DEC_30_2025 = 1_767_096_000_000L
        const val NOON_JAN_1_2026 = 1_767_268_800_000L
        const val NOON_JAN_2_2026 = 1_767_355_200_000L
    }
}
