package ba.etf.rma23.projekat

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import ba.etf.rma23.projekat.data.repositories.GameListAdapter
import junit.framework.TestCase.assertTrue
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {
    //potrebno close tab ukoliko se klasa pokrece vise puta

    val delayIdlingResource = object : IdlingResource {

        private var callback: IdlingResource.ResourceCallback? = null
        private var idleStartTime: Long = 0

        override fun getName(): String {
            return "Delay Idling Resource"
        }

        override fun isIdleNow(): Boolean {
            val idle = System.currentTimeMillis() >= idleStartTime + 5000 // Wait for 1 second
            if (idle) {
                callback?.onTransitionToIdle()
            }
            return idle
        }

        override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
            this.callback = callback
            this.idleStartTime = System.currentTimeMillis()
        }
    }

    @get:Rule
    var homeRule:ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Primarna uloga testa je da provjeri ispravnost button bara u svakoj situaciji
     * Test otvara aplikaciju (u portrait modu), provjera da li su buttoni onemoguceni,
     * otvara prvu igricu, provjera da li je details button onemogucen, vraca se na home,
     * sada provjera da li je details omogucen, te ga koristi da se vrati na prethodnu igricu,
     * provjerava da li je prethodna igrica odgovarajuca
     */
    @Test
    fun ownTest1() {

        // Ensure the app is in portrait mode
        val deviceOrientation = InstrumentationRegistry.getInstrumentation().targetContext.resources.configuration.orientation
        assertTrue(deviceOrientation == Configuration.ORIENTATION_PORTRAIT)
        // Wait for a fixed amount of time before performing the next action
        IdlingRegistry.getInstance().register(delayIdlingResource)

        // Ensure the buttons are disabled
        onView(withId(R.id.homeItem)).check(matches(isNotEnabled()))
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        // Navigate to the Game Details screen
        onView(withId(R.id.game_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<GameListAdapter.GameViewHolder>(0, click()))
        onView(withId(R.id.item_title_textview))
            .check(matches(isDisplayed()))
        onView(withId(R.id.gameDetailsItem)).check(matches(isNotEnabled()))

        // Unregister the delay IdlingResource
        IdlingRegistry.getInstance().unregister(delayIdlingResource)
        // Return to home screen
        onView(withId(R.id.homeItem)).perform(click())

        // Ensure the Game Details button is enabled
        onView(withId(R.id.gameDetailsItem)).check(matches(isEnabled()))

        // Navigate back to the previous game
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(GameData.getAll()[0].title)))
    }

    /**
     * Test mijenja orijentaciju iz portrait u landscape.
     * Provjerava da li je igrica na details (desnom) fragmentu prva iz liste.
     * Potom otvara sve igrice i provjerava da li su prikazane u desnom fragmentu.
     * Provjerava da li se vidi bottom bar u obje orijentacije
     * Na kraju vraca se u portrait mode i ispituje da li button details vraca na posljednju
     * igricu koja je otvorena u landscape modu (ujedno i posljednja u listi)
     */
    @Test
    fun owntest2() {
        val games = GameData.getAll()

        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        // Wait for a fixed amount of time before performing the next action
        IdlingRegistry.getInstance().register(delayIdlingResource)

        // Check if the first game is displayed on the right fragment
        onView(
            allOf(
                withId(R.id.item_title_textview),
                withText(games[0].title),
                isDescendantOfA(withId(R.id.nav_host_fragment_right))
            )
        )
            .check(matches(isDisplayed()))

        // Open all games and check if they are displayed on the right fragment
        games.forEach {
            onView(withId(R.id.game_list)).perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(it.title)), click()
                )
            )
            onView(
                allOf(
                    withId(R.id.item_title_textview),
                    withText(it.title),
                    isDescendantOfA(withId(R.id.nav_host_fragment_right))
                )
            ).check(matches(withText(it.title)))
        }
        // Unregister the delay IdlingResource
        IdlingRegistry.getInstance().unregister(delayIdlingResource)

        onView(withId(R.id.bottom_nav))
            .check(matches(not(isDisplayed())))

        // Change orientation back to portrait
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT }
        // Wait for a fixed amount of time before performing the next action
        IdlingRegistry.getInstance().register(delayIdlingResource)

        //Check if all elements are there
        onView(withId(R.id.game_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)).check(matches(allOf(
            hasDescendant(withId(R.id.item_title_textview)),
            hasDescendant(withId(R.id.game_rating_textview)),
            hasDescendant(withId(R.id.release_date)),
            hasDescendant(withId(R.id.game_platform_textview)),
            hasDescendant(withId(R.id.game_rating_textview))
        )))

        // Check if the last opened game is displayed on
        onView(withId(R.id.gameDetailsItem)).perform(click())
        onView(withId(R.id.item_title_textview))
            .check(matches(withText(games[games.size-1].title)))
        // Unregister the delay IdlingResource
        IdlingRegistry.getInstance().unregister(delayIdlingResource)

    }

    /**
     * Test prvenstveno otvara random igricu iz liste, a potom mijenja orijentaciju iz portrait u landscape.
     * Zatim provjerava da li je na details (desnom) fragmentu otvorena upravo ta igrica.
     * Provjerava raspored elemenata na novom game details fragmentu
     */
    @Test
    fun owntest3() {

        val randomGame = GameData.getAll().random()

        // Open the chosen game
        onView(withId(R.id.game_list)).perform(actionOnItem<RecyclerView.ViewHolder>(
            hasDescendant(withText(randomGame.title)), click()
        ))

        // Change orientation to landscape
        homeRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE }

        // Check if the chosen game is displayed on the right fragment
        onView(allOf(withId(R.id.item_title_textview), withText(randomGame.title), isDescendantOfA(withId(
            R.id.nav_host_fragment_right
        ))))
            .check(matches(isDisplayed()))

        // Check the layout of the new Game Details screen
        onView(allOf(withId(R.id.item_title_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.platform_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.release_date_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.esrb_rating_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.developer_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.publisher_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.genre_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
        onView(allOf(withId(R.id.description_textview), isDescendantOfA(withId(R.id.nav_host_fragment_right))))
            .check(matches(isCompletelyDisplayed()))
    }
}