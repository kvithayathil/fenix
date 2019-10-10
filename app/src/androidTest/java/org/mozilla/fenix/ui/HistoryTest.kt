/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.ui

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mozilla.fenix.helpers.AndroidAssetDispatcher
import org.mozilla.fenix.helpers.HomeActivityTestRule
import org.mozilla.fenix.helpers.TestAssetHelper
import org.mozilla.fenix.ui.robots.homeScreen
import org.mozilla.fenix.ui.robots.navigationToolbar

/**
 *  Tests for verifying basic functionality of history
 *
 */
class HistoryTest {
    /* ktlint-disable no-blank-line-before-rbrace */ // This imposes unreadable grouping.
    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    val activityTestRule = HomeActivityTestRule()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer().apply {
            setDispatcher(AndroidAssetDispatcher())
            start()
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun noHistoryItemsInCacheTest() {
        homeScreen {
        }.openThreeDotMenu {
            verifyLibraryButton()
        }.openLibrary {
            verifyHistoryButton()
        }.openHistory {
            verifyHistoryMenuView()
            verifyEmptyHistoryView()
        }
    }

    @Test
    fun visitedUrlHistoryTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            verifyHistoryMenuView()
            verifyFirstTestPageTitle()
            verifyTestPageUrl(firstWebPage.url)
        }
    }

    @Test
    fun deleteHistoryItemTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            openOverflowMenu()
            clickThreeDotMenuDelete()
            verifyEmptyHistoryView()
        }
    }

    @Test
    fun deleteAllHistoryTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            clickDeleteHistoryButton()
            verifyDeleteConfirmationMessage()
            confirmDeleteAllHistory()
            verifyEmptyHistoryView()
        }
    }

    @Test
    fun multiSelectionToolbarTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            longTapSelectItem()
            verifyMultiSelectionCheckmark()
            verifyMultiSelectionCounter()
            verifyShareButton()
            verifyMultiSelectionOverflowMenu()
        }
    }

    @Test
    fun openHistoryInNewTabTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            longTapSelectItem()
            openMultiSelectionOverflowMenu()
        }.clickOpenNewTab{
            verifyPageContent(firstWebPage.content)
        }.openHomeScreen {
            verifyOpenTabsHeader()
        }
    }

    @Test
    fun openHistoryInPrivateTabTest() {
        val firstWebPage = TestAssetHelper.getGenericAsset(mockWebServer, 1)

        navigationToolbar {
        }.enterURLAndEnterToBrowser(firstWebPage.url) {
        }.openHomeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
            longTapSelectItem()
            openMultiSelectionOverflowMenu()
        }.clickOpenPrivateTab{
            verifyPageContent(firstWebPage.content)
        }.openHomeScreen {
            verifyPrivateSessionHeader()
        }
    }

    @Test
    fun deleteMultipleSelectionTest() {
    }

    @Test
    fun shareHistoryItemTest(){}

    @Test
    fun verifyBackNavigation() {
        homeScreen {
        }.openThreeDotMenu {
        }.openLibrary {
        }.openHistory {
        }.goBack {
            verifyLibraryView()
        }
    }

    @Test
    fun verifyCloseMenu() {
    }
}
