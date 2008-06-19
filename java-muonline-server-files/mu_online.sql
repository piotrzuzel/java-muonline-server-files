--
-- PostgreSQL database dump
--

SET client_encoding = 'UTF8';
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: DATABASE mu_online; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON DATABASE mu_online IS 'baza danych do mu online';


--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: 
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- Name: ch_stats; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE ch_stats AS (
	st_agi integer,
	st_str integer,
	st_vit integer,
	st_ene integer,
	st_com integer
);


ALTER TYPE public.ch_stats OWNER TO postgres;

--
-- Name: add_new_character(integer, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_new_character(u_id integer, name text, clas integer) RETURNS boolean
    AS $_$
 
DECLARE
base_stats RECORD; -- dane z character_base_stats
ilosc_p integer; --ilos postaci usera

BEGIN

--sprawdzamy ilosc postaci

SELECT u_ch_c INTO ilosc_p FROM users WHERE u_id=$1;

IF ilosc_p<5 then 





ilosc_p:=ilosc_p+1;

update users set u_ch_c=ilosc_p where u_id=$1;



--pobieramy temp wartosci dla danej klasy

SELECT * INTO base_stats FROM chatacter_base_stats WHERE ch_base_class=$3 ;

--dodajemy postac ...

INSERT INTO characters(

            us_id, ch_name, ch_class, ch_stat_lvl,

	    ch_stat_str, ch_stat_agi, 

            ch_stat_vit, ch_stat_enr, 

            ch_stat_com)

    VALUES ($1, $2, $3, 1, 

            base_stats.ch_base_str, base_stats.ch_base_agi, 

            base_stats.ch_base_vit, base_stats.ch_base_enr, 

            base_stats.ch_base_com);

--aktualizujemy ilosc postaci usera



--wyknane z powodzeniem

RETURN TRUE;

else 

return FALSE;

end if;
END;	
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_new_character(u_id integer, name text, clas integer) OWNER TO postgres;

--
-- Name: add_new_item(text, integer, integer, character[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_new_item(_name text, _windw_id integer, _slot integer, _hex character[]) RETURNS boolean
    AS $_$

DECLARE

ilosc integer;

name_ ALIAS FOR $1;

windw_id_ ALIAS FOR $2;

slot_ ALIAS FOR $3;

hex_ ALIAS FOR $4;

BEGIN

select count (*) INTO ilosc  from inwentories where inwentories.ch_name=name_ and inwentories.window_id=windw_id_ and  inwentories.slot=slot_;

if ilosc >0 then return false;

end if;



INSERT INTO inwentories(

            ch_name, window_id, slot, item_hex)

    VALUES (name_, windw_id_,slot_, hex_);



return true;

END;	

$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_new_item(_name text, _windw_id integer, _slot integer, _hex character[]) OWNER TO postgres;

--
-- Name: add_new_user(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_new_user(user1 text, pass text) RETURNS integer
    AS $_$
 
DECLARE
act date;

ret integer;
BEGIN
SELECT CURRENT_TIMESTAMP(0) into act;
INSERT INTO users(
            u_user, u_pass, u_flag, u_ch_c, u_create_acc_date
            )
    VALUES ($1, $2, 0, 0, act);

SELECT u_id into ret from users where u_user=$1;

return ret;

END;	
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.add_new_user(user1 text, pass text) OWNER TO postgres;

--
-- Name: delete_character(integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION delete_character(u_id integer, name text) RETURNS boolean
    AS $_$

DECLARE

ilosc_p integer; --ilos postaci usera
BEGIN
--sprawdzamy ilosc postaci
SELECT u_ch_c INTO ilosc_p FROM users WHERE u_id=$1;
IF ilosc_p>0 then 


ilosc_p:=ilosc_p-1;
update users set u_ch_c=ilosc_p where u_id=$1;

--pobieramy temp wartosci dla danej klasy
delete from characters where us_id=$1 and ch_name=$2;


RETURN TRUE;
else 
return FALSE;
end if;
END; 
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.delete_character(u_id integer, name text) OWNER TO postgres;

--
-- Name: move_item(text, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION move_item(_name text, _win_id_from integer, _slot_from integer, _win_id_to integer, _slot_to integer) RETURNS boolean
    AS $_$
DECLARE
ilosc integer;


name_ ALIAS FOR $1;
win_id_f ALIAS FOR $2;
slot_f ALIAS FOR $3;

win_id_t ALIAS FOR $4;

slot_t ALIAS FOR $5;
BEGIN
select count (*) INTO ilosc  from inwentories where inwentories.ch_name=name_ and inwentories.window_id=win_id_t and  inwentories.slot=slot_t;
if ilosc >0 then return false; -- miejsce zajete
end if;

UPDATE  inwentories set  window_id=win_id_t, slot=slot_t where ch_name=name_ and window_id=win_id_f and slot=slot_f ;

return true;
END;	
$_$
    LANGUAGE plpgsql;


ALTER FUNCTION public.move_item(_name text, _win_id_from integer, _slot_from integer, _win_id_to integer, _slot_to integer) OWNER TO postgres;

--
-- Name: AreaSpot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "AreaSpot_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public."AreaSpot_id_seq" OWNER TO postgres;

--
-- Name: AreaSpot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"AreaSpot_id_seq"', 1, false);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: AreaSpot; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "AreaSpot" (
    id integer DEFAULT nextval('"AreaSpot_id_seq"'::regclass) NOT NULL,
    x1 integer DEFAULT 0 NOT NULL,
    y1 integer DEFAULT 0 NOT NULL,
    x2 integer DEFAULT 0 NOT NULL,
    y2 integer DEFAULT 0 NOT NULL,
    "MonsterID" integer NOT NULL,
    "SpownTime" integer,
    "MapID" integer DEFAULT 0 NOT NULL,
    "SpownName" text
);


ALTER TABLE public."AreaSpot" OWNER TO postgres;

--
-- Name: PointSpot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "PointSpot_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public."PointSpot_id_seq" OWNER TO postgres;

--
-- Name: PointSpot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"PointSpot_id_seq"', 1, false);


--
-- Name: PointSpot; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE "PointSpot" (
    id integer DEFAULT nextval('"PointSpot_id_seq"'::regclass) NOT NULL,
    "SpotXPos" integer DEFAULT 0 NOT NULL,
    "SpotYPos" integer DEFAULT 0 NOT NULL,
    "MapID" integer NOT NULL,
    "MonsterID" integer NOT NULL,
    "SpownTime" integer NOT NULL,
    "SpownName" text
);


ALTER TABLE public."PointSpot" OWNER TO postgres;

--
-- Name: COLUMN "PointSpot"."SpownTime"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN "PointSpot"."SpownTime" IS 'czas respowna';


--
-- Name: characters; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE characters (
    us_id integer,
    ch_name character(10) NOT NULL,
    ch_class integer DEFAULT 0,
    ch_stat_lvl integer DEFAULT 1 NOT NULL,
    ch_stat_str integer DEFAULT 0,
    ch_stat_agi integer DEFAULT 0,
    ch_stat_vit integer DEFAULT 0,
    ch_stat_enr integer DEFAULT 0,
    ch_stat_com integer DEFAULT 0,
    ch_last_pos_map character(1),
    ch_last_pos_h character(1),
    ch_exp_act bigint,
    ch_exp_lp integer DEFAULT 0,
    ch_last_pos_x integer DEFAULT 170,
    ch_last_pos_y integer DEFAULT 127
);


ALTER TABLE public.characters OWNER TO postgres;

--
-- Name: TABLE characters; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE characters IS 'informacje na temat postaci ';


--
-- Name: COLUMN characters.ch_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_name IS 'nawa postaci';


--
-- Name: COLUMN characters.ch_class; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_class IS 'typ postaci';


--
-- Name: COLUMN characters.ch_stat_lvl; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_lvl IS 'lvl postaci';


--
-- Name: COLUMN characters.ch_stat_str; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_str IS 'str';


--
-- Name: COLUMN characters.ch_stat_agi; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_agi IS 'agi';


--
-- Name: COLUMN characters.ch_stat_vit; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_vit IS 'itality';


--
-- Name: COLUMN characters.ch_stat_enr; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_enr IS 'enr';


--
-- Name: COLUMN characters.ch_stat_com; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_stat_com IS 'command
';


--
-- Name: COLUMN characters.ch_last_pos_map; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_last_pos_map IS 'ostatnia mapa';


--
-- Name: COLUMN characters.ch_last_pos_h; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN characters.ch_last_pos_h IS 'ostatni kierune patrzenia';


--
-- Name: chatacter_base_stats; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE chatacter_base_stats (
    ch_base_class integer,
    ch_base_agi integer,
    ch_base_str integer,
    ch_base_vit integer,
    ch_base_enr integer,
    ch_base_com integer,
    ch_base_pos_map character(1),
    ch_base_lp integer DEFAULT 5,
    ch_base_clasname text
);


ALTER TABLE public.chatacter_base_stats OWNER TO postgres;

--
-- Name: TABLE chatacter_base_stats; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE chatacter_base_stats IS 'definicje startowych bazowych statystyk postaci';


--
-- Name: COLUMN chatacter_base_stats.ch_base_class; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN chatacter_base_stats.ch_base_class IS 'klasa postaci';


--
-- Name: COLUMN chatacter_base_stats.ch_base_agi; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN chatacter_base_stats.ch_base_agi IS 'wartosc aglity';


--
-- Name: inwentories_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE inwentories_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.inwentories_id_seq OWNER TO postgres;

--
-- Name: inwentories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('inwentories_id_seq', 5, true);


--
-- Name: inwentories; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE inwentories (
    ch_name character(10) NOT NULL,
    window_id integer,
    slot integer NOT NULL,
    item_hex character(5),
    id integer DEFAULT nextval('inwentories_id_seq'::regclass) NOT NULL
);


ALTER TABLE public.inwentories OWNER TO postgres;

--
-- Name: COLUMN inwentories.ch_name; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN inwentories.ch_name IS 'klucz z czaacters tabeli';


--
-- Name: COLUMN inwentories.window_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN inwentories.window_id IS 'item w otpknie : 0 -inwentoru 1 store i';


--
-- Name: COLUMN inwentories.slot; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN inwentories.slot IS 'sot w dnaym oknie';


--
-- Name: COLUMN inwentories.item_hex; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN inwentories.item_hex IS 'hex itemu';


--
-- Name: maps; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE maps (
    map_id integer NOT NULL,
    name text
);


ALTER TABLE public.maps OWNER TO postgres;

--
-- Name: TABLE maps; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE maps IS 'mapy w mu';


--
-- Name: monster_base_stats; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE monster_base_stats (
    monster_id integer NOT NULL,
    rate integer,
    name text,
    "level" integer,
    hp integer,
    mp integer,
    damagemin integer,
    damagemax integer,
    defence integer,
    magicdefence integer,
    attackrating integer,
    successfulbloking integer,
    moverange integer,
    attacktype integer,
    attackrange integer,
    viewrange integer,
    movespeed integer,
    attackspeed integer,
    regentime integer,
    attribute integer,
    itemrate integer,
    moneyrate integer,
    maxitemlevel integer,
    skilli integer
);


ALTER TABLE public.monster_base_stats OWNER TO postgres;

--
-- Name: TABLE monster_base_stats; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE monster_base_stats IS 'statystyki potworow wyciagniete z org servera';


--
-- Name: users_u_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_u_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 0
    CACHE 1;


ALTER TABLE public.users_u_id_seq OWNER TO postgres;

--
-- Name: users_u_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('users_u_id_seq', 1, true);


SET default_with_oids = true;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    u_user character(10) NOT NULL,
    u_pass character(10) NOT NULL,
    u_flag integer DEFAULT 0,
    u_ch_c integer DEFAULT 0 NOT NULL,
    u_id integer DEFAULT nextval('users_u_id_seq'::regclass) NOT NULL,
    u_create_acc_date date,
    u_last_login_date date,
    u_last_login_ip inet,
    u_fail_log_count integer DEFAULT 0,
    u_date_to_blocked date
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON TABLE users IS 'tabela zawiera informacje na temat userow w bd';


--
-- Name: COLUMN users.u_user; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_user IS 'nazwa kata';


--
-- Name: COLUMN users.u_pass; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_pass IS 'chaslo do kata';


--
-- Name: COLUMN users.u_flag; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_flag IS 'flaga [czy polaczony cz nie]';


--
-- Name: COLUMN users.u_ch_c; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_ch_c IS 'ilosc postaci w bd';


--
-- Name: COLUMN users.u_id; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_id IS 'e';


--
-- Name: COLUMN users.u_create_acc_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_create_acc_date IS 'data utozenie acc';


--
-- Name: COLUMN users.u_last_login_date; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_last_login_date IS 'ostatnie zalogowanie';


--
-- Name: COLUMN users.u_last_login_ip; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_last_login_ip IS 'ostatni ip logowania';


--
-- Name: COLUMN users.u_fail_log_count; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_fail_log_count IS 'ilosc zlych wpisanych pass';


--
-- Name: COLUMN users.u_date_to_blocked; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON COLUMN users.u_date_to_blocked IS 'data odblokowania kata[jesli zablokowane]';


--
-- Data for Name: AreaSpot; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: PointSpot; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: characters; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO characters VALUES (0, 'adminDW   ', 0, 40, 15, 18, 15, 200, 0, '0', '1', NULL, 0, 170, 127);
INSERT INTO characters VALUES (1, 'avamps4   ', 32, 40, 28, 20, 25, 200, 0, '0', '1', NULL, 0, 170, 128);
INSERT INTO characters VALUES (1, 'vampDW    ', 0, 40, 15, 18, 15, 200, 0, '0', '1', NULL, 0, 170, 127);


--
-- Data for Name: chatacter_base_stats; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO chatacter_base_stats VALUES (0, 18, 15, 15, 30, 0, NULL, 5, 'Dark Wizzard');
INSERT INTO chatacter_base_stats VALUES (16, 18, 15, 15, 30, 0, NULL, 6, 'Soul Master');
INSERT INTO chatacter_base_stats VALUES (32, 20, 28, 25, 10, 0, NULL, 5, 'Dark Knight');
INSERT INTO chatacter_base_stats VALUES (48, 20, 28, 25, 10, 0, NULL, 6, 'Blad Knight');
INSERT INTO chatacter_base_stats VALUES (64, 25, 22, 20, 15, 0, NULL, 5, 'Fairy Elf');
INSERT INTO chatacter_base_stats VALUES (80, 25, 22, 20, 15, 0, NULL, 6, 'Mouse Elf');
INSERT INTO chatacter_base_stats VALUES (96, 26, 26, 26, 26, 0, NULL, 7, 'Magic gladriator');
INSERT INTO chatacter_base_stats VALUES (128, 20, 26, 20, 15, 0, NULL, 7, 'Dark Lord');
INSERT INTO chatacter_base_stats VALUES (0, 18, 15, 15, 30, 0, NULL, 5, 'Dark Wizzard');
INSERT INTO chatacter_base_stats VALUES (16, 18, 15, 15, 30, 0, NULL, 6, 'Soul Master');
INSERT INTO chatacter_base_stats VALUES (32, 20, 28, 25, 10, 0, NULL, 5, 'Dark Knight');
INSERT INTO chatacter_base_stats VALUES (48, 20, 28, 25, 10, 0, NULL, 6, 'Blad Knight');
INSERT INTO chatacter_base_stats VALUES (64, 25, 22, 20, 15, 0, NULL, 5, 'Fairy Elf');
INSERT INTO chatacter_base_stats VALUES (80, 25, 22, 20, 15, 0, NULL, 6, 'Mouse Elf');
INSERT INTO chatacter_base_stats VALUES (96, 26, 26, 26, 26, 0, NULL, 7, 'Magic gladriator');
INSERT INTO chatacter_base_stats VALUES (128, 20, 26, 20, 15, 0, NULL, 7, 'Dark Lord');


--
-- Data for Name: inwentories; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: maps; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO maps VALUES (0, 'Lorencia');
INSERT INTO maps VALUES (1, 'Dungeon');
INSERT INTO maps VALUES (2, 'Davias');
INSERT INTO maps VALUES (3, 'noria');
INSERT INTO maps VALUES (4, 'losttower');
INSERT INTO maps VALUES (5, 'Extile');
INSERT INTO maps VALUES (6, 'atlans');
INSERT INTO maps VALUES (8, 'tarkan');
INSERT INTO maps VALUES (9, 'devil square ');
INSERT INTO maps VALUES (10, 'icarus');


--
-- Data for Name: monster_base_stats; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO users VALUES ('mikione   ', 'michalki1 ', 0, 2, 1, '2007-08-23', NULL, NULL, 0, NULL);
INSERT INTO users VALUES ('admin1    ', 'admin1    ', 0, 1, 0, '2007-08-23', NULL, NULL, 0, NULL);


--
-- Name: ch_name; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT ch_name UNIQUE (ch_name);


--
-- Name: CONSTRAINT ch_name ON characters; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT ch_name ON characters IS 'sprawdzanie czy postac ma unikalny nik';


--
-- Name: ch_name_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT ch_name_pkey PRIMARY KEY (ch_name);


--
-- Name: CONSTRAINT ch_name_pkey ON characters; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT ch_name_pkey ON characters IS 'primery key';


--
-- Name: ids; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "AreaSpot"
    ADD CONSTRAINT ids PRIMARY KEY (id);


--
-- Name: item_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY inwentories
    ADD CONSTRAINT item_id PRIMARY KEY (id);


--
-- Name: key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY "PointSpot"
    ADD CONSTRAINT "key" PRIMARY KEY (id);


--
-- Name: map_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maps
    ADD CONSTRAINT map_id PRIMARY KEY (map_id);


--
-- Name: mob_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY monster_base_stats
    ADD CONSTRAINT mob_id UNIQUE (monster_id);


--
-- Name: CONSTRAINT mob_id ON monster_base_stats; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT mob_id ON monster_base_stats IS 'kazdy mob ma unikalny swoj kod';


--
-- Name: u_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT u_id PRIMARY KEY (u_id);


--
-- Name: u_name; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT u_name UNIQUE (u_user, u_id, u_pass);


--
-- Name: fki_last_pos_map; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX fki_last_pos_map ON characters USING btree (ch_last_pos_map);


--
-- Name: last_pos_map; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT last_pos_map FOREIGN KEY (ch_last_pos_map) REFERENCES maps(map_id);


--
-- Name: name_of_character; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY inwentories
    ADD CONSTRAINT name_of_character FOREIGN KEY (ch_name) REFERENCES characters(ch_name) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: us_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT us_id FOREIGN KEY (us_id) REFERENCES users(u_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

