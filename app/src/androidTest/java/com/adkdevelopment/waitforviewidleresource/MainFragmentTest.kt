package com.adkdevelopment.waitforviewidleresource

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adkdevelopment.waitforviewidleresource.RecyclerViewItemCountAssertion.Companion.withItemCount
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class MainFragmentTest {

    @Before
    fun setUp() {
        // changes default IdlingResource timeout to 3 minutes (default is like 30 seconds)
        IdlingPolicies.setIdlingResourceTimeout(3, TimeUnit.MINUTES)
        ActivityScenario.launch((MainActivity::class.java))
    }

    @Test
    fun testLongDelayBeforeDataReturned() {
        // initially RV is Gone, this is a simple example so I didn't
        // want to create a matcher for data being loaded
        waitUntilViewIsDisplayed(withId(R.id.recycler_view))

        // confirm that we have the expected number of elements in the RV
        onView(withId(R.id.recycler_view))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(withItemCount(4))

        // test click on a second item in the list
        onView(withId(R.id.recycler_view))
            .perform(actionOnItemAtPosition<TestItemViewHolder>(1, click()))

        // check data in the actual items
        assertTextAtPosition(0, listOf("Test 1", "1"))
        assertTextAtPosition(1, listOf("Test 2", "2"))
        assertTextAtPosition(2, listOf("Test 3", "3"))
        assertTextAtPosition(3, listOf("Test 4", "4"))
    }

}

/**
 * Allows a UI test to assert Item counts in recycler views
 */
class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>) : ViewAssertion {

    override fun check(
        view: View,
        noViewFoundException: NoMatchingViewException?
    ) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, matcher)
    }

    companion object {
        /**
         * Assert that the RecyclerView being evaluated has exactly [expectedCount]
         */
        fun withItemCount(expectedCount: Int): RecyclerViewItemCountAssertion {
            return RecyclerViewItemCountAssertion(`is`(expectedCount))
        }
    }

}

/**
 * Asserts that a specific text is shown in an item at the specified position.
 * You can provide a list of strings if your item has multiple textviews for example.
 */
fun assertTextAtPosition(position: Int, string: List<String>) {
    onView(withId(R.id.recycler_view)).check(
        matches(
            atPosition(
                position,
                allOf(string.map { hasDescendant(withText(it)) }
                    .toList())
            )
        )
    )
}

/**
 * Ensures that the [ViewHolder] at [position] matches [itemMatcher]
 */
fun atPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {

    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false

            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}