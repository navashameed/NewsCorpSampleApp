package com.newscorp.sampleapp.utils.lifecycle

/**
 *
 * Copied from: https://gist.github.com/JoseAlcerreca/5b661f1800e1e654f07cc54fe87441af
 *
 * For more info check this post:
 *      https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 *
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(protected val content: T) {

    var hasBeenHandled = false
        protected set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
