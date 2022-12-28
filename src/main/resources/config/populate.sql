INSERT INTO public.currency (symbol, rate, date_cur)
VALUES ('USD/KZT', 2, NOW()),
        ('USD/RUB', 3, NOW());

INSERT INTO public.accounts (owner_id, number, balance)
VALUES (1, '2222222', 200000);

INSERT INTO public.limits (account_id, limit_op, date_set, type_limit)
VALUES (1, 2000, NOW(), 'SERVICE'),
       (1, 1000, NOW(), 'PRODUCT');