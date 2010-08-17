/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.openmu.logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 
 * @author mikiones
 */
public class MuLoggerMennager {

	private MuLoggerMennager() {
		menager = LogManager.getLogManager();
		try {

			final FileInputStream fis = new FileInputStream("MuLog.conf");
			menager.readConfiguration(fis);
			// LogManager.getLogManager().readConfiguration()
			fis.close();
		} catch (final IOException ex) {
			getLogger("MuLoggerMenager").log(Level.SEVERE,
					"Cannot find/read configuration file", ex);
		} catch (final SecurityException ex) {
			getLogger("MuLoggerMenager").log(Level.SEVERE,
					"Cannot aces to configuatio file", ex);
		}

	}

	public LogManager menager = null;
	protected static MuLoggerMennager _instance;

	public static MuLoggerMennager getInstance() {
		if (_instance == null) {
			_instance = new MuLoggerMennager();
		}
		return _instance;
	}

	public static Logger getLogger(String name) {
		if (_instance == null) {
			_instance = new MuLoggerMennager();
		}
		return Logger.getLogger(name);
	}
}
