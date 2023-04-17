-- 建立資料庫
DROP DATABASE IF EXISTS boardgame;
CREATE DATABASE boardgame;

-- 切換到boardgame資料庫
USE boardgame;

-- 新增MEMBERS表格
DROP TABLE IF EXISTS MEMBERS;
CREATE TABLE MEMBERS
(
    MEM_NO	       INT NOT NULL AUTO_INCREMENT ,
    MEM_NAME	   VARCHAR(10) NOT NULL,
    MEM_GENDER	   TINYINT NOT NULL,
    MEM_EMAIL	   VARCHAR(30) NOT NULL UNIQUE,
    MEM_PASSWORD   CHAR(100) NOT NULL,
    MEM_PHONE	   VARCHAR(10) NOT NULL,
    MEM_ADDRESS	   VARCHAR(100) NOT NULL,
    MEM_BIRTHDAY   DATE,
    COUPON	       INT DEFAULT 0,
    RESERVE_AUTH   TINYINT DEFAULT 1,
    MEM_VIO	       VARCHAR(512) DEFAULT 0,
    MEM_STATUS	   DATE,
    PRIMARY KEY(MEM_NO)
);
-- 新增MEMBERS資料
INSERT INTO MEMBERS VALUES
('1', '張三', '1', 'zhangsan@gmail.com', '123456', '0912345678', '台北市中山區南京東路', '1990/1/1', '500', '1', '0', '2023/3/15'),
('2', '李四', '1', 'lisi@yahoo.com',  '654321', '0912345679', '台北市大安區信義路', '1995/3/20', '1000', '1', '1', '2023/3/11'),
('3', '王五', '1', 'wangwu@hotmail.com',  'password123', '0912345680', '台北市中正區羅斯福路', '1992/12/18', '200', '1', '2', '2023/3/12'),
('4', '陳美麗', '0', 'meilichen@yahoo.com.tw',  'abcd1234', '0912345681', '新北市板橋區中山路', '1993/7/2', '800', '1', '0', '2023/3/13'),
('5', '林志玲', '0', 'linzhiling@gmail.com',  'pass1234', '0912345682', '台北市士林區中正路', '1974/11/29', '1200', '1', '0', '2023/3/14'),
('6', '張曼玉', '0', 'zhangmanyu@hotmail.com', 'qwerasdf', '0912345683', '台北市大同區民權西路', '1963/10/23', '300', '1', '1', '2023/3/10'),
('7', '張麗珠', '0', 'misszhang@gmail.com',  'qazwsx', '0977888999', '新竹市東區中華路30號', '1999/7/7', '200', '0', '0', '2023/3/6'),
('8', '陳偉銘', '1', 'mrchen@yahoo.com.tw',  'asdfgh', '0988777666', '高雄市鳳山區中山路80號', '1985/9/15', '1500', '1', '1', '2023/3/2'),
('9', '林宣宣', '0', 'misslin@hotmail.com', 'zxcvbn', '0966666777', '台南市北區成功路10號', '1992/2/28', '300', '0', '0', '2023/2/26'),
('10', '吳柏毅', '1', 'mrwu@gmail.com',  'qwerty', '0933444555', '桃園市中壢區龍岡路50號', '1998/11/11', '100', '0', '3', '2023/2/22');

-- 新增MEM_PERMISSION表格
DROP TABLE IF EXISTS MEM_PERMISSION;
CREATE TABLE MEM_PERMISSION
(
	PERMISSIONS_NO INT NOT NULL AUTO_INCREMENT,
    DURATION INT,
    LOWER_LIMIT INT,
    UPPER_LIMIT INT,
    PRIMARY KEY(PERMISSIONS_NO)
);

