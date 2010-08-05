--
-- PostgreSQL database dump
--

-- Started on 2008-06-20 11:00:47

SET client_encoding = 'SQL_ASCII';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.characters DROP CONSTRAINT us_id;
ALTER TABLE ONLY public.inwentories DROP CONSTRAINT name_of_character;
ALTER TABLE ONLY public.characters DROP CONSTRAINT last_pos_map;
DROP INDEX public.fki_last_pos_map;
ALTER TABLE ONLY public.users DROP CONSTRAINT u_name;
ALTER TABLE ONLY public.users DROP CONSTRAINT u_id;
ALTER TABLE ONLY public.monster_base_stats DROP CONSTRAINT mob_id;
ALTER TABLE ONLY public.maps DROP CONSTRAINT map_id;
ALTER TABLE ONLY public."PointSpot" DROP CONSTRAINT "key";
ALTER TABLE ONLY public.inwentories DROP CONSTRAINT item_id;
ALTER TABLE ONLY public."AreaSpot" DROP CONSTRAINT ids;
ALTER TABLE ONLY public."guildsMemembers" DROP CONSTRAINT guild_memember_id;
ALTER TABLE ONLY public.characters DROP CONSTRAINT ch_name_pkey;
ALTER TABLE ONLY public.characters DROP CONSTRAINT ch_name;
ALTER TABLE ONLY public."guildNames" DROP CONSTRAINT "Guld_name_id";
ALTER TABLE public."guildsMemembers" ALTER COLUMN guild_memember_id DROP DEFAULT;
ALTER TABLE public."guildNames" ALTER COLUMN id DROP DEFAULT;
DROP TABLE public.users;
DROP SEQUENCE public.users_u_id_seq;
DROP TABLE public.monster_base_stats;
DROP TABLE public.maps;
DROP TABLE public.inwentories;
DROP SEQUENCE public.inwentories_id_seq;
DROP SEQUENCE public."guildsNames_guild_memember_id_seq";
DROP TABLE public."guildsMemembers";
DROP SEQUENCE public."guildNames_id_seq";
DROP TABLE public."guildNames";
DROP TABLE public.chatacter_base_stats;
DROP TABLE public.characters;
DROP TABLE public."PointSpot";
DROP SEQUENCE public."PointSpot_id_seq";
DROP TABLE public."AreaSpot";
DROP SEQUENCE public."AreaSpot_id_seq";
DROP FUNCTION public.move_item(_name text, _win_id_from integer, _slot_from integer, _win_id_to integer, _slot_to integer);
DROP FUNCTION public.delete_character(u_id integer, name text);
DROP FUNCTION public.add_new_user(user1 text, pass text);
DROP FUNCTION public.add_new_item(_name text, _windw_id integer, _slot integer, _hex character[]);
DROP FUNCTION public.add_new_character(u_id integer, name text, clas integer);
DROP TYPE public.ch_stats;
DROP PROCEDURAL LANGUAGE plpgsql;
DROP SCHEMA public;
--
-- TOC entry 1709 (class 1262 OID 42722)
-- Dependencies: 1708
-- Name: mu_online; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON DATABASE mu_online IS 'baza danych do mu online';


--
-- TOC entry 5 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 1710 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 290 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


--
-- TOC entry 266 (class 1247 OID 42724)
-- Dependencies: 1295
-- Name: ch_stats; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE ch_stats AS (
	st_agi integer,
	st_str integer,
	st_vit integer,
	st_ene integer,
	st_com integer
);


--
-- TOC entry 19 (class 1255 OID 42725)
-- Dependencies: 5 290
-- Name: add_new_character(integer, text, integer); Type: FUNCTION; Schema: public; Owner: -
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


--
-- TOC entry 20 (class 1255 OID 42726)
-- Dependencies: 5 290
-- Name: add_new_item(text, integer, integer, character[]); Type: FUNCTION; Schema: public; Owner: -
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


