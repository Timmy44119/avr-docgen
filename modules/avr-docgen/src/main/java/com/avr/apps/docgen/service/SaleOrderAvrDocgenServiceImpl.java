package com.avr.apps.docgen.service;

import com.avr.apps.docgen.service.interfaces.SaleOrderAvrDocgenService;
import com.axelor.apps.account.db.PaymentCondition;
import com.axelor.apps.account.db.PaymentConditionLine;
import com.axelor.apps.account.db.repo.PaymentConditionRepository;
import com.axelor.apps.sale.db.SaleOrder;
import com.axelor.common.ObjectUtils;
import com.google.inject.Singleton;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

@Singleton
public class SaleOrderAvrDocgenServiceImpl implements SaleOrderAvrDocgenService {

  @Override
  public LocalDate computedDeadLineDate(SaleOrder saleOrder) {
    PaymentCondition paymentCondition = saleOrder.getPaymentCondition();
    if (ObjectUtils.isEmpty(paymentCondition)) return null;
    if (ObjectUtils.isEmpty(saleOrder.getDeadlineDate())) return null;
    PaymentConditionLine paymentConditionLine =
        Collections.max(
            paymentCondition.getPaymentConditionLineList(),
            Comparator.comparing(PaymentConditionLine::getPaymentTime));
    if (paymentConditionLine.getPaymentTime() == 0) return saleOrder.getDeadlineDate();
    if (paymentConditionLine
        .getPeriodTypeSelect()
        .equals(PaymentConditionRepository.PERIOD_TYPE_DAYS)) {
      return saleOrder.getDeadlineDate().plusDays(paymentConditionLine.getPaymentTime());
    } else if (paymentConditionLine
        .getPeriodTypeSelect()
        .equals(PaymentConditionRepository.PERIOD_TYPE_MONTH)) {
      return saleOrder.getDeadlineDate().plusMonths(paymentConditionLine.getPaymentTime());
    } else {
      return saleOrder.getDeadlineDate();
    }
  }
}