-- 新增PRODUCT表格
DROP TABLE IF EXISTS PRODUCT;
CREATE TABLE PRODUCT (
    PD_NO 			INT NOT NULL AUTO_INCREMENT,
    PD_NAME			VARCHAR(15) NOT NULL,
    PD_PRICE 		INT UNSIGNED NOT NULL,
    PD_STOCK 		INT,
    PD_DESCRIPTION 	TEXT,
    PD_STATUS 		TINYINT DEFAULT 0,
    PD_UPDATE 		DATETIME,
    PRIMARY KEY(PD_NO)
);
-- 新增PRODUCT資料
INSERT INTO PRODUCT VALUES
(1,'雙胞胎',1050, 5,'每回合檯面上都會擺出11張圖片卡，每位玩家都要將這11張卡祕密地兩兩配對。配對完成之後，所有人揭露自己的配對結果，如果有人配對與其他人相同便得分。最後剩下的卡片跟別人相同則會獲得更高的分數。遊戲進行四輪，四輪後分數最高的玩家獲勝！', 0,'2023-03-09 10:15:30'),
(2, '矮人礦坑', 590, 3, '在遊戲中玩家們將會扮演矮人族，其中一種矮人是淘金老人，他們開拓礦坑坑道向山脈深處尋找寶藏；另一種則是試著在淘金矮人的路上放置障礙的搗蛋鬼。', 1, '2023-03-09 10:19:03'),
(3, '農家樂', 1400, 5, '農家樂(Agricola)是很經典的策略桌遊。卡牌和配件非常多，文字量也非常大，並可以搭配組合出各種策略玩法，所以屬於中型到重型策略。基礎資源有10種，職業卡和發展卡各是超過一百張，每張的效果都不同，並且可以組合加成效果。遊戲流程其實蠻簡單的，複雜的地方只有卡牌和資源而已，通常要玩第二次以上才能想出自己的策略，第一次玩只能熟悉一下卡牌和資源要怎麼用。遊戲中玩家扮演17世紀的農莊主人，要發展自己的田地，可以扮演各種職業(169種)，使用各種道具(139種)來種田、飼養動物、獲得食物和資源、擴展房舍等等。並購入更好的生產工具，增加家庭成員來做更多工作。遊戲最後會根據資源去計分，以均勻發展農莊為加分導向。雖然遊戲的資訊量很大，但整體遊戲設計都很流暢，也很直覺，因為就是大家都知道的農莊生活，所以玩起來很順，玩第二次時就可以感受到遊戲的樂趣，不過依然是非常燒腦，因為熟悉玩法後就會開始想各種策略組合。農家樂的擺放機制算是很純的策略遊戲，同時又加入了抽牌機制，降低策略程度，提高遊戲性和耐玩度，因此成為很經典的策略桌遊。', 0, '2023-03-09 11:05:10'),
(4, '電力公司', 1280, 8, '「電力公司(Power Grid)」遊戲，又名「發電廠」，為2004年由Friedemann Friese所設計的深度策略遊戲，並獲得2004年國際遊戲最佳策略遊戲獎項入圍及2005年德國年度遊戲冠軍評審團獎項推薦。遊戲中玩家支配著一間電力公司，利用有限的資金，努力拓展自己的事業。遊戲進行時，玩家們不僅要競標電廠汰舊換新，還得囤積原料避免斷電，更要鞏固自家區域構築輸電網路。當有玩家連接的城市達到規定條件時，遊戲即告結束。玩家們統計自家供電城市的數量，供給電力最多城市的玩家即為遊戲贏家。', 1, '2023-03-09 11:15:45'),
(5, '卡卡頌', 1160, 4, '卡卡頌是位於法國南部的一座美麗古城，也是中古時代教會對抗異教徒的重鎮 ，這座城堡牢不可破的防禦工事，不論是在當時或今天都堪稱一絕！', 0, '2023-03-26 16:15:55'),
(6, '砰BANG', 830, 6, '歹徒追殺警長，警長追捕歹徒，而叛徒則躲在暗處，伺機從雙方的對立中獲利。但誰是無法無天的歹徒？誰又想將他斃於槍下？要找出這些答案你必需拔出你的槍!', 1, '2023-04-20 10:05:10'),
(7, '一夜終極狼人', 690, 3, '一夜終極狼人是一個可供3-10位玩家角色扮演的快速陣營遊戲，每位玩家扮演以下角色：狡猾的狼人、調皮的搗蛋鬼、樂於助人的預言家或12位不同角色中的一個。', 0, '2023-07-15 13:15:00'),
(8,'13道線索',1050, 5,'你有福爾摩斯的鷹眼嗎？適合2-6位、8歲以上的玩家一起角逐最佳偵探的榮耀！數起兇殘的犯罪案件震驚了1899年的倫敦，謎樣的案情掩蓋了真相，蘇格蘭警場在黑暗中摸索，號召一群優秀的偵探前來協助破案。每位偵探必須利用敏銳的直覺，從13道線索中找出蛛絲馬跡，負責解開自己的謎題，比其他人更快偵破自己的案件！', 1,'2023-10-03 15:45:40');


-- 新增PRODUCT_PIC表格
DROP TABLE IF EXISTS PRODUCT_PIC;
CREATE TABLE PRODUCT_PIC
(
    PIC_NO	INT NOT NULL AUTO_INCREMENT,
    PD_NO	INT NOT NULL,
    PD_PIC	LONGBLOB,
    PRIMARY KEY(PIC_NO)
);
-- 新增PRODUCT_PIC資料
INSERT INTO PRODUCT_PIC VALUES
(1,1,NULL),
(2,1,NULL),
(3,2,NULL),
(4,2,NULL),
(5,2,NULL),
(6,3,NULL),
(7,4,NULL),
(8,5,NULL),
(9,5,NULL),
(10,5,NULL),
(11,6,NULL),
(12,6,NULL),
(13,6,NULL),
(14,7,NULL),
(15,7,NULL),
(16,8,NULL),
(17,8,NULL),
(18,8,NULL);

