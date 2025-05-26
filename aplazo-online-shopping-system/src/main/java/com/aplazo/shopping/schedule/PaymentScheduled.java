/**
 * 
 */
package com.aplazo.shopping.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.aplazo.shopping.enums.InstallmentStatus;
import com.aplazo.shopping.service.IPaymentService;

/**
 * @author CesarSalazar
 */
@Service
@Component
public class PaymentScheduled {

	private static final Logger log = LoggerFactory.getLogger(PaymentScheduled.class);
	
	@Autowired
	private IPaymentService iPaymentService;

	@Scheduled(cron = "0 * * * * *")
	public void searchPaymentsPendings() {
		log.info("Search payments pendings...");
		this.iPaymentService.findByPaymentStatus(InstallmentStatus.PENDING)
		.stream().forEach(status -> {
			log.info("Payment pending at: " + status.getScheduledPaymentDate());
		});
	}
}
