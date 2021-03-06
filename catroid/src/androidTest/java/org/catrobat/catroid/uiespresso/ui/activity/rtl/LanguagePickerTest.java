/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.ui.activity.rtl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.PreferenceMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.R;
import org.catrobat.catroid.testsuites.annotations.Cat;
import org.catrobat.catroid.testsuites.annotations.Level;
import org.catrobat.catroid.ui.SettingsActivity;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.uiespresso.util.rules.DontGenerateDefaultProjectActivityTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import java.util.Locale;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.catrobat.catroid.common.SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY;
import static org.catrobat.catroid.common.SharedPreferenceKeys.SHOW_COPY_PROJECTS_FROM_EXTERNAL_STORAGE_DIALOG;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
public class LanguagePickerTest {

	@Rule
	public DontGenerateDefaultProjectActivityTestRule<SettingsActivity> baseActivityTestRule = new
			DontGenerateDefaultProjectActivityTestRule<>(SettingsActivity.class, false, false);

	private boolean bufferedPrivacyPolicyPreferenceSetting;
	private boolean bufferedImportFromExternalStoragePreferenceSetting;

	private static final Locale ARABICLOCALE = new Locale("ar");
	private static final Locale DEUTSCHLOCALE = Locale.GERMAN;

	@Before
	public void setUp() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext());

		bufferedPrivacyPolicyPreferenceSetting = sharedPreferences
				.getBoolean(AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, false);

		bufferedImportFromExternalStoragePreferenceSetting = sharedPreferences
				.getBoolean(SHOW_COPY_PROJECTS_FROM_EXTERNAL_STORAGE_DIALOG, false);

		PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
				.edit()
				.putBoolean(AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, true)
				.commit();
		baseActivityTestRule.launchActivity(null);
	}

	@After
	public void tearDown() {
		PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
				.edit()
				.putBoolean(AGREED_TO_PRIVACY_POLICY_PREFERENCE_KEY, bufferedPrivacyPolicyPreferenceSetting)
				.putBoolean(SHOW_COPY_PROJECTS_FROM_EXTERNAL_STORAGE_DIALOG, bufferedImportFromExternalStoragePreferenceSetting)
				.commit();

		SettingsFragment.removeLanguageSharedPreference(InstrumentationRegistry.getTargetContext());
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void testChangeLanguageToArabic() {
		onData(PreferenceMatchers.withTitle(R.string.preference_title_language))
				.perform(click());
		onData(hasToString(startsWith(ARABICLOCALE.getDisplayName(ARABICLOCALE))))
				.check(matches(isDisplayed()));
	}

	@Category({Cat.AppUi.class, Level.Smoke.class, Cat.RTLTests.class})
	@Test
	public void testChangeLanguageToDeutsch() {
		onData(PreferenceMatchers.withTitle(R.string.preference_title_language))
				.perform(click());
		onData(hasToString(startsWith(DEUTSCHLOCALE.getDisplayName(DEUTSCHLOCALE))))
				.check(matches(isDisplayed()));
	}
}
