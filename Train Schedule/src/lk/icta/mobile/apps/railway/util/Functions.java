/**
* @copyright	Copyright (C) 2010 - 2010 Chatura Dilan Perera
* @license		GNU/GPL
* This Application is released on behalf of ICTA (Information and Communication Technology Agency of Sri Lanka)
* to the public under the GNU General Public License
*/

package lk.icta.mobile.apps.railway.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class Functions {
	
	public static String capitalizeFirstLetters ( String s ) {
		s = s.toLowerCase();
	    for (int i = 0; i < s.length(); i++) {

	        if (i == 0) {
	        	// Capitalize the first letter of the string.
	            s = String.format( "%s%s",
	                         Character.toUpperCase(s.charAt(0)),
	                         s.substring(1) );
	        }
	        // Is this character a non-letter or non-digit?  If so
	        // then this is probably a word boundary so let's capitalize
	        // the next character in the sequence.
	        if (!Character.isLetterOrDigit(s.charAt(i))) {
	            if (i + 1 < s.length()) {
	                s = String.format( "%s%s%s",
	                             s.subSequence(0, i+1),
	                             Character.toUpperCase(s.charAt(i + 1)),
	                             s.substring(i+2) );
	            }
	        }

	    }
	    return s;
	}
	
	public static ProgressDialog getProgressDialog(Activity activity, String message){
		ProgressDialog progressDialog;
		progressDialog = new ProgressDialog(activity);				
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(message);
		progressDialog.show();
		return progressDialog;
	}

}
