package com.github.yoo20370.enrollment.payment.service;

import com.github.yoo20370.enrollment.enrollment.repository.EnrollmentRepository;
import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.enrollment.exception.EnrollmentException;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.payment.controller.response.PaymentResponse;
import com.github.yoo20370.enrollment.payment.controller.response.PaymentResult;
import com.github.yoo20370.enrollment.payment.domain.Payment;
import com.github.yoo20370.enrollment.payment.exception.PaymentException;
import com.github.yoo20370.enrollment.payment.repository.PaymentRepository;
import com.github.yoo20370.enrollment.user.domain.User;
import com.github.yoo20370.enrollment.user.exception.UserException;
import com.github.yoo20370.enrollment.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PaymentGateway paymentGateway;

    @Transactional
    public PaymentResponse pay(UUID enrollmentId, UUID userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new UserException(ErrorCode.USER_NOT_FOUND)
            );
        user.validateActiveStatus();

        Enrollment enrollment = enrollmentRepository.findWithLockById(enrollmentId)
            .orElseThrow(
                () -> new EnrollmentException(ErrorCode.ENROLLMENT_NOT_FOUND)
            );
        enrollment.validatePending();
        enrollment.validateOwner(userId);

        PaymentResult paymentResult = paymentGateway.pay(enrollment.getCourse().getPrice());

        if (paymentResult.isFailed()) {
            throw new PaymentException(ErrorCode.PAYMENT_FAILED);
        }

        Payment payment = Payment.create(
            user,
            enrollment,
            enrollment.getCourse().getPrice(),
            paymentResult.getPaidAt(),
            paymentResult.getTransactionId(),
            paymentResult.getProvider()
        );
        Payment savedPayment = paymentRepository.save(payment);

        enrollment.confirm();

        return PaymentResponse.of(savedPayment);
    }
}
