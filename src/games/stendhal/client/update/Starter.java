//*****************************************************************************
//*****************************************************************************
//
//                               Important note
//
// Please note that this file is compiled using Java 1.2 in the build-script
// in order to display a dialogbox to the user in case an old version of java
// is used. As we compile it with Java 1.2 no new features may be used in this
// class.
// 
//*****************************************************************************
//*****************************************************************************
package games.stendhal.client.update;

import javax.swing.JOptionPane;

/**
 * This class can be compiled with a lower version of Java and will display an
 * error message if the java version is too old.
 * 
 * @author hendrik
 */
public class Starter {

	/**
	 * Starts stendhal.
	 * 
	 * @param args
	 *            args
	 */
	public static void main(final String[] args) {
		try {
			final String version = System.getProperty("java.specification.version");
			if (Float.parseFloat(version) < 1.5f) {
				JOptionPane.showMessageDialog(
						null,
						"You need at least Java 1.5.0 (also known as 5.0) but you only have "
								+ version
								+ ". You can download it at http://java.sun.com");
				System.exit(-1);
			}
		} catch (final RuntimeException e) {
			// ignore
		}

	ClientRunner.run(args);
	}

}
