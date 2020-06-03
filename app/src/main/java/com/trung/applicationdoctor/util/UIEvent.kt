package com.trung.applicationdoctor.util

open class UIEvent<out T>(private val type: T,
                          private val value: Any? = null,
                          private val times: Int = 1) {
    private var opend = 0
        private set

    private var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): Pair<T, Any?>? {
        return if (hasBeenHandled) {
            null
        } else {
            opend++
            if (opend == times)
                hasBeenHandled = true

            Pair(type, value)
        }
    }

    fun getTypeIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null

        } else {
            opend++
            if (opend == times) hasBeenHandled = true
            type
        }
    }

    fun getValueIfNotHandled(): Any? {
        return if (hasBeenHandled) {
            null

        } else {
            opend++
            if (opend == times) hasBeenHandled = true
            value
        }
    }

    fun hasBeenHandled(): Boolean = hasBeenHandled
    fun peekType(): T = type
    fun peekValue(): Any? = value
}