/**
 * *********************************** AVR SOLUTIONS * ***********************************
 *
 * @author David
 * @date 11/03/2021
 * @time 17:06
 * @version 1.0
 */
package com.avr.apps.docgen.service;

import com.avr.apps.docgen.service.generatorDocument.SaleOrderGenerator;
import com.avr.apps.docgen.utils.DocGenType;
import com.axelor.apps.account.db.repo.AnalyticMoveLineRepository;
import com.axelor.apps.base.AxelorException;
import com.axelor.apps.base.db.repo.PartnerRepository;
import com.axelor.apps.base.db.repo.TraceBackRepository;
import com.axelor.apps.base.service.administration.SequenceService;
import com.axelor.apps.base.service.user.UserService;
import com.axelor.apps.businessproduction.service.SaleOrderWorkflowServiceBusinessProductionImpl;
import com.axelor.apps.crm.service.app.AppCrmService;
import com.axelor.apps.production.service.app.AppProductionService;
import com.axelor.apps.production.service.productionorder.ProductionOrderSaleOrderService;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.apps.sale.db.repo.SaleOrderRepository;
import com.axelor.apps.sale.service.app.AppSaleService;
import com.axelor.apps.sale.service.config.SaleConfigService;
import com.axelor.apps.sale.service.saleorder.SaleOrderLineService;
import com.axelor.apps.supplychain.service.*;
import com.axelor.apps.supplychain.service.app.AppSupplychainService;
import com.axelor.studio.db.AppDocgen;
import com.google.inject.Inject;

public class SaleOrderWorkflowDocgenServiceImpl
    extends SaleOrderWorkflowServiceBusinessProductionImpl {

  protected final SaleOrderGenerator saleOrderGenerator;

  @Inject
  public SaleOrderWorkflowDocgenServiceImpl(
      SequenceService sequenceService,
      PartnerRepository partnerRepo,
      SaleOrderRepository saleOrderRepo,
      AppSaleService appSaleService,
      AppCrmService appCrmService,
      UserService userService,
      SaleOrderLineService saleOrderLineService,
      SaleOrderStockService saleOrderStockService,
      SaleOrderPurchaseService saleOrderPurchaseService,
      AppSupplychainService appSupplychainService,
      AccountingSituationSupplychainService accountingSituationSupplychainService,
      PartnerSupplychainService partnerSupplychainService,
      SaleConfigService saleConfigService,
      SaleOrderCheckAnalyticService saleOrderCheckAnalyticService,
      ProductionOrderSaleOrderService productionOrderSaleOrderService,
      AppProductionService appProductionService,
      AnalyticMoveLineRepository analyticMoveLineRepository,
      SaleOrderGenerator saleOrderGenerator) {
    super(
        sequenceService,
        partnerRepo,
        saleOrderRepo,
        appSaleService,
        appCrmService,
        userService,
        saleOrderLineService,
        saleOrderStockService,
        saleOrderPurchaseService,
        appSupplychainService,
        accountingSituationSupplychainService,
        partnerSupplychainService,
        saleConfigService,
        saleOrderCheckAnalyticService,
        productionOrderSaleOrderService,
        appProductionService,
        analyticMoveLineRepository);
    this.saleOrderGenerator = saleOrderGenerator;
  }

  @Override
  public void saveSaleOrderPDFAsAttachment(SaleOrder saleOrder) throws AxelorException {
    if (appSaleService.isApp("docgen")
        && appSaleService.getApp("docgen") != null
        && ((AppDocgen) appSaleService.getApp("docgen")).getEnableSaleOrder()) {
      if (!saleOrderGenerator.generateToAttachment(saleOrder, DocGenType.PDF)) {
        throw new AxelorException(
            TraceBackRepository.CATEGORY_NO_VALUE, saleOrderGenerator.getError());
      }
    }
  }
}