-- 新增PRODUCT_DISCUSS表格
DROP TABLE IF EXISTS PRODUCT_DISCUSS;
CREATE TABLE PRODUCT_DISCUSS
(
    DIS_NO			INT NOT NULL AUTO_INCREMENT,
    PD_NO			INT NOT NULL,
    MEM_NO			INT NOT NULL,
    DISCUSS_DESC 	TEXT,
    DESC_TIME 		DATETIME,
    PRIMARY KEY(DIS_NO)
);
-- 新增PRODUCT_DISCUSS資料
INSERT INTO PRODUCT_DISCUSS VALUES
(1,1,1,'我很喜歡桌遊雙胞胎這個商品，因為它有趣又刺激。玩起來有種緊張刺激的感覺，但又不至於太過複雜，讓我和朋友們可以輕鬆上手。遊戲規則很清晰明瞭，每一回合的操作也很容易理解。另外，遊戲中的角色設計很有趣，每個角色都有不同的能力和策略，增加了遊戲的多樣性和趣味性。整體而言，這是一個非常有趣的桌遊，我會推薦給其他人一起來體驗。','2023-05-14 14:09:27'),
(2,1,3,'桌遊雙胞胎，整體感覺普普通通，沒有特別讓我驚艷的地方，但也沒有讓我失望。遊戲內容是兩款不同風格的遊戲結合在一起，雖然有點複雜，但規則說明寫得很清楚易懂，學起來也不難。遊戲流程也蠻順暢的，讓人不會有太多卡關的感覺。唯一美中不足的是，我們玩了好幾次都沒有發現有什麼特殊組合或策略可以玩得更好。總體來說，如果想找一款普通卻沒有失望的桌遊，桌遊雙胞胎是可以考慮的選擇。','2023-05-20 19:45:38'),
(7,5,6,'我非常喜歡卡卡頌，簡單易懂的規則和有趣的競爭讓我每次都有不同的挑戰和樂趣。','2023-10-18 11:04:22'),
(8,5,8,'卡卡頌的玩法相對簡單，但趣味性和策略性還是值得一試。是一款不錯的選擇。','2023-10-18 20:56:11');

-- 新增COLLECTION表格
DROP TABLE IF EXISTS COLLECTION;
CREATE TABLE COLLECTION
(
    MEM_NO	INT NOT NULL,
    PD_NO	INT NOT NULL,
    CONSTRAINT PK_MEM_COLLECTION PRIMARY KEY(MEM_NO,PD_NO)
);
-- 新增COLLECTION資料
INSERT INTO COLLECTION VALUES
('1', '2'),
('1', '6'),
('1', '1'),
('2', '3'),
('2', '2'),
('5', '2'),
('5', '8');

-- 新增ORDERS表格
DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS (
    ORD_NO				INT NOT NULL AUTO_INCREMENT ,
    MEM_NO				INT NOT NULL,
    USE_COUPON			INT UNSIGNED,
    ORD_FEE				INT,
    ORD_STATUS			TINYINT,
    ORD_CREATE			DATETIME NOT NULL,
    ORD_PICK			TINYINT,
    ORD_PAY_STATUS		TINYINT,
    ORD_FINISH			DATETIME,
    TOTAL_AMOUNT		INT,
    ACTUAL_AMOUNT		INT,
    RECIPIENT			VARCHAR(40),
    RECIPIENT_ADDRES	VARCHAR(200),
    RECIPIENT_PH		VARCHAR(20),
    PRIMARY KEY(ORD_NO)
);
-- 新增ORDERS資料
INSERT INTO ORDERS VALUES
('1', '3', '100', '50', '2', '2023-03-01', '1', '1', NULL, '4900', '4850', '劉大一', '新竹市東區力行五路27號', '0954654321'),
('2', '2', '200', '100', '0', '2023-03-02', '2', '0', NULL, '2320', '2220', '陳曉明', '彰化縣彰化市民生路24號', '0985231113'),
('3', '1', '0', '0', '5', '2023-03-03', '0', '1', '2023-03-13', '1280', '1280', '張三', '台北市中山區南京東路', '0912345678'),
('4', '5', '300', '50', '4', '2023-03-04', '1', '1', NULL, '1870', '1620', '李詩詩', '新北市泰山區信華三街7號', '0927654321'),
('5', '6', '0', '100', '3', '2023-03-05', '2', '1', NULL, '1980', '2080', '張曼玉', '台北市大同區民權西路', '0912345683'),
('6', '7', '200', '50', '5', '2023-03-06', '1', '1', '2023-03-15', '4310', '4160', '趙曉華', '苗栗縣頭份市光明街31號', '0987324221'),
('7', '6', '0', '100', '6', '2023-03-07', '2', '0', '2023-03-16', '1160', '1260', '張曼玉', '台北市大同區民權西路', '0912345683'),
('8', '5', '400', '0', '5', '2023-03-08', '0', '1', '2023-03-17', '2800', '2400', '林志玲', '台北市士林區中正路', '0912345682'),
('9', '10', '300', '50', '1', '2023-03-09', '1', '1', NULL, '1400', '1150', '吳銘賜', '高雄市大樹區中興南路31號', '0987224321'),
('10', '9', '500', '100', '0', '2023-03-10', '2', '0', NULL, '1740', '1340', '鄭姍姍', '屏東縣崁頂鄉平和南路35號', '0933654321');