--
-- TOC entry 21 (class 1255 OID 42727)
-- Dependencies: 5 290
-- Name: add_new_user(text, text); Type: FUNCTION; Schema: public; Owner: -
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


--
-- TOC entry 22 (class 1255 OID 42728)
-- Dependencies: 290 5
-- Name: delete_character(integer, text); Type: FUNCTION; Schema: public; Owner: -
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


--
-- TOC entry 23 (class 1255 OID 42729)
-- Dependencies: 290 5
-- Name: move_item(text, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: -
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


--
-- TOC entry 1296 (class 1259 OID 42730)
-- Dependencies: 5
-- Name: AreaSpot_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "AreaSpot_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1712 (class 0 OID 0)
-- Dependencies: 1296
-- Name: AreaSpot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"AreaSpot_id_seq"', 1, false);


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1297 (class 1259 OID 42732)
-- Dependencies: 1642 1643 1644 1645 1646 1647 5
-- Name: AreaSpot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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


--
-- TOC entry 1298 (class 1259 OID 42743)
-- Dependencies: 5
-- Name: PointSpot_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "PointSpot_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1713 (class 0 OID 0)
-- Dependencies: 1298
-- Name: PointSpot_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"PointSpot_id_seq"', 1, false);


--
-- TOC entry 1299 (class 1259 OID 42745)
-- Dependencies: 1648 1649 1650 5
-- Name: PointSpot; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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


--
-- TOC entry 1714 (class 0 OID 0)
-- Dependencies: 1299
-- Name: COLUMN "PointSpot"."SpownTime"; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN "PointSpot"."SpownTime" IS 'czas respowna';


--
-- TOC entry 1300 (class 1259 OID 42753)
-- Dependencies: 1651 1652 1653 1654 1655 1656 1657 1658 1659 1660 5
-- Name: characters; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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
    ch_last_pos_y integer DEFAULT 127,
    ch_guild_id integer
);


--
-- TOC entry 1715 (class 0 OID 0)
-- Dependencies: 1300
-- Name: TABLE characters; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE characters IS 'informacje na temat postaci ';


--
-- TOC entry 1716 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_name IS 'nawa postaci';


--
-- TOC entry 1717 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_class; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_class IS 'typ postaci';


--
-- TOC entry 1718 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_lvl; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_lvl IS 'lvl postaci';


--
-- TOC entry 1719 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_str; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_str IS 'str';


--
-- TOC entry 1720 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_agi; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_agi IS 'agi';


--
-- TOC entry 1721 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_vit; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_vit IS 'itality';


--
-- TOC entry 1722 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_enr; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_enr IS 'enr';


--
-- TOC entry 1723 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_stat_com; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_stat_com IS 'command
';


--
-- TOC entry 1724 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_last_pos_map; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_last_pos_map IS 'ostatnia mapa';


--
-- TOC entry 1725 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_last_pos_h; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_last_pos_h IS 'ostatni kierune patrzenia';


--
-- TOC entry 1726 (class 0 OID 0)
-- Dependencies: 1300
-- Name: COLUMN characters.ch_guild_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN characters.ch_guild_id IS 'the id to Guild table';


--
-- TOC entry 1301 (class 1259 OID 42765)
-- Dependencies: 1661 5
-- Name: chatacter_base_stats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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


--
-- TOC entry 1727 (class 0 OID 0)
-- Dependencies: 1301
-- Name: TABLE chatacter_base_stats; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE chatacter_base_stats IS 'definicje startowych bazowych statystyk postaci';


--
-- TOC entry 1728 (class 0 OID 0)
-- Dependencies: 1301
-- Name: COLUMN chatacter_base_stats.ch_base_class; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN chatacter_base_stats.ch_base_class IS 'klasa postaci';


--
-- TOC entry 1729 (class 0 OID 0)
-- Dependencies: 1301
-- Name: COLUMN chatacter_base_stats.ch_base_agi; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN chatacter_base_stats.ch_base_agi IS 'wartosc aglity';


