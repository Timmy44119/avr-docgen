/**
 * *********************************** AVR SOLUTIONS ***********************************
 *
 * @author David
 * @date 01/06/2021
 * @time 18:18 @Update 01/06/2021
 * @version 1.0
 */
package com.avr.apps.docgen.service;

import com.avr.apps.docgen.service.interfaces.AppDocgenService;
import com.axelor.apps.base.service.app.AppBaseServiceImpl;
import com.axelor.meta.MetaFiles;
import com.axelor.meta.db.repo.MetaModelRepository;
import com.axelor.studio.app.service.AppVersionService;
import com.axelor.studio.db.AppDocgen;
import com.axelor.studio.db.repo.AppDocgenRepository;
import com.axelor.studio.db.repo.AppRepository;
import com.axelor.studio.service.AppSettingsStudioService;
import com.google.inject.Inject;

public class AppDocgenServiceImpl extends AppBaseServiceImpl implements AppDocgenService {

  @Inject
  public AppDocgenServiceImpl(
      AppRepository appRepo,
      MetaFiles metaFiles,
      AppVersionService appVersionService,
      MetaModelRepository metaModelRepo,
      AppSettingsStudioService appSettingsStudioService) {
    super(appRepo, metaFiles, appVersionService, metaModelRepo, appSettingsStudioService);
  }

  @Inject protected AppDocgenRepository appAccountRepo;

  @Override
  public AppDocgen getAppDocgen() {
    return appAccountRepo.all().fetchOne();
  }
}
