

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

drop table IF EXISTS sentence_word;

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

CREATE TABLE IF NOT EXISTS sentence_word (
			sw_id 			INTEGER PRIMARY KEY AUTOINCREMENT
			, sw_word 	TEXT
			, sw_idx    INTEGER
			, at_id		INTEGER
			, FOREIGN KEY(at_id) REFERENCES audiometry_track(at_id));


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

