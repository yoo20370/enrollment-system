use enrollment_system;

-- users 샘플
INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
        'INSTRUCTOR',
        'hello1234@yoo.com',
        'qwer1234',
        'hello1234',
        'hello1234',
        'ACTIVE',
        '2026-05-13 00:00:00',
        '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
        'STUDENT',
        'hello5678@yoo.com',
        'qwer1234',
        'hello5678',
        'hello5678',
        'ACTIVE',
        '2026-05-13 00:00:00',
        '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')),
        'STUDENT',
        'hello9012@yoo.com',
        'qwer1234',
        'hello9012',
        'hello9012',
        'INACTIVE',
        '2026-05-13 00:00:00',
        '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', '')),
        'STUDENT',
        'hello4321@yoo.com',
        'qwer1234',
        'hello4321',
        'hello4321',
        'ACTIVE',
        '2026-05-13 00:00:00',
        '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
        'STUDENT', 'kim1234@test.com', 'qwer1234', 'kim1234', 'kim1234', 'ACTIVE',
        '2026-05-13 00:00:00', '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
        'STUDENT', 'park5678@test.com', 'qwer1234', 'park5678', 'park5678', 'ACTIVE',
        '2026-05-13 00:00:00', '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('77777777-7777-7777-7777-777777777777', '-', '')),
        'STUDENT', 'lee9012@test.com', 'qwer1234', 'lee9012', 'lee9012', 'ACTIVE',
        '2026-05-13 00:00:00', '2026-05-13 00:00:00');

INSERT INTO users (id, role, email, password, name, nickname, active_status, created_at, updated_at)
VALUES (UNHEX(REPLACE('88888888-8888-8888-8888-888888888888', '-', '')),
        'STUDENT', 'choi3456@test.com', 'qwer1234', 'choi3456', 'choi3456', 'ACTIVE',
        '2026-05-13 00:00:00', '2026-05-13 00:00:00');

-- DRAFT 강의
INSERT INTO courses (id, instructor_id, title, description, price, capacity, current_count, status, start_at, end_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000001', '-', '')),
           UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
           '스프링 부트 강의 (DRAFT)',
           '스프링 부트 기초부터 심화까지',
           50000, 30, 0, 'DRAFT',
           '2026-06-01 00:00:00', '2036-06-02 00:00:00', NOW(), NOW()
       );

-- OPEN 강의
INSERT INTO courses (id, instructor_id, title, description, price, capacity, current_count, status, start_at, end_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
           '자바 심화 강의 (OPEN)',
           '자바 심화 과정',
           30000, 30, 0, 'OPEN',
           '2026-06-01 00:00:00', '2036-06-02 00:00:00', NOW(), NOW()
       );

INSERT INTO courses (id, instructor_id, title, description, price, capacity, current_count, status, start_at, end_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000222', '-', '')),
           UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
           'MySQL 강의 (OPEN)',
           'MySQL 기초부터 실전까지',
           20000, 20, 0, 'OPEN',
           '2026-06-01 00:00:00', '2036-06-02 00:00:00', NOW(), NOW()
       );

-- OPEN 정원 꽉찬 강의
INSERT INTO courses (id, instructor_id, title, description, price, capacity, current_count, status, start_at, end_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000003', '-', '')),
           UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
           'JPA 강의 (OPEN, 정원 마감)',
           'JPA 완전 정복',
           40000, 30, 30, 'OPEN',
           '2026-06-01 00:00:00', '2036-06-02 00:00:00', NOW(), NOW()
       );



-- CLOSED 강의
INSERT INTO courses (id, instructor_id, title, description, price, capacity, current_count, status, start_at, end_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000004', '-', '')),
           UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')),
           'Docker 강의 (CLOSED)',
           'Docker 기초부터 실전까지',
           20000, 30, 0, 'CLOSED',
           '2026-06-01 00:00:00', '2036-06-02 00:00:00', NOW(), NOW()
       );

-- 수강 신청
INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000020', '-', '')),
           UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000222', '-', '')),
           'PENDING',
           null,
           null,
           '2026-05-13 11:25:07',
           '2026-05-13 11:32:03'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000021', '-', '')),
           UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CONFIRMED',
           '2026-05-13 11:27:19',
           null,
           '2026-05-13 11:25:07',
           '2026-05-13 11:27:19'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000022', '-', '')),
           UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CONFIRMED',
           '2026-05-13 11:27:19',
           null,
           '2026-05-13 11:25:07',
           '2026-05-13 11:27:19'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000023', '-', '')),
           UNHEX(REPLACE('77777777-7777-7777-7777-777777777777', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CONFIRMED',
           '2026-05-13 11:27:19',
           null,
           '2026-05-13 11:25:07',
           '2026-05-13 11:27:19'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000024', '-', '')),
           UNHEX(REPLACE('88888888-8888-8888-8888-888888888888', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CONFIRMED',
           '2026-05-13 11:27:19',
           null,
           '2026-05-13 11:25:07',
           '2026-05-13 11:27:19'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000025', '-', '')),
           UNHEX(REPLACE('88888888-8888-8888-8888-888888888888', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CANCELED',
           '2026-05-13 11:27:19',
           '2026-05-13 11:32:03',
           '2026-05-13 11:25:07',
           '2026-05-13 11:32:03'
       );

INSERT INTO enrollment (id, user_id, course_id, status, confirmed_at, cancelled_at, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000026', '-', '')),
           UNHEX(REPLACE('88888888-8888-8888-8888-888888888888', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000002', '-', '')),
           'CONFIRMED',
           '2025-05-13 11:27:19',
           null,
           '2025-05-13 11:25:07',
           '2025-05-13 11:27:19'
       );

INSERT INTO payment (id, user_id, enrollment_id, amount, status, paid_at, cancelled_at, transaction_id, provider, created_at, updated_at)
VALUES (
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000030', '-', '')),
           UNHEX(REPLACE('88888888-8888-8888-8888-888888888888', '-', '')),
           UNHEX(REPLACE('00000000-0000-0000-0000-000000000026', '-', '')),
           30000,
           'PAID',
           '2025-05-13 11:27:19',
           null,
           'mock-transaction-aaa',
           'TOSS',
           '2025-05-13 11:27:19',
           '2025-05-13 11:27:19'
       );