--
-- TOC entry 1311 (class 1259 OID 59121)
-- Dependencies: 5
-- Name: guildNames; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "guildNames" (
    id integer NOT NULL,
    "guildName" text,
    "guildHolder" integer
);


--
-- TOC entry 1310 (class 1259 OID 59119)
-- Dependencies: 5 1311
-- Name: guildNames_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "guildNames_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1730 (class 0 OID 0)
-- Dependencies: 1310
-- Name: guildNames_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "guildNames_id_seq" OWNED BY "guildNames".id;


--
-- TOC entry 1731 (class 0 OID 0)
-- Dependencies: 1310
-- Name: guildNames_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"guildNames_id_seq"', 1, false);


--
-- TOC entry 1309 (class 1259 OID 59113)
-- Dependencies: 1668 5
-- Name: guildsMemembers; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE "guildsMemembers" (
    guild_memember_id integer NOT NULL,
    ch_id integer,
    "typeOf" integer DEFAULT 0,
    guild_id integer
);


--
-- TOC entry 1732 (class 0 OID 0)
-- Dependencies: 1309
-- Name: TABLE "guildsMemembers"; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE "guildsMemembers" IS 'memembers of guilds';


--
-- TOC entry 1308 (class 1259 OID 59111)
-- Dependencies: 1309 5
-- Name: guildsNames_guild_memember_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "guildsNames_guild_memember_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1733 (class 0 OID 0)
-- Dependencies: 1308
-- Name: guildsNames_guild_memember_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "guildsNames_guild_memember_id_seq" OWNED BY "guildsMemembers".guild_memember_id;


--
-- TOC entry 1734 (class 0 OID 0)
-- Dependencies: 1308
-- Name: guildsNames_guild_memember_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('"guildsNames_guild_memember_id_seq"', 1, false);


--
-- TOC entry 1302 (class 1259 OID 42771)
-- Dependencies: 5
-- Name: inwentories_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE inwentories_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1735 (class 0 OID 0)
-- Dependencies: 1302
-- Name: inwentories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('inwentories_id_seq', 5, true);


--
-- TOC entry 1303 (class 1259 OID 42773)
-- Dependencies: 1662 5
-- Name: inwentories; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE inwentories (
    ch_name character(10) NOT NULL,
    window_id integer,
    slot integer NOT NULL,
    item_hex character(5),
    id integer DEFAULT nextval('inwentories_id_seq'::regclass) NOT NULL
);


--
-- TOC entry 1736 (class 0 OID 0)
-- Dependencies: 1303
-- Name: COLUMN inwentories.ch_name; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN inwentories.ch_name IS 'klucz z czaacters tabeli';


--
-- TOC entry 1737 (class 0 OID 0)
-- Dependencies: 1303
-- Name: COLUMN inwentories.window_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN inwentories.window_id IS 'item w otpknie : 0 -inwentoru 1 store i';


--
-- TOC entry 1738 (class 0 OID 0)
-- Dependencies: 1303
-- Name: COLUMN inwentories.slot; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN inwentories.slot IS 'sot w dnaym oknie';


--
-- TOC entry 1739 (class 0 OID 0)
-- Dependencies: 1303
-- Name: COLUMN inwentories.item_hex; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN inwentories.item_hex IS 'hex itemu';


--
-- TOC entry 1304 (class 1259 OID 42776)
-- Dependencies: 5
-- Name: maps; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE maps (
    map_id integer NOT NULL,
    name text
);


--
-- TOC entry 1740 (class 0 OID 0)
-- Dependencies: 1304
-- Name: TABLE maps; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE maps IS 'mapy w mu';


--
-- TOC entry 1305 (class 1259 OID 42781)
-- Dependencies: 5
-- Name: monster_base_stats; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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


--
-- TOC entry 1741 (class 0 OID 0)
-- Dependencies: 1305
-- Name: TABLE monster_base_stats; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE monster_base_stats IS 'statystyki potworow wyciagniete z org servera';