-- 新增ORDERS表格
DROP TABLE IF EXISTS ORDERLIST;
CREATE TABLE ORDERLIST
(
    ORD_NO	INT NOT NULL,
    PD_NO	INT NOT NULL,
    QTY		INT NOT NULL,
    PRICE 	INT NOT NULL,
    CONSTRAINT PK_MEM_ORDERLIST PRIMARY KEY(ORD_NO, PD_NO)
);
-- 新增ORDERLIST資料
INSERT INTO ORDERLIST(ORD_NO, PD_NO, QTY, PRICE ) VALUES
('1', '1', '2', '1050'),
('1', '3', '2', '1400'),
('2', '5', '2', '1160'),
('3', '4', '1', '1280'),
('4', '2', '1', '590'),
('4', '4', '1', '1280'),
('5', '8', '2', '990'),
('6', '7', '2', '690'),
('6', '5', '1', '1160'),
('6', '2', '3', '590'),
('7', '6', '2', '830'),
('8', '3', '2', '1400'),
('9', '3', '1', '1400'),
('10', '7', '1', '690'),
('10', '1', '1', '1050');

-- 新增PRODUCT_AD表格
DROP TABLE IF EXISTS PRODUCT_AD;
CREATE TABLE PRODUCT_AD
(
    AD_NO	INT NOT NULL AUTO_INCREMENT,
    PD_NO	INT,
    UPDATE_DATE	DATE,
    PRIMARY KEY (AD_NO)
);
-- 新增PRODUCT_AD資料
INSERT INTO PRODUCT_AD VALUES
(1, 2, '2023/3/10'),
(2, 3, '2023/3/10'),
(3, 5, '2023/3/10'),
(4, 6, '2023/3/10'),
(5, 7, '2023/3/10');

-- SEATS_STATUS - redis
-- 新增RESERVATION_PRICE表格
DROP TABLE IF EXISTS RESERVATION_PRICE;
CREATE TABLE RESERVATION_PRICE (
    RESERVATION_PRICE_NO 	INT NOT NULL AUTO_INCREMENT,
    RESERVATION_PRICE 		INT UNSIGNED,
    TIME_START 				DATE,
    TIME_END 				DATE,
    PRIMARY KEY (RESERVATION_PRICE_NO)
);
-- 新增RESERVATION_PRICE資料
INSERT INTO RESERVATION_PRICE VALUES
(1, 100, '2020-01-01', '2023-03-01'),
(2, 200, '2023-03-01', '2050-12-31');

-- 新增OPENNING_TIME表格
DROP TABLE IF EXISTS OPENNING_TIME;
CREATE TABLE OPENNING_TIME (
    OPENTIME_NO 	INT NOT NULL AUTO_INCREMENT,
    WEEK 			TINYINT,
    OPENTIME_START 	TIME,
    OPENTIME_END 	TIME,
    PRIMARY KEY (OPENTIME_NO)
);
-- 新增OPENNING_TIME資料
INSERT INTO OPENNING_TIME VALUES
(1, 1, '11:00:00', '21:00:00'),
(2, 2, '11:00:00', '21:00:00'),
(3, 3, '11:00:00', '21:00:00'),
(4, 4, '11:00:00', '21:00:00'),
(5, 5, '11:00:00', '21:00:00'),
(6, 6, '09:00:00', '23:00:00'),
(7, 7, '09:00:00', '23:00:00');


-- 新增EVENT表格
DROP TABLE IF EXISTS EVENT;
CREATE TABLE EVENT
(
    EVENT_NO	INT PRIMARY KEY AUTO_INCREMENT,
    EVENT_NAME	VARCHAR(15) NOT NULL,
    EVENT_DISC	TEXT,
    EVENT_DATE	DATE,
    EVENT_STARTTIME	TIMESTAMP,
    EVENT_ENDTIME	TIMESTAMP,
    EVENT_LIMIT	INT,
    SIGNUP_NUM	INT,
    EVENT_FEE	INT UNSIGNED,
    EVENT_WINNER1	VARCHAR(10),
    EVENT_WINNER2	VARCHAR(10),
    EVENT_WINNER3	VARCHAR(10),
    EVENT_STATUS	TINYINT,
    SIGNUP_START_TIME	TIMESTAMP,
    SIGNUP_DEADLINE	 TIMESTAMP,
    EVENT_PIC	         LONGBLOB
);


