package com.github.yoo20370.enrollment.payment.domain;

import com.github.yoo20370.enrollment.enrollment.domain.Enrollment;
import com.github.yoo20370.enrollment.global.domain.BaseEntity;
import com.github.yoo20370.enrollment.global.exception.ErrorCode;
import com.github.yoo20370.enrollment.payment.exception.PaymentException;
import com.github.yoo20370.enrollment.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="payment")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="enrollment_id")
    private Enrollment enrollment;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name="paid_at")
    private LocalDateTime paidAt;

    @Column(name="cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name="transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    public static Payment create(User user, Enrollment enrollment, Long amount,
            LocalDateTime paidAt, String transactionId, String provider
        ) {
        return Payment.builder()
            .user(user)
            .enrollment(enrollment)
            .amount(amount)
            .status(PaymentStatus.PAID)
            .paidAt(paidAt)
            .transactionId(transactionId)
            .provider(Provider.valueOf(provider))
            .build();
    }
}