--
-- TOC entry 1306 (class 1259 OID 42786)
-- Dependencies: 5
-- Name: users_u_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_u_id_seq
    INCREMENT BY 1
    NO MAXVALUE
    MINVALUE 0
    CACHE 1;


--
-- TOC entry 1742 (class 0 OID 0)
-- Dependencies: 1306
-- Name: users_u_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_u_id_seq', 1, true);


SET default_with_oids = true;

--
-- TOC entry 1307 (class 1259 OID 42788)
-- Dependencies: 1663 1664 1665 1666 5
-- Name: users; Type: TABLE; Schema: public; Owner: -; Tablespace: 
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
    u_date_to_blocked date,
    u_vol_code text
);


--
-- TOC entry 1743 (class 0 OID 0)
-- Dependencies: 1307
-- Name: TABLE users; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON TABLE users IS 'tabela zawiera informacje na temat userow w bd';


--
-- TOC entry 1744 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_user; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_user IS 'nazwa kata';


--
-- TOC entry 1745 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_pass; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_pass IS 'chaslo do kata';


--
-- TOC entry 1746 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_flag; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_flag IS 'flaga [czy polaczony cz nie]';


--
-- TOC entry 1747 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_ch_c; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_ch_c IS 'ilosc postaci w bd';


--
-- TOC entry 1748 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_id; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_id IS 'e';


--
-- TOC entry 1749 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_create_acc_date; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_create_acc_date IS 'data utozenie acc';


--
-- TOC entry 1750 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_last_login_date; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_last_login_date IS 'ostatnie zalogowanie';


--
-- TOC entry 1751 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_last_login_ip; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_last_login_ip IS 'ostatni ip logowania';


--
-- TOC entry 1752 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_fail_log_count; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_fail_log_count IS 'ilosc zlych wpisanych pass';


--
-- TOC entry 1753 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_date_to_blocked; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_date_to_blocked IS 'data odblokowania kata[jesli zablokowane]';


--
-- TOC entry 1754 (class 0 OID 0)
-- Dependencies: 1307
-- Name: COLUMN users.u_vol_code; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN users.u_vol_code IS 'code to del character ';


--
-- TOC entry 1669 (class 2604 OID 59123)
-- Dependencies: 1311 1310 1311
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE "guildNames" ALTER COLUMN id SET DEFAULT nextval('"guildNames_id_seq"'::regclass);


--
-- TOC entry 1667 (class 2604 OID 59115)
-- Dependencies: 1309 1308 1309
-- Name: guild_memember_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE "guildsMemembers" ALTER COLUMN guild_memember_id SET DEFAULT nextval('"guildsNames_guild_memember_id_seq"'::regclass);


--
-- TOC entry 1696 (class 0 OID 42732)
-- Dependencies: 1297
-- Data for Name: AreaSpot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1697 (class 0 OID 42745)
-- Dependencies: 1299
-- Data for Name: PointSpot; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1698 (class 0 OID 42753)
-- Dependencies: 1300
-- Data for Name: characters; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO characters (us_id, ch_name, ch_class, ch_stat_lvl, ch_stat_str, ch_stat_agi, ch_stat_vit, ch_stat_enr, ch_stat_com, ch_last_pos_map, ch_last_pos_h, ch_exp_act, ch_exp_lp, ch_last_pos_x, ch_last_pos_y, ch_guild_id) VALUES (0, 'adminDW   ', 0, 40, 15, 18, 15, 200, 0, '0', '1', NULL, 0, 170, 127, NULL);
INSERT INTO characters (us_id, ch_name, ch_class, ch_stat_lvl, ch_stat_str, ch_stat_agi, ch_stat_vit, ch_stat_enr, ch_stat_com, ch_last_pos_map, ch_last_pos_h, ch_exp_act, ch_exp_lp, ch_last_pos_x, ch_last_pos_y, ch_guild_id) VALUES (1, 'avamps4   ', 32, 40, 28, 20, 25, 200, 0, '0', '1', NULL, 0, 170, 128, NULL);
INSERT INTO characters (us_id, ch_name, ch_class, ch_stat_lvl, ch_stat_str, ch_stat_agi, ch_stat_vit, ch_stat_enr, ch_stat_com, ch_last_pos_map, ch_last_pos_h, ch_exp_act, ch_exp_lp, ch_last_pos_x, ch_last_pos_y, ch_guild_id) VALUES (1, 'vampDW    ', 0, 40, 15, 18, 15, 200, 0, '0', '1', NULL, 0, 170, 127, NULL);