-- 第一筆資料(已完賽)
INSERT INTO boardgame.event (
 EVENT_NAME,
 EVENT_DISC,
 EVENT_DATE,
 EVENT_STARTTIME,
 EVENT_ENDTIME,
 EVENT_LIMIT,
 SIGNUP_NUM,
 EVENT_FEE,
 EVENT_WINNER1	,
 EVENT_WINNER2	,
 EVENT_WINNER3	,
 EVENT_STATUS,
 SIGNUP_START_TIME,
 SIGNUP_DEADLINE
)
VALUES (
'風聲',
'在《風聲》桌遊中，將分為兩大對立的陣營（潛伏戰線、軍情處），大部分玩家都會扮演忠於某個陣營的的間諜角色，其餘玩家則會扮演無辜捲入間諜生活的路人。（打醬油）

遊戲開始時，每位玩家手上都會分發一定數量的手牌，這些手牌就是你手裡掌握的情報。每位玩家每回合開始時，會補充兩張手牌，然後就要想辦法將有利於自身陣營的情報傳遞到同伴那裡。其他扮演對立陣營的間諜玩家，則會想方設法阻撓你那些情報的傳遞。

整個遊戲過程中，會穿插著許許多多虛假的情報，情報收與不收，是否能捕捉其他玩家不經意間透露的"蛛絲馬跡"，憑藉自己的推理和判斷，步步為營，忍辱負重，找出暗藏的敵人與同伴，將是對玩家判斷能力與邏輯推理能力最為關鍵的考驗。作為間諜的您，如果收集到的假情報超過一定數量時，將會被宣判收取假情報太多而死亡。反之，如果您收集到一定數量屬於您自己陣營的情報時，那您同您的盟友們就可以獲得遊戲的勝利啦！

是“神一樣的對手，還是豬一樣的隊友？”趕快來體驗一下《風聲》桌遊帶給您的無限樂趣吧！！',
'2023-04-05',
'2023-04-05 11:00:00',
'2023-04-05 21:30:00',
'60',
'55',
'400',
'馬櫻韭',
'蔡瑛雯',
'陳衰貶',
'1',
'2023-03-15 09:30:00',
'2023-03-20 23:30:00'
);

-- 第二筆資料(未完賽)
INSERT INTO boardgame.event (
 EVENT_NAME	,
 EVENT_DISC	,
 EVENT_DATE	,
 EVENT_STARTTIME,
 EVENT_ENDTIME	,
 EVENT_LIMIT	,
 SIGNUP_NUM	,
 EVENT_FEE	,
 EVENT_STATUS	,
 SIGNUP_START_TIME,
 SIGNUP_DEADLINE
)
VALUES (
'獵巫鎮',
'在1692年馬賽諸塞州的塞林鎮，一群少女同時患上了怪病。為了追查病原，人們逮捕了當地幾名婦女，並聲稱她們是女巫。頓時，鎮子上籠罩上了可怕的陰影，人人自危。誰都不知道下一秒自己是否被指控。你會被指控嗎？你會自首嗎？還是要把你所有親友都送上斷頭臺，來換取自己生存的機會？

獵巫鎮，是一款以真實歷史事件為主題發想而成的陣營遊戲。塞林鎮的女巫審判狂潮是從幾名女子開始的，這幾名女子同時得了怪病，於是教會將其認定為惡魔附身。很快，憤怒的人們將此歸結於是女巫的詛咒，並將無辜的人推向審判。許多婦女被逼無奈下，承認了自己參與巫術，但等待她們的不是救贖，而是死亡。在1692年的狂潮中，共有150人被指控有女巫罪，其中19人被處以絞刑，1人被活活壓死，另外有至少5人在監獄中被虐待致死。遊戲中所使用的角色也根據該事件進行改編。

在遊戲過程中，玩家需要使用手中的卡牌，努力說服其他玩家自己是無辜的村民，並幫助自己的同夥贏得勝利。持有女巫身分的玩家，如果殺死了所有村民，則女巫一方立刻獲勝。如果玩家在遊戲過程中從未獲得過女巫身分卡，則是無辜村民，他們要努力揭開女巫的身分。遊戲中傳染事件會令恐懼蔓延，女巫會把其他人變成女巫。',
'2023-04-10',
'2023-04-10 14:30:00',
'2023-04-10 17:30:00',
'60',
'0',
'300',
'0',
'2023-03-30 06:00:00',
'2023-04-06 22:30:00'
);

-- 第三筆資料(已取消)
INSERT INTO boardgame.event (
 EVENT_NAME	,
 EVENT_DISC	,
 EVENT_DATE	,
 EVENT_STARTTIME,
 EVENT_ENDTIME	,
 EVENT_LIMIT	,
 SIGNUP_NUM	,
 EVENT_FEE	,
 EVENT_STATUS	,
 SIGNUP_START_TIME,
 SIGNUP_DEADLINE
)
VALUES (
'卡坦島',
'發現新世界！

你們是首批登陸卡坦島的勇敢拓荒者，迅速創立了初期的村莊和道路，透過收集島上豐富的資源來擴展聚落，進而興建城市，並且藉由交易各取所需，解決資源分佈不均的問題。
然而這兒畢竟是個小島，土地與資源都很有限；當各自的勢力越來越強大，衝突也隨之而來。你是否能從群雄中脫穎而出，稱霸卡坦島？

自1995年榮獲德國年度遊戲冠軍以來，卡坦島讓全球數百萬玩家為之瘋狂，流連於這款充滿益智與趣味的遊戲中。

想進入桌上遊戲世界，你一定不能錯過卡坦島！',
'2023-04-01',
'2023-04-10 09:30:00',
'2023-04-10 16:30:00',
'0',
'0',
'300',
'2',
'2023-03-20 09:00:00',
'2023-03-15 23:30:00'
);



