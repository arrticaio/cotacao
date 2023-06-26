package org.br.mineracao.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineracao.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationSheduler {

    private final Logger LOG = LoggerFactory.getLogger(QuotationSheduler.class);

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    public  void schedule(){
        LOG.info("-- Executabdo sheduler agora --");
        quotationService.getCurrencyPrice();
    }

}
