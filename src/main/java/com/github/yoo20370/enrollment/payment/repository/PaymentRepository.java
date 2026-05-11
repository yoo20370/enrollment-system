package com.github.yoo20370.enrollment.payment.repository;

import com.github.yoo20370.enrollment.payment.domain.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}
