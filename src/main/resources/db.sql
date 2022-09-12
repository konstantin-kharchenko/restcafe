CREATE DATABASE cafe
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Belarus.1251'
    LC_CTYPE = 'Russian_Belarus.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public.users
(
    user_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    login character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(100) COLLATE pg_catalog."default" NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.administrators
(
    administrator_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    status_id smallint NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT administrators_pkey PRIMARY KEY (administrator_id),
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.administrators
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.clients
(
    client_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    loyalty_points double precision NOT NULL,
    user_id integer NOT NULL DEFAULT 0,
    CONSTRAINT clients_pkey PRIMARY KEY (client_id),
    CONSTRAINT "user" FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.clients
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.ingredients
(
    ingredient_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT ingredients_pkey PRIMARY KEY (ingredient_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.ingredients
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.orders
(
    order_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    client_id integer NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (order_id),
    CONSTRAINT client FOREIGN KEY (client_id)
        REFERENCES public.clients (client_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.orders
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.products
(
    product_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT products_pkey PRIMARY KEY (product_id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.orders_products
(
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    CONSTRAINT "order" FOREIGN KEY (order_id)
        REFERENCES public.orders (order_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT product FOREIGN KEY (product_id)
        REFERENCES public.products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.orders_products
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public.products_ingredients
(
    product_id integer NOT NULL,
    grams double precision NOT NULL,
    ingredient_id integer NOT NULL,
    CONSTRAINT ingredient FOREIGN KEY (ingredient_id)
        REFERENCES public.ingredients (ingredient_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT product FOREIGN KEY (product_id)
        REFERENCES public.products (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products_ingredients
    OWNER to postgres;