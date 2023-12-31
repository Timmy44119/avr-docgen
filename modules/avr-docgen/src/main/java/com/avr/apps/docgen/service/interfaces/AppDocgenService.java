/**
 * *********************************** AVR SOLUTIONS ***********************************
 *
 * @author David
 * @date 01/06/2021
 * @time 18:20 @Update 01/06/2021
 * @version 1.0
 */
package com.avr.apps.docgen.service.interfaces;

import com.axelor.apps.base.service.app.AppBaseService;
import com.axelor.studio.db.AppDocgen;

public interface AppDocgenService extends AppBaseService {
  AppDocgen getAppDocgen();
}