-- 新增EMPLOYEE表格
DROP TABLE IF EXISTS EMPLOYEE;
CREATE TABLE EMPLOYEE (
    EMPLOYEE_NO 		INT NOT NULL AUTO_INCREMENT,
    EMPLOYEE_NAME 		VARCHAR(10) NOT NULL,
    EMPLOYEE_PHONE 		VARCHAR(10),
    EMPLOYEE_ADDRESS 	VARCHAR(100),
    EMPLOYEE_EMAIL 		VARCHAR(30) NOT NULL,
    EMPLOYEE_PASSWORD 	VARCHAR(100) NOT NULL,
    ROLE_NO 			INT,
    EMPLOYEE_STATUS 	TINYINT,
    PRIMARY KEY (EMPLOYEE_NO)
);
-- 新增EMPLOYEE資料
INSERT INTO EMPLOYEE (EMPLOYEE_NAME, EMPLOYEE_PHONE, EMPLOYEE_ADDRESS, EMPLOYEE_EMAIL, EMPLOYEE_PASSWORD, ROLE_NO, EMPLOYEE_STATUS)
VALUES
('李四', '0987654321', '台灣台北市大安區', 'lisi@example.com', 'mypassword', 1, 1),
('王五', '0912345678', '台灣台中市南區', 'wangwu@example.com', 'mypassword', 2, 1),
('陳六', '0922333444', '台灣高雄市三民區', 'chenliu@example.com', 'mypassword', 2, 0),
('林七', '0933123456', '台灣新北市板橋區', 'linqi@example.com', 'mypassword', 3, 1),
('張八', '0977888999', '台灣桃園市龜山區', 'zhangba@example.com', 'mypassword', 3, 0);

-- 新增ROLE表格
DROP TABLE IF EXISTS ROLE;
CREATE TABLE ROLE (
    ROLE_NO 	INT NOT NULL AUTO_INCREMENT,
    ROLE_NAME 	VARCHAR(15) NOT NULL,
    PRIMARY KEY (ROLE_NO)
);
-- 新增ROLE資料
INSERT INTO ROLE (ROLE_NAME)
VALUES ('Admin'), ('Manager'), ('Others');

-- 新增ROLE_PERMISSIONS表格
DROP TABLE IF EXISTS ROLE_PERMISSIONS;
CREATE TABLE ROLE_PERMISSIONS (
    ROLE_PERMS_NO 	INT NOT NULL AUTO_INCREMENT,
    ROLE_NO 		INT NOT NULL,
    PERMS_NO 		INT NOT NULL, -- not sure yet
    PRIMARY KEY (ROLE_PERMS_NO)
);
-- 新增ROLE_PERMISSIONS資料
INSERT INTO ROLE_PERMISSIONS (ROLE_NO, PERMS_NO)
VALUES (1, 1), (2, 2);

-- 新增PERMISSIONS表格
DROP TABLE IF EXISTS PERMISSIONS;
CREATE TABLE PERMISSIONS (
	PERMS_NO 	INT NOT NULL AUTO_INCREMENT,
    PERMS 		VARCHAR(50),
    PRIMARY KEY (PERMS_NO)
);

-- 新增SERVICE表格
DROP TABLE IF EXISTS SERVICE;
CREATE TABLE SERVICE
(
    SERVICE_NO		INT NOT NULL AUTO_INCREMENT ,
    EMPLOYEE_NO		INT,
    MEM_NO			INT,
    SERVICE_MSG		VARCHAR(1200),
    SERVICE_TIME	DATETIME,
    DIALOGUE_DIRECTION	TINYINT,
    PRIMARY KEY(SERVICE_NO)
);

INSERT INTO SERVICE (SERVICE_NO, EMPLOYEE_NO, MEM_NO, SERVICE_MSG, SERVICE_TIME, DIALOGUE_DIRECTION) VALUES
('1', '1', '1', '請問有哪些桌遊是適合小孩子玩的呢？', '2023/3/10 10:30:00', '1'),
('2', '1', '1', '我們店裡有提供雙胞胎及矮人礦坑都很適合小孩子玩。', '2023/3/10 10:33:00', '0'),
('3', '1', '2', '請問您們店裡有哪些新款桌遊可以推薦嗎？', '2023/3/10 11:45:00', '1'),
('4', '1', '2', '您可以到我們官網商城使用商品列表的功能即可找到目前最新上架的桌遊。', '2023/3/10 11:50:00', '0'),
('5', '3', '4', '能否請問一下您們店裡的熱門桌遊有哪些呢？', '2023/3/10 14:10:00', '1'),
('6', '3', '4', '您可以瀏覽我們官網，首頁會針對熱門桌遊進行推播', '2023/3/10 14:40:00', '0'),
('7', '3', '5', '能否請問一下您們店裡的桌遊價格範圍是多少呢？', '2023/3/10 15:30:00', '1'),
('8', '3', '5', '我們店裡的桌遊價格範圍500~1500不等', '2023/3/10 15:33:00', '0'),
('9', '4', '7', '能否請問一下您們店裡的桌遊訂位價格是多少呢？', '2023/3/11 9:10:00', '1'),
('10', '4', '7', '目前訂位價格一人一小時50元，因價格會調漲建議您可到我們官網首頁查詢，您欲預約的日期所需支付的價格', '2023/3/11 9:30:00', '0'),
('11', '4', '8', '想找些策略性的桌遊，有什麼好推薦的嗎？', '2023/3/11 10:30:00', '1'),
('12', '4', '8', '推薦您可以參考我們一款名為卡卡頌的桌遊，每位玩家建立一個餐廳，透過經營餐廳來獲取金錢及星星，最後擁有最多星星的玩家獲勝。遊戲中需要玩家運用策略、抉擇及運氣來經營餐廳，', '2023/3/11 10:40:00', '0');

