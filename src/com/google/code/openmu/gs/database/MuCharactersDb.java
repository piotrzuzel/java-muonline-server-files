/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.gs.database;

public class MuCharactersDb {

	public static final String CH_AGI = "ch_stat_agi";
	public static final String CH_CLASS = "ch_class";
	public static final String CH_COM = "ch_stat_com";
	public static final String CH_ENR = "ch_stat_enr";

	// exp
	public static final String CH_EXP = "ch_exp_act";
	public static final String CH_LP = "ch_exp_lp";

	// statystyki
	public static final String CH_LVL = "ch_stat_lvl";
	public static final String CH_NAME = "ch_name";
	public static final String CH_POS_H = "ch_last_pos_h";
	public static final String CH_POS_M = "ch_last_pos_map";

	// pozycje
	public static final String CH_POS_X = "ch_last_pos_x";
	public static final String CH_POS_Y = "ch_last_pos_y";
	public static final String CH_STR = "ch_stat_str";
	public static final String CH_TAB = "characters";
	public static final String CH_VIT = "ch_stat_vit";
	public static final String US_ID = "us_id";
	public static final String CREATE = ""
			+ "- Table: characters "
			+ "-- DROP TABLE characters;"
			+ "CREATE TABLE characters"
			+ "("
			+ "us_id integer,"
			+ "  ch_name character(10) NOT NULL, -- nawa postaci"
			+ "  ch_class integer DEFAULT 0, -- typ postaci"
			+ "  ch_stat_lvl integer NOT NULL DEFAULT 1, -- lvl postaci"
			+ " "
			+ " ch_stat_str integer DEFAULT 0, -- str"
			+ " ch_stat_agi integer DEFAULT 0, -- agi"
			+ " ch_stat_vit integer DEFAULT 0, -- itality"
			+ " ch_stat_enr integer DEFAULT 0, -- enr"
			+ " "
			+ "ch_stat_com integer DEFAULT 0, -- command..."
			+ " ch_last_pos_map character(1), -- ostatnia mapa"
			+ " ch_last_pos_h character(1), -- ostatni kierune patrzenia"
			+ " ch_exp_act bigint,"
			+ " ch_exp_lp integer DEFAULT 0,"
			+ " ch_last_pos_x integer DEFAULT 170,"
			+ " ch_last_pos_y integer DEFAULT 127,"
			+ " CONSTRAINT ch_name_pkey PRIMARY KEY (ch_name), -- primery key"
			+ " CONSTRAINT last_pos_map FOREIGN KEY (ch_last_pos_map)"
			+ "     REFERENCES maps (map_id) MATCH SIMPLE"
			+ "     ON UPDATE NO ACTION ON DELETE NO ACTION,"
			+ " CONSTRAINT us_id FOREIGN KEY (us_id)"
			+ "     REFERENCES users (u_id) MATCH SIMPLE"
			+ "     ON UPDATE CASCADE ON DELETE CASCADE,"
			+ " CONSTRAINT ch_name UNIQUE (ch_name)"
			+ ") "
			+ "WITHOUT OIDS;"
			+ "ALTER TABLE characters OWNER TO root;"
			+ "COMMENT ON TABLE characters IS 'informacje na temat postaci ';"
			+ "{COMMENT ON COLUMN characters.ch_name IS 'nawa postaci';"
			+ "COMMENT ON COLUMN characters.ch_class IS 'typ postaci';"
			+ "COMMENT ON COLUMN characters.ch_stat_lvl IS 'lvl postaci';"
			+ "COMMENT ON COLUMN characters.ch_stat_str IS 'str';"
			+ "COMMENT ON COLUMN characters.ch_stat_agi IS 'agi';"
			+ "COMMENT ON COLUMN characters.ch_stat_vit IS 'itality';"
			+ "COMMENT ON COLUMN characters.ch_stat_enr IS 'enr';"
			+ "COMMENT ON COLUMN characters.ch_stat_com IS 'command"
			+ "';"
			+ "COMMENT ON COLUMN characters.ch_last_pos_map IS 'ostatnia mapa';"
			+ "COMMENT ON COLUMN characters.ch_last_pos_h IS 'ostatni kierune patrzenia';"
			+ "-- Index: fki_last_pos_map" + "-- DROP INDEX fki_last_pos_map;"
			+ "CREATE INDEX fki_last_pos_map" + "  ON characters"
			+ "  USING btree" + "  (ch_last_pos_map);";
	// uery
	// public static final String SQL_Char_List="select * from "+CH_TAB+
	// " where " +US_ID+"=";
}
// ~ Formatted by Jindent --- http://www.jindent.com

