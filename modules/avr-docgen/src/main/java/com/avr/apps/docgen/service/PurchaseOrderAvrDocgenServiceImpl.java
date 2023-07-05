package com.avr.apps.docgen.service;

import com.avr.apps.docgen.service.interfaces.PurchaseOrderAvrDocgenService;
import com.axelor.apps.account.db.PaymentCondition;
import com.axelor.apps.account.db.PaymentConditionLine;
import com.axelor.apps.account.db.repo.PaymentConditionRepository;
import com.axelor.apps.purchase.db.PurchaseOrder;
import com.axelor.common.ObjectUtils;
import com.google.inject.Singleton;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

@Singleton
public class PurchaseOrderAvrDocgenServiceImpl implements PurchaseOrderAvrDocgenService {

  @Override
  public LocalDate computedDeadLineDate(PurchaseOrder purchaseOrder) {
    PaymentCondition paymentCondition = purchaseOrder.getPaymentCondition();
    if (ObjectUtils.isEmpty(paymentCondition)) return null;
    if (ObjectUtils.isEmpty(purchaseOrder.getDeadlineDate())) return null;
    PaymentConditionLine paymentConditionLine =
        Collections.max(
            paymentCondition.getPaymentConditionLineList(),
            Comparator.comparing(PaymentConditionLine::getPaymentTime));
    if (paymentConditionLine.getPaymentTime() == 0) return purchaseOrder.getDeadlineDate();
    if (paymentConditionLine
        .getPeriodTypeSelect()
        .equals(PaymentConditionRepository.PERIOD_TYPE_DAYS)) {
      return purchaseOrder.getDeadlineDate().plusDays(paymentConditionLine.getPaymentTime());
    } else if (paymentConditionLine
        .getPeriodTypeSelect()
        .equals(PaymentConditionRepository.PERIOD_TYPE_MONTH)) {
      return purchaseOrder.getDeadlineDate().plusMonths(paymentConditionLine.getPaymentTime());
    } else {
      return purchaseOrder.getDeadlineDate();
    }
  }
}