-- 新增NEWS表格
DROP TABLE IF EXISTS NEWS;
CREATE TABLE NEWS
(
    NEWS_NO	  	INT NOT NULL AUTO_INCREMENT ,
    NEWS_TITLE	VARCHAR(25),
    NEWS_DESC	VARCHAR(50),
    NEWS_STATUS	TINYINT DEFAULT 0,
    PRIMARY KEY(NEWS_NO)
);
-- 新增NEWS資料
INSERT INTO NEWS (NEWS_TITLE, NEWS_DESC, NEWS_STATUS) VALUES
('新增咖啡廳桌遊', '我們最新上架了一款咖啡廳主題桌遊，歡迎來店體驗！', '1'),
('五月份活動預告', '五月份將會舉辦慶祝端午節的活動，敬請期待！', '0'),
('網站更新公告', '桌遊店的官方網站進行了一次更新，新增了線上訂購的功能。', '1'),
('暑期營隊開放報名', '暑假期間將會舉辦桌遊營隊，現已開放報名！', '0'),
('消費滿千送百活動', '本店現正舉辦消費滿千元送一百元的優惠活動，敬請把握機會！', '1');

-- 新增SYSTEM_PARAM表格
DROP TABLE IF EXISTS SYSTEM_PARAM;
CREATE TABLE SYSTEM_PARAM(
	SYSTEM_NO INT NOT NULL AUTO_INCREMENT ,
    PARAM_KEY VARCHAR(10) UNIQUE,
    PARAM_VAL VARCHAR(10),
    PRIMARY KEY(SYSTEM_NO)
);

-- 新增BOOKING表格
DROP TABLE IF EXISTS BOOKING;
CREATE TABLE BOOKING
(
    BOOKING_NO		INT NOT NULL AUTO_INCREMENT,
    MEM_NO			INT,
    BOOKING_DATE	DATE,
    BOOKING_START_TIME	TIME,
    BOOKING_END_TIME	TIME,
    BOOKING_PAYMENT_STATUS	TINYINT,
    BOOKING_CHECK_STATUS	TINYINT,
    BOOKING_FINISH_DATE		DATETIME,
    BOOKING_TOTAL_PRICE		INT,
    BOOKING_PEOPLE			INT,
    PRIMARY KEY(BOOKING_NO)
);
-- 新增BOOKING資料
INSERT INTO BOOKING (MEM_NO, BOOKING_DATE, BOOKING_START_TIME, BOOKING_END_TIME, BOOKING_PAYMENT_STATUS, BOOKING_CHECK_STATUS, BOOKING_FINISH_DATE, BOOKING_TOTAL_PRICE, BOOKING_PEOPLE) VALUES
('1', '2023-03-10', '8:00:00', '12:00:00', '1', '0', '2023-03-11 12:00:00', '800', '4'),
('2', '2023-03-10', '8:00:00', '12:00:00', '1', '0', '2023-03-11 12:00:00', '800', '4'),
('3', '2023-03-15', '8:00:00', '19:00:00', '1', '1', '2023-03-11 12:00:00', '400', '2');

-- 新增EVENT_ORD表格
DROP TABLE IF EXISTS EVENT_ORD;
CREATE TABLE EVENT_ORD
(
    EVENT_NO	INT NOT NULL,
    MEM_NO		INT NOT NULL,
    MEM_CHECKED	TINYINT DEFAULT 0,
    MEM_NAME    VARCHAR(10) NOT NULL,
    MEM_ADDRESS VARCHAR(100) NOT NULL,
    MEM_EMAIL   VARCHAR(30)  NOT NULL,
    MEM_PHONE  VARCHAR(10)  NOT NULL,
    EVENT_STATUS TINYINT,
    CONSTRAINT PK_MEM_EVENT_ORD PRIMARY KEY(EVENT_NO, MEM_NO)
);
-- 新增EVENT_ORD資料
INSERT INTO EVENT_ORD (EVENT_NO, MEM_NO, MEM_CHECKED, MEM_NAME, MEM_ADDRESS, MEM_EMAIL, MEM_PHONE, EVENT_STATUS) VALUES
('1', '1', '0', '張三', '台北市中山區南京東路', 'zhangsan@gmail.com', '0912345678', '0'),
('1', '3', '1', '王五', '台北市中正區羅斯福路', 'wangwu@hotmail.com', '0912345680', '1'),
('2', '6', '1', '張曼玉', '台北市大同區民權西路', 'zhangmanyu@hotmail.com', '0912345683', '1'),
('2', '7', '0', '張麗珠', '新竹市東區中華路30號', 'misszhang@gmail.com', '0977888999', '2'),
('3', '2', '1', '李四', '台北市大安區信義路', 'lisi@yahoo.com', '0912345679', '1'),
('3', '5', '1', '林志玲', '台北市士林區中正路', 'linzhiling@gmail.com', '0912345682', '1'),
('3', '8', '1', '陳偉銘', '高雄市鳳山區中山路80號', 'mrchen@yahoo.com.tw', '0988777666', '1');

