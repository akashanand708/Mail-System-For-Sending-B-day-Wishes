package com.birthdaywishes.util;

import java.util.List;

/**
 * @author Akash This interface contains data base related utility methods.
 */
public interface DbUtilityInterface {

	/**
	 * @return This method returns list of all ToEmailAddress whose b'day is
	 *         today's date. If the current year is not leap year and current
	 *         date is 28 FEB then list will contain those emails also whose
	 *         b'day is 29th FEB.
	 */
	public List<String> getListOfToEmailAddress();

}
