--
-- PostgreSQL database dump
--

-- Dumped from database version 10.4
-- Dumped by pg_dump version 10.4

-- Started on 2019-06-16 23:06:54

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2807 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 17917)
-- Name: benutzer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.benutzer (
    nutzername character varying NOT NULL,
    passwort character varying NOT NULL
);


ALTER TABLE public.benutzer OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 17925)
-- Name: statistik; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.statistik (
    nutzername character varying,
    gewonnene_spiele integer NOT NULL,
    spiele_gesamt integer NOT NULL,
    max_punkte integer NOT NULL
);


ALTER TABLE public.statistik OWNER TO postgres;

--
-- TOC entry 2798 (class 0 OID 17917)
-- Dependencies: 196
-- Data for Name: benutzer; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2799 (class 0 OID 17925)
-- Dependencies: 197
-- Data for Name: statistik; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2675 (class 2606 OID 17924)
-- Name: benutzer benutzer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.benutzer
    ADD CONSTRAINT benutzer_pkey PRIMARY KEY (nutzername);


--
-- TOC entry 2676 (class 2606 OID 17931)
-- Name: statistik statistik_nutzername_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.statistik
    ADD CONSTRAINT statistik_nutzername_fkey FOREIGN KEY (nutzername) REFERENCES public.benutzer(nutzername);


-- Completed on 2019-06-16 23:06:54

--
-- PostgreSQL database dump complete
--