-- 新增NOTIFICATION表格
DROP TABLE IF EXISTS NOTIFICATION;
CREATE TABLE NOTIFICATION
(
    NOTICE_NO	INT NOT NULL AUTO_INCREMENT,
    MEM_NO		INT,
    NOTICE_DESC	VARCHAR(100),
    NOTICE_TIME	DATETIME,
    NOTICE_READ	TINYINT DEFAULT 0,
    PRIMARY KEY(NOTICE_NO)
);
-- 新增NOTIFICATION資料
INSERT INTO NOTIFICATION (MEM_NO, NOTICE_DESC, NOTICE_TIME, NOTICE_READ) VALUES
('5', '親愛的參賽者您好..........', '2023-05-01', '1'),
('8', '親愛的參賽者您好..........', '2023-05-01', '1'),
('5', '親愛的參賽者您好..........', '2023-05-05', '0'),
('9', '親愛的參賽者您好..........', '2023-05-05', '1'),
('6', '親愛的參賽者您好..........', '2023-05-06', '1'),
('7', '親愛的參賽者您好..........', '2023-05-06', '1');

-- CONSTRAINT FK
-- 商品圖片
-- ALTER TABLE PRODUCT_PIC
-- ADD CONSTRAINT FK_PD_NO FOREIGN KEY (PD_NO) REFERENCES PRODUCT(PD_NO);
-- -- 商品討論區
-- ALTER TABLE PRODUCT_DISCUSS
-- ADD CONSTRAINT FK_PD_NO FOREIGN KEY (PD_NO) REFERENCES PRODUCT(PD_NO);

-- ALTER TABLE PRODUCT_DISCUSS
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);
-- -- 商品追蹤
-- ALTER TABLE COLLECTION
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);

-- ALTER TABLE COLLECTION
-- ADD CONSTRAINT FK_PD_NO FOREIGN KEY (PD_NO) REFERENCES PRODUCT(PD_NO);
-- -- 訂單
-- ALTER TABLE ORDERS
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);
-- -- 訂單明細
-- ALTER TABLE ORDERLIST
-- ADD CONSTRAINT FK_ORD_NO FOREIGN KEY (ORD_NO) REFERENCES ORDERS(ORD_NO);

-- ALTER TABLE ORDERLIST
-- ADD CONSTRAINT FK_PD_NO FOREIGN KEY (PD_NO) REFERENCES PRODUCT(PD_NO);
-- -- 熱門遊戲推播
-- ALTER TABLE PRODUCT_AD
-- ADD CONSTRAINT FK_PD_NO FOREIGN KEY (PD_NO) REFERENCES PRODUCT(PD_NO);
-- -- 預約訂單
-- ALTER TABLE BOOKING
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);
-- -- 賽事報名訂單
-- ALTER TABLE EVENT_ORD
-- ADD CONSTRAINT FK_EVENT_NO FOREIGN KEY (EVENT_NO) REFERENCES EVENT(EVENT_NO);

-- ALTER TABLE EVENT_ORD
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);
-- -- 後台管理員
-- ALTER TABLE EMPLOYEE
-- ADD CONSTRAINT FK_ROLE_NO FOREIGN KEY (ROLE_NO) REFERENCES ROLE(ROLE_NO);
-- -- 角色權限
-- ALTER TABLE ROLE_PERMISSIONS
-- ADD CONSTRAINT FK_ROLE_NO FOREIGN KEY (ROLE_NO) REFERENCES ROLE(ROLE_NO);

-- ALTER TABLE ROLE_PERMISSIONS
-- ADD CONSTRAINT FK_PERMS_NO FOREIGN KEY (PERMS_NO) REFERENCES PERMISSIONS(PERMS_NO);
-- -- 線上客服
-- ALTER TABLE SERVICE
-- ADD CONSTRAINT FK_EMPLOYEE_NO FOREIGN KEY (EMPLOYEE_NO) REFERENCES EMPLOYEE(EMPLOYEE_NO);

-- ALTER TABLE SERVICE
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);
-- -- 通知
-- ALTER TABLE NOTIFICATION
-- ADD CONSTRAINT FK_MEM_NO FOREIGN KEY (MEM_NO) REFERENCES MEMBERS(MEM_NO);