--
-- TOC entry 1699 (class 0 OID 42765)
-- Dependencies: 1301
-- Data for Name: chatacter_base_stats; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (0, 18, 15, 15, 30, 0, NULL, 5, 'Dark Wizard');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (16, 18, 15, 15, 30, 0, NULL, 6, 'Soul Master');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (32, 20, 28, 25, 10, 0, NULL, 5, 'Dark Knight');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (48, 20, 28, 25, 10, 0, NULL, 6, 'Blade Knight');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (64, 25, 22, 20, 15, 0, NULL, 5, 'Fairy Elf');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (80, 25, 22, 20, 15, 0, NULL, 6, 'Muse Elf');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (96, 26, 26, 26, 26, 0, NULL, 7, 'Magic Gladiator');
INSERT INTO chatacter_base_stats (ch_base_class, ch_base_agi, ch_base_str, ch_base_vit, ch_base_enr, ch_base_com, ch_base_pos_map, ch_base_lp, ch_base_clasname) VALUES (128, 20, 26, 20, 15, 0, NULL, 7, 'Dark Lord');


--
-- TOC entry 1705 (class 0 OID 59121)
-- Dependencies: 1311
-- Data for Name: guildNames; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1704 (class 0 OID 59113)
-- Dependencies: 1309
-- Data for Name: guildsMemembers; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1700 (class 0 OID 42773)
-- Dependencies: 1303
-- Data for Name: inwentories; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1701 (class 0 OID 42776)
-- Dependencies: 1304
-- Data for Name: maps; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO maps (map_id, name) VALUES (0, 'Lorencia');
INSERT INTO maps (map_id, name) VALUES (1, 'Dungeon');
INSERT INTO maps (map_id, name) VALUES (2, 'Davias');
INSERT INTO maps (map_id, name) VALUES (3, 'Noria');
INSERT INTO maps (map_id, name) VALUES (4, 'LostTower');
INSERT INTO maps (map_id, name) VALUES (5, 'Exile');
INSERT INTO maps (map_id, name) VALUES (6, 'Atlans');
INSERT INTO maps (map_id, name) VALUES (8, 'Tarkan');
INSERT INTO maps (map_id, name) VALUES (9, 'Devil Square');
INSERT INTO maps (map_id, name) VALUES (10, 'Icarus');


--
-- TOC entry 1702 (class 0 OID 42781)
-- Dependencies: 1305
-- Data for Name: monster_base_stats; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1703 (class 0 OID 42788)
-- Dependencies: 1307
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (u_user, u_pass, u_flag, u_ch_c, u_id, u_create_acc_date, u_last_login_date, u_last_login_ip, u_fail_log_count, u_date_to_blocked, u_vol_code) VALUES ('admin1    ', 'admin1    ', 0, 1, 0, '2007-08-23', NULL, NULL, 0, NULL, NULL);
INSERT INTO users (u_user, u_pass, u_flag, u_ch_c, u_id, u_create_acc_date, u_last_login_date, u_last_login_ip, u_fail_log_count, u_date_to_blocked, u_vol_code) VALUES ('mikione   ', 'michalki1 ', 0, 3, 1, '2007-08-23', NULL, NULL, 0, NULL, NULL);


--
-- TOC entry 1692 (class 2606 OID 59128)
-- Dependencies: 1311 1311
-- Name: Guld_name_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "guildNames"
    ADD CONSTRAINT "Guld_name_id" PRIMARY KEY (id);


