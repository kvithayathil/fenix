package org.mozilla.fenix.ext

import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.mozilla.fenix.TestApplication
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import io.mockk.mockk
import io.mockk.verify
import android.widget.ImageButton

@ObsoleteCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)

class ImageButtonTest {
    val mockIb: ImageButton = mockk(relaxed = true)

    @Test
    fun `Hide and disable`() {
        mockIb.hideAndDisable()
        verify { mockIb.setVisibility(4) }
        verify { mockIb.setEnabled(false) }
    }

    @Test
    fun `Show and enable`() {
        mockIb.showAndEnable()
        verify { mockIb.setVisibility(0) }
        verify { mockIb.setEnabled(true) }
    }
}
