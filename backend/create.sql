--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: car_washes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.car_washes (
    car_wash_id bigint NOT NULL,
    location character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL
);


ALTER TABLE public.car_washes OWNER TO postgres;

--
-- Name: car_washes_car_wash_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.car_washes_car_wash_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.car_washes_car_wash_id_seq OWNER TO postgres;

--
-- Name: car_washes_car_wash_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.car_washes_car_wash_id_seq OWNED BY public.car_washes.car_wash_id;


--
-- Name: confirmation_codes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.confirmation_codes (
    confirmation_code_id bigint NOT NULL,
    code character varying(255) NOT NULL,
    contact character varying(255) NOT NULL
);


ALTER TABLE public.confirmation_codes OWNER TO postgres;

--
-- Name: confirmation_codes_confirmation_code_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.confirmation_codes_confirmation_code_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.confirmation_codes_confirmation_code_id_seq OWNER TO postgres;

--
-- Name: confirmation_codes_confirmation_code_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.confirmation_codes_confirmation_code_id_seq OWNED BY public.confirmation_codes.confirmation_code_id;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    id bigint NOT NULL,
    car_wash_id bigint NOT NULL,
    user_id bigint NOT NULL,
    deleted boolean NOT NULL
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employees_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employees_id_seq OWNER TO postgres;

--
-- Name: employees_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employees_id_seq OWNED BY public.employees.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_id bigint NOT NULL,
    date date NOT NULL,
    grade double precision NOT NULL,
    text character varying(255),
    status boolean NOT NULL,
    price integer NOT NULL,
    service_id bigint NOT NULL,
    car_wash_id bigint NOT NULL,
    employee_id bigint,
    user_id bigint NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_order_id_seq OWNER TO postgres;

--
-- Name: orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_order_id_seq OWNED BY public.orders.order_id;


--
-- Name: services; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.services (
    service_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    price integer NOT NULL,
    car_wash_id bigint NOT NULL,
    deleted boolean NOT NULL
);


ALTER TABLE public.services OWNER TO postgres;

--
-- Name: services_service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.services_service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.services_service_id_seq OWNER TO postgres;

--
-- Name: services_service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.services_service_id_seq OWNED BY public.services.service_id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255) NOT NULL,
    phone character varying(255) NOT NULL,
    password character varying(255),
    role character varying(255),
    status character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: car_washes car_wash_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_washes ALTER COLUMN car_wash_id SET DEFAULT nextval('public.car_washes_car_wash_id_seq'::regclass);


--
-- Name: confirmation_codes confirmation_code_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmation_codes ALTER COLUMN confirmation_code_id SET DEFAULT nextval('public.confirmation_codes_confirmation_code_id_seq'::regclass);


--
-- Name: employees id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees ALTER COLUMN id SET DEFAULT nextval('public.employees_id_seq'::regclass);


--
-- Name: orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.orders_order_id_seq'::regclass);


--
-- Name: services service_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services ALTER COLUMN service_id SET DEFAULT nextval('public.services_service_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Data for Name: car_washes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car_washes (car_wash_id, location, name, phone) FROM stdin;
1	ул. Белинского 32a	Belka	89234350945
2	Верхневолжская наб.14	Naba	89654356675
3	Мещерский бульвар 5	Meshchera	82346579834
4	Комсомольская площадь 2а	Komsomolka	89325432654
5	ул. Родионова 14	Rodinka	89437893412
\.


--
-- Data for Name: confirmation_codes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.confirmation_codes (confirmation_code_id, code, contact) FROM stdin;
\.


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (id, car_wash_id, user_id, deleted) FROM stdin;
1	1	3	f
2	1	5	f
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (order_id, date, grade, text, status, price, service_id, car_wash_id, employee_id, user_id) FROM stdin;
1	2022-04-06	5	Круто	t	15000	1	1	2	2
2	2022-04-05	0		f	5000	2	1	2	2
3	2022-04-04	3	Норм	t	13000	3	1	2	6
4	2022-04-07	4		t	13000	4	1	2	4
5	2022-04-07	0		f	13000	1	1	2	4
\.


--
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.services (service_id, name, price, car_wash_id, deleted) FROM stdin;
1	АНТИДОЖДЬ	2000	1	f
2	ДЕТЕЙЛИНГ ХИМЧИСТКА	13000	1	f
3	ДЕТЕЙЛИНГ МОЙКА	3000	1	f
4	ПОЛИРОВКА ФАР	2000	1	f
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, name, phone, password, role, status) FROM stdin;
1	admin@mail.ru	Admin	89876543210	$2a$12$sUzUs.5FC7MA2LiBZ/g.zu.1RIiQvzxsWGDhf2tjfPMVJ5tk1u3fi	ADMIN	ACTIVE
2	customer@mail.ru	Customer	88005553535	$2a$12$EKaWdQUj.1m1DFudmsimAOGLfS1f4uVL.iGTfY4ztQfjbSXQB4CcK	CUSTOMER	ACTIVE
3	employee@mail.ru	Employee	81234567890	$2a$12$XI7HennTEQg4We/Uy4zLKux0KRLMsyIrVXplongKPN7iypQbi21vG	EMPLOYEE	ACTIVE
4	lilgud@mail.ru	Илья	89999999999	$2a$12$b7B8lbetQLkBR3n3HKSgp.N0O3iRFwWSinAD/VdO90rPeQcwBG8WK	CUSTOMER	ACTIVE
5	ivan@bk.ru	Иван	87776665544	$2a$12$gFkROho2pekL0UH7kctJtOfZteAC/hf191szI60c1TbYlxmq4nFwK	EMPLOYEE	ACTIVE
6	lilpad@bk.ru	Петя	89991112255	$2a$12$tFh8TrNQm5oSunTPQSR4GuY9vpFCODmmOO9dhZKydgdsHdj8EgqSu	CUSTOMER	ACTIVE
\.


--
-- Name: car_washes_car_wash_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.car_washes_car_wash_id_seq', 5, true);


--
-- Name: confirmation_codes_confirmation_code_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.confirmation_codes_confirmation_code_id_seq', 1, true);


--
-- Name: employees_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employees_id_seq', 2, true);


--
-- Name: orders_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_order_id_seq', 5, true);


--
-- Name: services_service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.services_service_id_seq', 4, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 6, true);


--
-- Name: car_washes car_washes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.car_washes
    ADD CONSTRAINT car_washes_pkey PRIMARY KEY (car_wash_id);


--
-- Name: confirmation_codes confirmation_codes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confirmation_codes
    ADD CONSTRAINT confirmation_codes_pkey PRIMARY KEY (confirmation_code_id);


--
-- Name: employees employees_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employees_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);


--
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (service_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: orders fk3l6j0unv4rbx4b8tp5cd17xdj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk3l6j0unv4rbx4b8tp5cd17xdj FOREIGN KEY (car_wash_id) REFERENCES public.car_washes(car_wash_id);


--
-- Name: employees fk66qf3y81urtiknh6wuqms9l85; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fk66qf3y81urtiknh6wuqms9l85 FOREIGN KEY (car_wash_id) REFERENCES public.car_washes(car_wash_id);


--
-- Name: employees fk69x3vjuy1t5p18a5llb8h2fjx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT fk69x3vjuy1t5p18a5llb8h2fjx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: services fkclnkd4md84dbhiemunf44sk7e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT fkclnkd4md84dbhiemunf44sk7e FOREIGN KEY (car_wash_id) REFERENCES public.car_washes(car_wash_id);


--
-- PostgreSQL database dump complete
--

