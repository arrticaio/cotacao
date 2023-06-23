package org.br.mineracao.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineracao.service.QuotationService;

@ApplicationScoped
public class QuotationSheduler {

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    public  void schedule(){
        quotationService.getCurrencyPrice();
    }

}
