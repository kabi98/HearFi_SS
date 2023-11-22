

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


INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('1', '12345678901', '123-4567-8901', '123456', '관리자', '남', '12-34-56');
INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('2', '01034567890', '010-5678-1234', '010101', '성춘향', '여', '01-01-01');
INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('3', '01012345678', '010-1234-5678', '541212', '홍길동', '남', '54-12-12');





/*

*/

