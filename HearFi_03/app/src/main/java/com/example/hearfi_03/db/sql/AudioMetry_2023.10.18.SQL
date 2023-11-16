
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
			, tu_dbHL		INTEGER
			, at_id         INTEGER
			, FOREIGN KEY(ts_id) REFERENCES hrtest_set(ts_id)
			, FOREIGN KEY(at_id) REFERENCES audiometry_track(at_id));

CREATE TABLE IF NOT EXISTS sentence_word (
			sw_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, sw_word 	TEXT
			, sw_idx    INTEGER
			, at_id		INTEGER
			, FOREIGN KEY(at_id) REFERENCES audiometry_track(at_id));


CREATE TABLE IF NOT EXISTS pt_track (
			pt_id INTEGER PRIMARY KEY AUTOINCREMENT
			, pt_file_name 	TEXT
			, pt_file_ext 	TEXT
			, frequency		INTEGER
			, dBHL	        INTEGER
			, phone			TEXT
			, device 		TEXT);

CREATE TABLE IF NOT EXISTS ptt_test_unit (
			tu_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, ts_id			INTEGER
			, tu_hz	        INTEGER
			, tu_dBHL		INTEGER
			, FOREIGN KEY(ts_id) REFERENCES hrtest_set(ts_id)
			);


INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_001','mp3','sl_a1','백화점에 가서 목걸이와 반지를 샀습니다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_002','mp3','sl_a1','휴지를 버려 주세요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_003','mp3','sl_a1','우체국은 병원 앞에 있어요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_004','mp3','sl_a1','약을 하루에 두 번씩 드세요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_005','mp3','sl_a1','감기에 걸리지 않도록 조심해라');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_006','mp3','sl_a1','내가 퇴근하는 시간은 항상 같다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_007','mp3','sl_a1','물이 차다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_008','mp3','sl_a1','오늘처럼 눈이 오는 날은 조심해서 운전해야 한다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_009','mp3','sl_a1','당신이 만든 작품을 설명해 보세요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a1_010','mp3','sl_a1','당근은 무슨 색입니까');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_001','mp3','sl_a2','커피를 마셔서 잠이 오지 않는다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_002','mp3','sl_a2','여기서 기다리세요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_003','mp3','sl_a2','전화번호가 어떻게 됩니까');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_004','mp3','sl_a2','오래된 음식은 먹지 마세요');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_005','mp3','sl_a2','눈이 내리는 겨울이 빨리 왔으면 좋겠습니다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_006','mp3','sl_a2','아침에 안개가 껴서 앞을 보기가 힘들었다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_007','mp3','sl_a2','수박이 달다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_008','mp3','sl_a2','일을 마치고 갈 시간이 충분합니다');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_009','mp3','sl_a2','내가 읽은 책을 너한테 빌려줄게');
INSERT INTO audiometry_track (at_file_name, at_file_ext,at_type,at_content) VALUES ('ks_sl_a2_010','mp3','sl_a2','음악을 들으며 운동해 보세요');

INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_001', 'mp3', 'bwl_a1', '편지');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_002', 'mp3', 'bwl_a1', '달걀');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_003', 'mp3', 'bwl_a1', '시간');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_004', 'mp3', 'bwl_a1', '육군');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_005', 'mp3', 'bwl_a1', '신발');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_006', 'mp3', 'bwl_a1', '땅콩');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_007', 'mp3', 'bwl_a1', '안개');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_008', 'mp3', 'bwl_a1', '마음');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_009', 'mp3', 'bwl_a1', '허리');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_010', 'mp3', 'bwl_a1', '욕심');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_011', 'mp3', 'bwl_a1', '노래');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a1_012', 'mp3', 'bwl_a1', '저녁');
--COMMIT;

INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_001', 'mp3', 'bwl_a2', '사람');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_002', 'mp3', 'bwl_a2', '토끼');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_003', 'mp3', 'bwl_a2', '병원');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_004', 'mp3', 'bwl_a2', '등대');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_005', 'mp3', 'bwl_a2', '논밭');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_006', 'mp3', 'bwl_a2', '과일');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_007', 'mp3', 'bwl_a2', '송곳');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_008', 'mp3', 'bwl_a2', '딸기');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_009', 'mp3', 'bwl_a2', '문제');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_010', 'mp3', 'bwl_a2', '나무');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_011', 'mp3', 'bwl_a2', '극장');
INSERT INTO audiometry_track (at_file_name, at_file_ext, at_type, at_content) VALUES ('ks_bwl_a2_012', 'mp3', 'bwl_a2', '가위');
--COMMIT;

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



INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (1,0,'백화점');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (1,1,'가서');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (1,2,'목걸이');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (1,3,'반지');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (1,4,'샀습니다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (2,0,'휴지를');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (2,1,'버려');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (2,2,'주세요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (3,0,'우체국');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (3,1,'병원');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (3,2,'앞');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (3,3,'있어요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (4,0,'약');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (4,1,'하루');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (4,2,'두');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (4,3,'번');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (4,4,'드세요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (5,0,'감기');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (5,1,'걸리지');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (5,2,'않도록');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (5,3,'조심해라');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (6,1,'퇴근');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (6,2,'시간');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (6,3,'항상');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (6,4,'같다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (7,0,'물');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (7,1,'차다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (8,1,'눈');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (8,2,'오는');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (8,3,'날');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (8,4,'조심');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (8,5,'운전해야');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (9,0,'당신');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (9,1,'만든');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (9,2,'작품');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (9,3,'설명');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (9,4,'보세요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (10,0,'당근');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (10,1,'무슨');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (10,2,'색');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (11,0,'커피');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (11,1,'마셔서');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (11,2,'잠');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (11,3,'오지');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (11,4,'않는다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (12,0,'여기');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (12,1,'기다리세요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (13,0,'전화번호');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (13,1,'어떻게');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (13,2,'됩니까');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (14,0,'오래된');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (14,1,'음식은');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (14,2,'먹지');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (14,3,'마세요');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (15,0,'눈');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (15,1,'내리는');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (15,2,'겨울');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (15,4,'왔으면');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (15,5,'좋겠습니다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (16,0,'아침');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (16,1,'안개');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (16,3,'앞');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (16,4,'보기');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (16,5,'힘들었다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (17,0,'수박');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (17,1,'달다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (18,0,'일');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (18,1,'마치고');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (18,2,'갈');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (18,3,'시간');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (18,4,'충분합니다');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (19,0,'내');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (19,1,'읽은');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (19,2,'책');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (19,3,'너');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (19,4,'빌려줄게');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (20,0,'음악');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (20,1,'들으며');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (20,2,'운동');
INSERT INTO SENTENCE_WORD (at_id, sw_idx, sw_word) VALUES (20,3,'보세요');

INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_00_','mp3',  250,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_05_','mp3',  250,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_10_','mp3',  250,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_15_','mp3',  250,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_20_','mp3',  250,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_25_','mp3',  250,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_30_','mp3',  250,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_35_','mp3',  250,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_40_','mp3',  250,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_45_','mp3',  250,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_50_','mp3',  250,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_55_','mp3',  250,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_60_','mp3',  250,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_65_','mp3',  250,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_70_','mp3',  250,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_75_','mp3',  250,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_250_80_','mp3',  250,00, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_00_','mp3',  500,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_05_','mp3',  500,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_10_','mp3',  500,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_15_','mp3',  500,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_20_','mp3',  500,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_25_','mp3',  500,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_30_','mp3',  500,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_35_','mp3',  500,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_40_','mp3',  500,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_45_','mp3',  500,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_50_','mp3',  500,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_55_','mp3',  500,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_60_','mp3',  500,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_65_','mp3',  500,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_70_','mp3',  500,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_75_','mp3',  500,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ( 'pt_500_80_','mp3',  500,00, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_00_','mp3', 1000,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_05_','mp3', 1000,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_10_','mp3', 1000,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_15_','mp3', 1000,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_20_','mp3', 1000,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_25_','mp3', 1000,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_30_','mp3', 1000,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_35_','mp3', 1000,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_40_','mp3', 1000,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_45_','mp3', 1000,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_50_','mp3', 1000,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_55_','mp3', 1000,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_60_','mp3', 1000,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_65_','mp3', 1000,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_70_','mp3', 1000,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_75_','mp3', 1000,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_1000_80_','mp3', 1000,00, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_00_','mp3', 2000,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_05_','mp3', 2000,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_10_','mp3', 2000,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_15_','mp3', 2000,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_20_','mp3', 2000,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_25_','mp3', 2000,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_30_','mp3', 2000,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_35_','mp3', 2000,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_40_','mp3', 2000,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_45_','mp3', 2000,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_50_','mp3', 2000,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_55_','mp3', 2000,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_60_','mp3', 2000,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_65_','mp3', 2000,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_70_','mp3', 2000,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_75_','mp3', 2000,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_2000_80_','mp3', 2000,00, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_00_','mp3', 4000,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_05_','mp3', 4000,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_10_','mp3', 4000,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_15_','mp3', 4000,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_20_','mp3', 4000,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_25_','mp3', 4000,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_30_','mp3', 4000,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_35_','mp3', 4000,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_40_','mp3', 4000,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_45_','mp3', 4000,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_50_','mp3', 4000,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_55_','mp3', 4000,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_60_','mp3', 4000,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_65_','mp3', 4000,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_70_','mp3', 4000,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_75_','mp3', 4000,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_4000_80_','mp3', 4000,00, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_00_','mp3', 8000,80, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_05_','mp3', 8000,75, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_10_','mp3', 8000,70, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_15_','mp3', 8000,65, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_20_','mp3', 8000,60, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_25_','mp3', 8000,55, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_30_','mp3', 8000,50, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_35_','mp3', 8000,45, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_40_','mp3', 8000,40, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_45_','mp3', 8000,35, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_50_','mp3', 8000,30, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_55_','mp3', 8000,25, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_60_','mp3', 8000,20, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_65_','mp3', 8000,15, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_70_','mp3', 8000,10, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_75_','mp3', 8000,05, 'A24', 'W800BT');
INSERT INTO pt_track (pt_file_name,pt_file_ext,frequency,dBHL, phone, device) VALUES ('pt_8000_80_','mp3', 8000,00, 'A24', 'W800BT');

--commit;



INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,   0,  'pure_tone_0250hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,   5,  'pure_tone_0250hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  10,  'pure_tone_0250hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  15,  'pure_tone_0250hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  20,  'pure_tone_0250hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  25,  'pure_tone_0250hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  30,  'pure_tone_0250hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  35,  'pure_tone_0250hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  40,  'pure_tone_0250hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  45,  'pure_tone_0250hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  50,  'pure_tone_0250hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  55,  'pure_tone_0250hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  60,  'pure_tone_0250hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  65,  'pure_tone_0250hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  70,  'pure_tone_0250hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  75,  'pure_tone_0250hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  80,  'pure_tone_0250hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  85,  'pure_tone_0250hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  90,  'pure_tone_0250hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250,  95,  'pure_tone_0250hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  250, 100,  'pure_tone_0250hz_00db','mp3');


INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,   0,  'pure_tone_0500hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,   5,  'pure_tone_0500hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  10,  'pure_tone_0500hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  15,  'pure_tone_0500hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  20,  'pure_tone_0500hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  25,  'pure_tone_0500hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  30,  'pure_tone_0500hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  35,  'pure_tone_0500hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  40,  'pure_tone_0500hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  45,  'pure_tone_0500hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  50,  'pure_tone_0500hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  55,  'pure_tone_0500hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  60,  'pure_tone_0500hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  65,  'pure_tone_0500hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  70,  'pure_tone_0500hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  75,  'pure_tone_0500hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  80,  'pure_tone_0500hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  85,  'pure_tone_0500hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  90,  'pure_tone_0500hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500,  95,  'pure_tone_0500hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2',  500, 100,  'pure_tone_0500hz_00db','mp3');


INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,   0,  'pure_tone_1000hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,   5,  'pure_tone_1000hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  10,  'pure_tone_1000hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  15,  'pure_tone_1000hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  20,  'pure_tone_1000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  25,  'pure_tone_1000hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  30,  'pure_tone_1000hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  35,  'pure_tone_1000hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  40,  'pure_tone_1000hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  45,  'pure_tone_1000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  50,  'pure_tone_1000hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  55,  'pure_tone_1000hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  60,  'pure_tone_1000hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  65,  'pure_tone_1000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  70,  'pure_tone_1000hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  75,  'pure_tone_1000hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  80,  'pure_tone_1000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  85,  'pure_tone_1000hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  90,  'pure_tone_1000hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000,  95,  'pure_tone_1000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 1000, 100,  'pure_tone_1000hz_00db','mp3');



INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,   0,  'pure_tone_2000hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,   5,  'pure_tone_2000hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  10,  'pure_tone_2000hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  15,  'pure_tone_2000hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  20,  'pure_tone_2000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  25,  'pure_tone_2000hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  30,  'pure_tone_2000hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  35,  'pure_tone_2000hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  40,  'pure_tone_2000hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  45,  'pure_tone_2000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  50,  'pure_tone_2000hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  55,  'pure_tone_2000hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  60,  'pure_tone_2000hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  65,  'pure_tone_2000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  70,  'pure_tone_2000hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  75,  'pure_tone_2000hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  80,  'pure_tone_2000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  85,  'pure_tone_2000hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  90,  'pure_tone_2000hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000,  95,  'pure_tone_2000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 2000, 100,  'pure_tone_2000hz_00db','mp3');


INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,   0,  'pure_tone_4000hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,   5,  'pure_tone_4000hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  10,  'pure_tone_4000hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  15,  'pure_tone_4000hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  20,  'pure_tone_4000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  25,  'pure_tone_4000hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  30,  'pure_tone_4000hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  35,  'pure_tone_4000hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  40,  'pure_tone_4000hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  45,  'pure_tone_4000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  50,  'pure_tone_4000hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  55,  'pure_tone_4000hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  60,  'pure_tone_4000hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  65,  'pure_tone_4000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  70,  'pure_tone_4000hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  75,  'pure_tone_4000hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  80,  'pure_tone_4000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  85,  'pure_tone_4000hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  90,  'pure_tone_4000hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000,  95,  'pure_tone_4000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 4000, 100,  'pure_tone_4000hz_00db','mp3');


INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,   0,  'pure_tone_8000hz_80db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,   5,  'pure_tone_8000hz_76db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  10,  'pure_tone_8000hz_72db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  15,  'pure_tone_8000hz_68db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  20,  'pure_tone_8000hz_64db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  25,  'pure_tone_8000hz_60db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  30,  'pure_tone_8000hz_56db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  35,  'pure_tone_8000hz_52db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  40,  'pure_tone_8000hz_48db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  45,  'pure_tone_8000hz_44db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  50,  'pure_tone_8000hz_40db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  55,  'pure_tone_8000hz_36db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  60,  'pure_tone_8000hz_32db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  65,  'pure_tone_8000hz_28db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  70,  'pure_tone_8000hz_24db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  75,  'pure_tone_8000hz_20db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  80,  'pure_tone_8000hz_16db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  85,  'pure_tone_8000hz_12db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  90,  'pure_tone_8000hz_08db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000,  95,  'pure_tone_8000hz_04db','mp3');
INSERT INTO pt_track (phone, device, frequency, dBHL, pt_file_name, pt_file_ext ) VALUES ('A24', 'BUDS2', 8000, 100,  'pure_tone_8000hz_00db','mp3');



INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('1', '01012345678', '010-1234-5678', '111111', 'aaa', 'male', '11-11-11');
INSERT INTO account (acc_id, acc_phone_id, acc_phone, acc_pwd, acc_name, acc_gender, acc_birth) VALUES ('2', '01034567890', '010-3456-7890', '820101', '홍길동', '여', '82-01-01');


INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('1', '2023-10-19 23:48:56', 'PTA', '경도난청', '1');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('2', '2023-10-19 23:49:49', 'PTA', '심도난청', '1');
INSERT INTO hrtest_group (tg_id, tg_date, tg_type, tg_result, acc_id) VALUES ('3', '2023-10-19 23:50:56', 'WRS', '매우 저조', '1');
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

INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('1', '5', '약', '약', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('2', '5', '능', '능', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('3', '5', '논', '논', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('4', '5', '상', '상', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('5', '5', '귀', '귀', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('6', '5', '옷', '옷', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('7', '5', '병', '병', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('8', '5', '잎', '잎', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('9', '5', '시', '시', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('10', '5', '꿈', '꿈', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('11', '6', '자', '자', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('12', '6', '덕', '덕', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('13', '6', '시', '기', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('14', '6', '죄', '죄', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('15', '6', '만', '만', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('16', '6', '댁', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('17', '6', '논', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('18', '6', '밀', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('19', '6', '무', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('20', '6', '소', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('21', '9', '알', '말', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('22', '9', '상', '상', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('23', '9', '김', '김', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('24', '9', '약', '약', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('25', '9', '옷', '옷', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('26', '9', '구', '구', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('27', '9', '조', '조', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('28', '9', '댁', '댁', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('29', '9', '네', '네', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('30', '9', '녹', '녹', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('31', '10', '삼', '사', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('32', '10', '추', '사', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('33', '10', '샘', '갬', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('34', '10', '군', '군', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('35', '10', '귀', '귀', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('36', '10', '죄', '저ㅣ', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('37', '10', '밀', '밀', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('38', '10', '도', '도', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('39', '10', '무', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('40', '10', '벌', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('41', '11', '알', '말', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('42', '11', '상', '상', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('43', '11', '김', '김', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('44', '11', '약', '약', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('45', '11', '옷', '옷', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('46', '11', '구', '구', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('47', '11', '조', '조', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('48', '11', '댁', '댁', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('49', '11', '네', '네', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('50', '11', '녹', '녹', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('51', '12', '삼', '사', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('52', '12', '추', '사', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('53', '12', '샘', '갬', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('54', '12', '군', '군', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('55', '12', '귀', '귀', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('56', '12', '죄', '저ㅣ', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('57', '12', '밀', '밀', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('58', '12', '도', '도', '1', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('59', '12', '무', '', '0', '50', '0');
INSERT INTO hrtest_unit (tu_id, ts_id, tu_question, tu_answer, tu_iscorrect, tu_dbHL, at_id) VALUES ('60', '12', '벌', '', '0', '50', '0');


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