--
-- TOC entry 1675 (class 2606 OID 42800)
-- Dependencies: 1300 1300
-- Name: ch_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT ch_name UNIQUE (ch_name);


--
-- TOC entry 1755 (class 0 OID 0)
-- Dependencies: 1675
-- Name: CONSTRAINT ch_name ON characters; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON CONSTRAINT ch_name ON characters IS 'sprawdzanie czy postac ma unikalny nik';


--
-- TOC entry 1677 (class 2606 OID 42802)
-- Dependencies: 1300 1300
-- Name: ch_name_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT ch_name_pkey PRIMARY KEY (ch_name);


--
-- TOC entry 1756 (class 0 OID 0)
-- Dependencies: 1677
-- Name: CONSTRAINT ch_name_pkey ON characters; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON CONSTRAINT ch_name_pkey ON characters IS 'primery key';


--
-- TOC entry 1690 (class 2606 OID 59118)
-- Dependencies: 1309 1309
-- Name: guild_memember_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "guildsMemembers"
    ADD CONSTRAINT guild_memember_id PRIMARY KEY (guild_memember_id);


--
-- TOC entry 1671 (class 2606 OID 42804)
-- Dependencies: 1297 1297
-- Name: ids; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "AreaSpot"
    ADD CONSTRAINT ids PRIMARY KEY (id);


--
-- TOC entry 1680 (class 2606 OID 42806)
-- Dependencies: 1303 1303
-- Name: item_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY inwentories
    ADD CONSTRAINT item_id PRIMARY KEY (id);


--
-- TOC entry 1673 (class 2606 OID 42808)
-- Dependencies: 1299 1299
-- Name: key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY "PointSpot"
    ADD CONSTRAINT "key" PRIMARY KEY (id);


--
-- TOC entry 1682 (class 2606 OID 42810)
-- Dependencies: 1304 1304
-- Name: map_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY maps
    ADD CONSTRAINT map_id PRIMARY KEY (map_id);


--
-- TOC entry 1684 (class 2606 OID 42812)
-- Dependencies: 1305 1305
-- Name: mob_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY monster_base_stats
    ADD CONSTRAINT mob_id UNIQUE (monster_id);


--
-- TOC entry 1757 (class 0 OID 0)
-- Dependencies: 1684
-- Name: CONSTRAINT mob_id ON monster_base_stats; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON CONSTRAINT mob_id ON monster_base_stats IS 'kazdy mob ma unikalny swoj kod';


--
-- TOC entry 1686 (class 2606 OID 42814)
-- Dependencies: 1307 1307
-- Name: u_id; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT u_id PRIMARY KEY (u_id);


--
-- TOC entry 1688 (class 2606 OID 42816)
-- Dependencies: 1307 1307 1307 1307
-- Name: u_name; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT u_name UNIQUE (u_user, u_id, u_pass);


--
-- TOC entry 1678 (class 1259 OID 42817)
-- Dependencies: 1300
-- Name: fki_last_pos_map; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_last_pos_map ON characters USING btree (ch_last_pos_map);


--
-- TOC entry 1693 (class 2606 OID 42818)
-- Dependencies: 1681 1300 1304
-- Name: last_pos_map; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT last_pos_map FOREIGN KEY (ch_last_pos_map) REFERENCES maps(map_id);


--
-- TOC entry 1695 (class 2606 OID 42823)
-- Dependencies: 1303 1674 1300
-- Name: name_of_character; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY inwentories
    ADD CONSTRAINT name_of_character FOREIGN KEY (ch_name) REFERENCES characters(ch_name) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1694 (class 2606 OID 42828)
-- Dependencies: 1307 1685 1300
-- Name: us_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY characters
    ADD CONSTRAINT us_id FOREIGN KEY (us_id) REFERENCES users(u_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1711 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM root;
GRANT ALL ON SCHEMA public TO root;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-06-20 11:00:47

--
-- PostgreSQL database dump complete
--

