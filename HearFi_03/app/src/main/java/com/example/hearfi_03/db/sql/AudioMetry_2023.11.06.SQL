

drop table IF EXISTS ptt_test_device;
drop table IF EXISTS ptt_test_unit;
drop table IF EXISTS pt_track;
drop table IF EXISTS sentence_word;
drop table IF EXISTS audiometry_unit;
drop table IF EXISTS audiometry_set;

drop table IF EXISTS hrtest_unit;
drop table IF EXISTS hrtest_set;
drop table IF EXISTS hrtest_group;

drop table IF EXISTS sentence_unit;
drop table IF EXISTS audiometry_track;

drop table IF EXISTS account;

CREATE TABLE IF NOT EXISTS account (
			acc_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, acc_phone_id 	TEXT UNIQUE NOT NULL
			, acc_phone 	TEXT
			, acc_pwd		TEXT
			, acc_name		TEXT
			, acc_gender	TEXT
			, acc_birth		TEXT );

CREATE TABLE IF NOT EXISTS hrtest_group (
			tg_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, tg_date 		TEXT
			, tg_type		TEXT
			, tg_result		TEXT
			, acc_id		INTEGER
			, FOREIGN KEY(acc_id) REFERENCES account(acc_id) );

CREATE TABLE IF NOT EXISTS hrtest_set (
			ts_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, tg_id 		INTEGER
			, ts_side		TEXT
			, ts_result		TEXT
			, ts_comment	TEXT
			, FOREIGN KEY(tg_id) REFERENCES hrtest_group(tg_id) );

CREATE TABLE IF NOT EXISTS audiometry_track (
			at_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, at_file_name 	TEXT
			, at_file_ext 	TEXT
			, at_type		TEXT
			, at_content	TEXT );

CREATE TABLE IF NOT EXISTS hrtest_unit (
			tu_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, ts_id			INTEGER
			, tu_question 	TEXT
			, tu_answer		TEXT
			, tu_iscorrect	INTEGER
			, FOREIGN KEY(ts_id) REFERENCES hrtest_set(ts_id)
			);

CREATE TABLE IF NOT EXISTS pt_track (
			pt_id INTEGER PRIMARY KEY AUTOINCREMENT
			, pt_file_name 	TEXT
			, pt_file_ext 	TEXT
			, frequency		INTEGER
			, dBHL	        INTEGER
			, phone			TEXT
			, device 		TEXT );

CREATE TABLE IF NOT EXISTS ptt_test_unit (
			tu_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, ts_id			INTEGER
			, tu_hz	        INTEGER
			, tu_dBHL		INTEGER
			, FOREIGN KEY(ts_id) REFERENCES hrtest_set(ts_id)
			);

CREATE TABLE IF NOT EXISTS ptt_test_device (
			td_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, tg_id 		INTEGER
			, td_phone			TEXT
			, td_device 		TEXT
			, FOREIGN KEY(tg_id) REFERENCES hrtest_group(tg_id) );



INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_001', 'mp3', 'mwl_a1', '귀');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_002', 'mp3', 'mwl_a1', '남');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_003', 'mp3', 'mwl_a1', '해');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_004', 'mp3', 'mwl_a1', '밀');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_005', 'mp3', 'mwl_a1', '옷');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_006', 'mp3', 'mwl_a1', '잔');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_007', 'mp3', 'mwl_a1', '댁');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_008', 'mp3', 'mwl_a1', '겁');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_009', 'mp3', 'mwl_a1', '시');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_010', 'mp3', 'mwl_a1', '병');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_011', 'mp3', 'mwl_a1', '소');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_012', 'mp3', 'mwl_a1', '점');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_013', 'mp3', 'mwl_a1', '키');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_014', 'mp3', 'mwl_a1', '앞');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_015', 'mp3', 'mwl_a1', '무');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_016', 'mp3', 'mwl_a1', '논');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_017', 'mp3', 'mwl_a1', '자');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_018', 'mp3', 'mwl_a1', '글');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_019', 'mp3', 'mwl_a1', '용');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_020', 'mp3', 'mwl_a1', '겉');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_021', 'mp3', 'mwl_a1', '다');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_022', 'mp3', 'mwl_a1', '뜰');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_023', 'mp3', 'mwl_a1', '피');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_024', 'mp3', 'mwl_a1', '상');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_025', 'mp3', 'mwl_a1', '네');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_026', 'mp3', 'mwl_a1', '벌');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_027', 'mp3', 'mwl_a1', '추');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_028', 'mp3', 'mwl_a1', '만');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_029', 'mp3', 'mwl_a1', '죄');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_030', 'mp3', 'mwl_a1', '일');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_031', 'mp3', 'mwl_a1', '구');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_032', 'mp3', 'mwl_a1', '삼');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_033', 'mp3', 'mwl_a1', '도');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_034', 'mp3', 'mwl_a1', '알');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_035', 'mp3', 'mwl_a1', '꽃');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_036', 'mp3', 'mwl_a1', '연');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_037', 'mp3', 'mwl_a1', '달');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_038', 'mp3', 'mwl_a1', '혀');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_039', 'mp3', 'mwl_a1', '녹');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_040', 'mp3', 'mwl_a1', '김');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_041', 'mp3', 'mwl_a1', '약');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_042', 'mp3', 'mwl_a1', '덕');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_043', 'mp3', 'mwl_a1', '조');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_044', 'mp3', 'mwl_a1', '군');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_045', 'mp3', 'mwl_a1', '잎');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_046', 'mp3', 'mwl_a1', '폐');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_047', 'mp3', 'mwl_a1', '꿈');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_048', 'mp3', 'mwl_a1', '터');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_049', 'mp3', 'mwl_a1', '샘');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_mwl_a1_050', 'mp3', 'mwl_a1', '능');



INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,   0,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,   5,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  10,  'shin_pure_tone_0250hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  15,  'shin_pure_tone_0250hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  20,  'shin_pure_tone_0250hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  25,  'pure_tone_0250hz_76db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  30,  'pure_tone_0250hz_71db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  35,  'pure_tone_0250hz_66db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  40,  'pure_tone_0250hz_61db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  45,  'pure_tone_0250hz_55db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  50,  'pure_tone_0250hz_49db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  55,  'pure_tone_0250hz_42db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  60,  'pure_tone_0250hz_36db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  65,  'pure_tone_0250hz_31db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  70,  'pure_tone_0250hz_26db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  75,  'pure_tone_0250hz_20db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  80,  'pure_tone_0250hz_15db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  85,  'pure_tone_0250hz_10db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  90,  'pure_tone_0250hz_05db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  95,  'pure_tone_0250hz_00db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250, 100,  'pure_tone_0250hz_00db', 		  'mp3');
																													   
																													   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,   0,  'pure_tone_mute_1000hz',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,   5,  'pure_tone_mute_1000hz',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  10,  'shin_pure_tone_0500hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  15,  'shin_pure_tone_0500hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  20,  'shin_pure_tone_0500hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  25,  'shin_pure_tone_0500hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  30,  'pure_tone_0500hz_76db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  35,  'pure_tone_0500hz_72db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  40,  'pure_tone_0500hz_68db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  45,  'pure_tone_0500hz_64db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  50,  'pure_tone_0500hz_60db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  55,  'pure_tone_0500hz_55db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  60,  'pure_tone_0500hz_51db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  65,  'pure_tone_0500hz_46db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  70,  'pure_tone_0500hz_41db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  75,  'pure_tone_0500hz_35db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  80,  'pure_tone_0500hz_30db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  85,  'pure_tone_0500hz_25db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  90,  'pure_tone_0500hz_20db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  95,  'pure_tone_0500hz_15db',  	  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500, 100,  'pure_tone_0500hz_15db',  	  'mp3');
																													   
																													   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,   0,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,   5,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  10,  'shin_pure_tone_1000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  15,  'shin_pure_tone_1000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  20,  'shin_pure_tone_1000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  25,  'shin_pure_tone_1000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  30,  'pure_tone_1000hz_78db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  35,  'pure_tone_1000hz_77db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  40,  'pure_tone_1000hz_75db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  45,  'pure_tone_1000hz_70db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  50,  'pure_tone_1000hz_64db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  55,  'pure_tone_1000hz_59db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  60,  'pure_tone_1000hz_53db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  65,  'pure_tone_1000hz_48db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  70,  'pure_tone_1000hz_43db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  75,  'pure_tone_1000hz_37db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  80,  'pure_tone_1000hz_32db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  85,  'pure_tone_1000hz_27db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  90,  'pure_tone_1000hz_22db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  95,  'pure_tone_1000hz_17db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000, 100,  'pure_tone_1000hz_17db', 		  'mp3');
																													   
																													   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,   0,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,   5,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  10,  'shin_pure_tone_2000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  15,  'shin_pure_tone_2000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  20,  'shin_pure_tone_2000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  25,  'shin_pure_tone_2000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  30,  'pure_tone_2000hz_76db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  35,  'pure_tone_2000hz_71db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  40,  'pure_tone_2000hz_67db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  45,  'pure_tone_2000hz_62db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  50,  'pure_tone_2000hz_57db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  55,  'pure_tone_2000hz_52db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  60,  'pure_tone_2000hz_47db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  65,  'pure_tone_2000hz_42db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  70,  'pure_tone_2000hz_37db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  75,  'pure_tone_2000hz_32db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  80,  'pure_tone_2000hz_27db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  85,  'pure_tone_2000hz_22db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  90,  'pure_tone_2000hz_17db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  95,  'pure_tone_2000hz_12db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000, 100,  'pure_tone_2000hz_12db', 		  'mp3');
																													   
																													   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,   0,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,   5,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  10,  'shin_pure_tone_4000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  15,  'shin_pure_tone_4000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  20,  'shin_pure_tone_4000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  25,  'shin_pure_tone_4000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  30,  'pure_tone_4000hz_75db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  35,  'pure_tone_4000hz_70db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  40,  'pure_tone_4000hz_65db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  45,  'pure_tone_4000hz_61db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  50,  'pure_tone_4000hz_57db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  55,  'pure_tone_4000hz_52db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  60,  'pure_tone_4000hz_48db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  65,  'pure_tone_4000hz_42db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  70,  'pure_tone_4000hz_37db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  75,  'pure_tone_4000hz_31db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  80,  'pure_tone_4000hz_25db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  85,  'pure_tone_4000hz_20db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  90,  'pure_tone_4000hz_15db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  95,  'pure_tone_4000hz_10db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000, 100,  'pure_tone_4000hz_10db', 		  'mp3');
																													    
																													   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,   0,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,   5,  'pure_tone_mute_1000hz', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  10,  'shin_pure_tone_8000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  15,  'shin_pure_tone_8000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  20,  'shin_pure_tone_8000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  25,  'pure_tone_8000hz_80db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  30,  'pure_tone_8000hz_77db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  35,  'pure_tone_8000hz_74db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  40,  'pure_tone_8000hz_71db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  45,  'pure_tone_8000hz_66db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  50,  'pure_tone_8000hz_61db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  55,  'pure_tone_8000hz_56db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  60,  'pure_tone_8000hz_51db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  65,  'pure_tone_8000hz_45db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  70,  'pure_tone_8000hz_40db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  75,  'pure_tone_8000hz_34db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  80,  'pure_tone_8000hz_28db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  85,  'pure_tone_8000hz_23db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  90,  'pure_tone_8000hz_18db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  95,  'pure_tone_8000hz_13db', 		  'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000, 100,  'pure_tone_8000hz_13db', 		  'mp3');



INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  10,  'shin_pure_tone_0250hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  15,  'shin_pure_tone_0250hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  20,  'shin_pure_tone_0250hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  25,  'pure_tone_0250hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  30,  'pure_tone_0250hz_75db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  35,  'pure_tone_0250hz_70db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  40,  'pure_tone_0250hz_65db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  45,  'pure_tone_0250hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  50,  'pure_tone_0250hz_55db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  55,  'pure_tone_0250hz_50db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  60,  'pure_tone_0250hz_45db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  65,  'pure_tone_0250hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  70,  'pure_tone_0250hz_35db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  75,  'pure_tone_0250hz_29db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  80,  'pure_tone_0250hz_22db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  85,  'pure_tone_0250hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  90,  'pure_tone_0250hz_10db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250,  95,  'pure_tone_0250hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  250, 100,  'pure_tone_0250hz_04db','mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  10,  'shin_pure_tone_0500hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  15,  'shin_pure_tone_0500hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  20,  'shin_pure_tone_0500hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  25,  'pure_tone_0500hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  30,  'pure_tone_0500hz_75db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  35,  'pure_tone_0500hz_70db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  40,  'pure_tone_0500hz_65db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  45,  'pure_tone_0500hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  50,  'pure_tone_0500hz_55db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  55,  'pure_tone_0500hz_50db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  60,  'pure_tone_0500hz_45db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  65,  'pure_tone_0500hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  70,  'pure_tone_0500hz_35db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  75,  'pure_tone_0500hz_29db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  80,  'pure_tone_0500hz_22db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  85,  'pure_tone_0500hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  90,  'pure_tone_0500hz_10db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500,  95,  'pure_tone_0500hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3',  500, 100,  'pure_tone_0500hz_04db','mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  10,  'shin_pure_tone_1000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  15,  'shin_pure_tone_1000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  20,  'shin_pure_tone_1000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  25,  'pure_tone_1000hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  30,  'pure_tone_1000hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  35,  'pure_tone_1000hz_71db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  40,  'pure_tone_1000hz_66db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  45,  'pure_tone_1000hz_61db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  50,  'pure_tone_1000hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  55,  'pure_tone_1000hz_51db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  60,  'pure_tone_1000hz_46db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  65,  'pure_tone_1000hz_41db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  70,  'pure_tone_1000hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  75,  'pure_tone_1000hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  80,  'pure_tone_1000hz_27db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  85,  'pure_tone_1000hz_21db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  90,  'pure_tone_1000hz_14db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000,  95,  'pure_tone_1000hz_09db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 1000, 100,  'pure_tone_1000hz_09db','mp3');
																														   
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  10,  'shin_pure_tone_2000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  15,  'shin_pure_tone_2000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  20,  'shin_pure_tone_2000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  25,  'pure_tone_2000hz_79db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  30,  'pure_tone_2000hz_74db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  35,  'pure_tone_2000hz_69db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  40,  'pure_tone_2000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  45,  'pure_tone_2000hz_59db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  50,  'pure_tone_2000hz_54db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  55,  'pure_tone_2000hz_49db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  60,  'pure_tone_2000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  65,  'pure_tone_2000hz_39db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  70,  'pure_tone_2000hz_34db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  75,  'pure_tone_2000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  80,  'pure_tone_2000hz_22db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  85,  'pure_tone_2000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  90,  'pure_tone_2000hz_10db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000,  95,  'pure_tone_2000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 2000, 100,  'pure_tone_2000hz_04db','mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  10,  'shin_pure_tone_4000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  15,  'shin_pure_tone_4000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  20,  'shin_pure_tone_4000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  25,  'pure_tone_4000hz_79db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  30,  'pure_tone_4000hz_74db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  35,  'pure_tone_4000hz_69db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  40,  'pure_tone_4000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  45,  'pure_tone_4000hz_59db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  50,  'pure_tone_4000hz_54db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  55,  'pure_tone_4000hz_49db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  60,  'pure_tone_4000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  65,  'pure_tone_4000hz_39db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  70,  'pure_tone_4000hz_34db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  75,  'pure_tone_4000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  80,  'pure_tone_4000hz_22db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  85,  'pure_tone_4000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  90,  'pure_tone_4000hz_10db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000,  95,  'pure_tone_4000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 4000, 100,  'pure_tone_4000hz_04db','mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,   0,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,   5,  'pure_tone_mute_1000hz','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  10,  'shin_pure_tone_8000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  15,  'shin_pure_tone_8000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  20,  'shin_pure_tone_8000hz_20dbhl','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  25,  'pure_tone_8000hz_79db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  30,  'pure_tone_8000hz_74db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  35,  'pure_tone_8000hz_69db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  40,  'pure_tone_8000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  45,  'pure_tone_8000hz_59db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  50,  'pure_tone_8000hz_54db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  55,  'pure_tone_8000hz_49db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  60,  'pure_tone_8000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  65,  'pure_tone_8000hz_39db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  70,  'pure_tone_8000hz_34db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  75,  'pure_tone_8000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  80,  'pure_tone_8000hz_22db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  85,  'pure_tone_8000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  90,  'pure_tone_8000hz_10db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000,  95,  'pure_tone_8000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'MOMENTUM3', 8000, 100,  'pure_tone_8000hz_04db','mp3');


INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,   0,  'shin_pure_tone_0250hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,   5,  'shin_pure_tone_0250hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  10,  'shin_pure_tone_0250hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  15,  'pure_tone_0250hz_79db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  20,  'pure_tone_0250hz_72db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  25,  'pure_tone_0250hz_67db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  30,  'pure_tone_0250hz_62db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  35,  'pure_tone_0250hz_57db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  40,  'pure_tone_0250hz_52db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  45,  'pure_tone_0250hz_47db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  50,  'pure_tone_0250hz_42db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  55,  'pure_tone_0250hz_37db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  60,  'pure_tone_0250hz_32db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  65,  'pure_tone_0250hz_30db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  70,  'pure_tone_0250hz_28db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  75,  'pure_tone_0250hz_26db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  80,  'pure_tone_0250hz_23db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  85,  'pure_tone_0250hz_18db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  90,  'pure_tone_0250hz_13db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250,  95,  'pure_tone_0250hz_08db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  250, 100,  'pure_tone_0250hz_08db', 'mp3');
																						
																						
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,   0,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,   5,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  10,  'shin_pure_tone_0500hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  15,  'shin_pure_tone_0500hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  20,  'shin_pure_tone_0500hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  25,  'pure_tone_0500hz_79db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  30,  'pure_tone_0500hz_72db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  35,  'pure_tone_0500hz_67db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  40,  'pure_tone_0500hz_62db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  45,  'pure_tone_0500hz_57db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  50,  'pure_tone_0500hz_52db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  55,  'pure_tone_0500hz_47db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  60,  'pure_tone_0500hz_42db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  65,  'pure_tone_0500hz_37db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  70,  'pure_tone_0500hz_32db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  75,  'pure_tone_0500hz_30db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  80,  'pure_tone_0500hz_28db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  85,  'pure_tone_0500hz_26db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  90,  'pure_tone_0500hz_23db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500,  95,  'pure_tone_0500hz_18db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT',  500, 100,  'pure_tone_0500hz_18db', 'mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,   0,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,   5,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  10,  'shin_pure_tone_1000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  15,  'shin_pure_tone_1000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  20,  'shin_pure_tone_1000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  25,  'pure_tone_1000hz_76db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  30,  'pure_tone_1000hz_71db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  35,  'pure_tone_1000hz_66db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  40,  'pure_tone_1000hz_61db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  45,  'pure_tone_1000hz_56db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  50,  'pure_tone_1000hz_51db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  55,  'pure_tone_1000hz_46db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  60,  'pure_tone_1000hz_41db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  65,  'pure_tone_1000hz_36db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  70,  'pure_tone_1000hz_31db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  75,  'pure_tone_1000hz_27db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  80,  'pure_tone_1000hz_22db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  85,  'pure_tone_1000hz_18db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  90,  'pure_tone_1000hz_13db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000,  95,  'pure_tone_1000hz_09db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 1000, 100,  'pure_tone_1000hz_09db', 'mp3');
																														   
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,   0,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,   5,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  10,  'shin_pure_tone_2000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  15,  'shin_pure_tone_2000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  20,  'shin_pure_tone_2000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  25,  'pure_tone_2000hz_74db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  30,  'pure_tone_2000hz_69db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  35,  'pure_tone_2000hz_64db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  40,  'pure_tone_2000hz_59db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  45,  'pure_tone_2000hz_54db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  50,  'pure_tone_2000hz_49db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  55,  'pure_tone_2000hz_44db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  60,  'pure_tone_2000hz_39db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  65,  'pure_tone_2000hz_34db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  70,  'pure_tone_2000hz_29db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  75,  'pure_tone_2000hz_24db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  80,  'pure_tone_2000hz_20db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  85,  'pure_tone_2000hz_15db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  90,  'pure_tone_2000hz_10db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000,  95,  'pure_tone_2000hz_06db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 2000, 100,  'pure_tone_2000hz_06db', 'mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,   0,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,   5,  'pure_tone_mute_1000hz', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  10,  'shin_pure_tone_4000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  15,  'shin_pure_tone_4000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  20,  'shin_pure_tone_4000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  25,  'pure_tone_4000hz_74db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  30,  'pure_tone_4000hz_69db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  35,  'pure_tone_4000hz_64db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  40,  'pure_tone_4000hz_59db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  45,  'pure_tone_4000hz_54db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  50,  'pure_tone_4000hz_49db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  55,  'pure_tone_4000hz_44db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  60,  'pure_tone_4000hz_39db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  65,  'pure_tone_4000hz_34db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  70,  'pure_tone_4000hz_29db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  75,  'pure_tone_4000hz_24db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  80,  'pure_tone_4000hz_20db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  85,  'pure_tone_4000hz_15db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  90,  'pure_tone_4000hz_10db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000,  95,  'pure_tone_4000hz_06db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 4000, 100,  'pure_tone_4000hz_06db', 'mp3');
																														   
																														   
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,   0,  'shin_pure_tone_8000hz_15dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,   5,  'shin_pure_tone_8000hz_20dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  10,  'shin_pure_tone_8000hz_25dbhl', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  15,  'pure_tone_8000hz_79db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  20,  'pure_tone_8000hz_74db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  25,  'pure_tone_8000hz_69db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  30,  'pure_tone_8000hz_64db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  35,  'pure_tone_8000hz_59db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  40,  'pure_tone_8000hz_54db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  45,  'pure_tone_8000hz_49db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  50,  'pure_tone_8000hz_44db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  55,  'pure_tone_8000hz_39db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  60,  'pure_tone_8000hz_34db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  65,  'pure_tone_8000hz_29db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  70,  'pure_tone_8000hz_24db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  75,  'pure_tone_8000hz_20db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  80,  'pure_tone_8000hz_15db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  85,  'pure_tone_8000hz_10db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  90,  'pure_tone_8000hz_06db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000,  95,  'pure_tone_8000hz_06db', 'mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'W800BT', 8000, 100,  'pure_tone_8000hz_06db', 'mp3');


INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('1', '12345678901', '123-4567-8901', '123456', '관리자', '남', '12-34-56');
INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('2', '01034567890', '010-5678-1234', '010101', '성춘향', '여', '01-01-01');
INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('3', '01012345678', '010-1234-5678', '541212', '홍길동', '남', '54-12-12');


INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('1', '2023-10-19 23:48:56', 'PTA', '경도난청', '3');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('2', '2023-10-19 23:49:49', 'PTA', '심도난청', '3');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('3', '2023-10-19 23:50:56', 'WRS', '매우 저조', '3');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('4', '2023-10-19 23:53:12', 'PTA', '중도난청', '2');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('5', '2023-10-19 23:54:38', 'WRS', '매우 저조', '2');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('6', '2023-10-19 23:54:59', 'WRS', '매우 저조', '2');

INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('1', '1', 'LEFT', '21', '21 dB HL (경도난청)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('2', '1', 'RIGHT', '18', '18 dB HL (정상)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('3', '2', 'LEFT', '100', '100 dB HL (심도난청)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('4', '2', 'RIGHT', '53', '53 dB HL (중도난청)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('5', '3', 'LEFT', '100', '100 % (매우 우수)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('6', '3', 'RIGHT', '40', '40 % (매우 저조)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('7', '4', 'LEFT', '25', '25 dB HL (경도난청)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('8', '4', 'RIGHT', '50', '50 dB HL (중도난청)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('9', '5', 'LEFT', '90', '90 % (우수)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('10', '5', 'RIGHT', '40', '40 % (매우 저조)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('11', '6', 'LEFT', '90', '90 % (우수)');
INSERT INTO hrtest_set (ts_id, tg_id, ts_side, ts_result, ts_comment) VALUES ('12', '6', 'RIGHT', '40', '40 % (매우 저조)');

INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '1', '5', '약', '약', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '2', '5', '능', '능', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '3', '5', '논', '논', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '4', '5', '상', '상', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '5', '5', '귀', '귀', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '6', '5', '옷', '옷', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '7', '5', '병', '병', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '8', '5', '잎', '잎', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ( '9', '5', '시', '시', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('10', '5', '꿈', '꿈', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('11', '6', '자', '자', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('12', '6', '덕', '덕', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('13', '6', '시', '기', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('14', '6', '죄', '죄', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('15', '6', '만', '만', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('16', '6', '댁',  '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('17', '6', '논',  '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('18', '6', '밀',  '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('19', '6', '무',  '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('20', '6', '소',  '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('21', '9', '알', '말', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('22', '9', '상', '상', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('23', '9', '김', '김', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('24', '9', '약', '약', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('25', '9', '옷', '옷', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('26', '9', '구', '구', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('27', '9', '조', '조', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('28', '9', '댁', '댁', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('29', '9', '네', '네', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('30', '9', '녹', '녹', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('31', '10', '삼', '사', '0' );
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('32', '10', '추', '사', '0' );
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('33', '10', '샘', '갬', '0' );
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('34', '10', '군', '군', '1' );
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('35', '10', '귀', '귀', '1' );
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('36', '10', '죄', '저ㅣ', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('37', '10', '밀', '밀', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('38', '10', '도', '도', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('39', '10', '무', '',  '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('40', '10', '벌', '',  '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('41', '11', '알', '말', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('42', '11', '상', '상', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('43', '11', '김', '김', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('44', '11', '약', '약', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('45', '11', '옷', '옷', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('46', '11', '구', '구', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('47', '11', '조', '조', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('48', '11', '댁', '댁', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('49', '11', '네', '네', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('50', '11', '녹', '녹', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('51', '12', '삼', '사', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('52', '12', '추', '사', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('53', '12', '샘', '갬', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('54', '12', '군', '군', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('55', '12', '귀', '귀', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('56', '12', '죄', '저ㅣ', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('57', '12', '밀', '밀', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('58', '12', '도', '도', '1');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('59', '12', '무', '', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect) VALUES ('60', '12', '벌', '', '0');


INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('1', '1', '250', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('2', '1', '500', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('3', '1', '1000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('4', '1', '2000', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('5', '1', '4000', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('6', '1', '8000', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('7', '2', '250', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('8', '2', '500', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('9', '2', '1000', '25');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('10', '2', '2000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('11', '2', '4000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('12', '2', '8000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('13', '3', '250', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('14', '3', '500', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('15', '3', '1000', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('16', '3', '2000', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('17', '3', '4000', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('18', '3', '8000', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('19', '4', '250', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('20', '4', '500', '100');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('21', '4', '1000', '60');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('22', '4', '2000', '60');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('23', '4', '4000', '0');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('24', '4', '8000', '0');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('25', '7', '250', '35');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('26', '7', '500', '40');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('27', '7', '1000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('28', '7', '2000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('29', '7', '4000', '15');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('30', '7', '8000', '30');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('31', '8', '250', '35');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('32', '8', '500', '55');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('33', '8', '1000', '55');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('34', '8', '2000', '45');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('35', '8', '4000', '55');
INSERT INTO ptt_test_unit (tu_id, ts_id, tu_hz, tu_dBHL) VALUES ('36', '8', '8000', '55');


/*
select * from hrtest_group;

select * from hrtest_group g, hrtest_set s ;

select * from hrtest_group g, hrtest_set s 
where g.tg_id = s.tg_id;

select * from hrtest_group g, hrtest_set s , hrtest_unit u
where g.tg_id = s.tg_id
and s.ts_id = u.ts_id;


select * from hrtest_group g, hrtest_set s , ptt_test_unit u
where g.tg_id = s.tg_id
and g.tg_type = 'PTA';

select * from hrtest_group g, hrtest_set s , ptt_test_unit u, ptt_test_device d
where g.tg_id = s.tg_id
and d.tg_id = g.tg_id
and s.ts_id = u.ts_id
and g.tg_type = 'PTA';


select * from hrtest_group g, hrtest_set s , ptt_test_unit u, ptt_test_device d, account a
where g.tg_id = s.tg_id
and g.acc_id = a.acc_id
and d.tg_id = g.tg_id
and s.ts_id = u.ts_id
and g.tg_type = 'PTA';

*/

