-- 회원
DROP TABLE IF EXISTS bc_memb RESTRICT;

-- 입출금
DROP TABLE IF EXISTS bc_amount RESTRICT;

-- 예산
DROP TABLE IF EXISTS bc_budget RESTRICT;

-- 출금내용
DROP TABLE IF EXISTS bc_contents RESTRICT;

-- 회원
CREATE TABLE bc_memb (
    mno       INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
    name      VARCHAR(50)  NOT NULL COMMENT '이름', -- 이름
    email     VARCHAR(40)  NOT NULL COMMENT '이메일', -- 이메일
    pwd       VARCHAR(100) NOT NULL COMMENT '비밀번호', -- 비밀번호
    auth_code VARCHAR(255) NULL     COMMENT '인증코드', -- 인증코드
    actvt     CHAR(1)      NULL     COMMENT '활성화' -- 활성화
)
COMMENT '회원 테이블';

-- 회원
ALTER TABLE bc_memb
    ADD CONSTRAINT PK_bc_memb -- 회원 기본키
        PRIMARY KEY (
            mno -- 회원번호
        );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_bc_memb
    ON bc_memb ( -- 회원
        email ASC -- 이메일
    );

-- 회원 유니크 인덱스2
CREATE UNIQUE INDEX UIX_bc_memb2
    ON bc_memb ( -- 회원
        auth_code ASC -- 인증코드
    );

ALTER TABLE bc_memb
    MODIFY COLUMN mno INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 입출금
CREATE TABLE bc_amount (
    amntno INTEGER      NOT NULL COMMENT '입출금번호', -- 입출금번호
    mno    INTEGER      NOT NULL COMMENT '회원번호', -- 회원번호
    type   VARCHAR(50)  NOT NULL COMMENT '입출금유형', -- 입출금유형
    hst    VARCHAR(50)  NOT NULL COMMENT '내역', -- 내역
    amnt   INTEGER      NOT NULL COMMENT '금액', -- 금액
    cat    VARCHAR(50)  NULL     COMMENT '분류', -- 분류
    memo   VARCHAR(255) NULL     COMMENT '메모', -- 메모
    sdt    DATETIME     NOT NULL COMMENT '일시' -- 일시
)
COMMENT '입출금';

-- 입출금
ALTER TABLE bc_amount
    ADD CONSTRAINT PK_bc_amount -- 입출금 기본키
        PRIMARY KEY (
            amntno -- 입출금번호
        );

-- 입출금 인덱스
CREATE INDEX IX_bc_amount
    ON bc_amount( -- 입출금
        sdt DESC -- 일시
    );

ALTER TABLE bc_amount
    MODIFY COLUMN amntno INTEGER NOT NULL AUTO_INCREMENT COMMENT '입출금번호';

-- 예산
CREATE TABLE bc_budget (
    bdgno INTEGER    NOT NULL COMMENT '예산번호', -- 예산번호
    mno   INTEGER    NOT NULL COMMENT '회원번호', -- 회원번호
    month VARCHAR(2) NOT NULL COMMENT '해당 월', -- 해당 월
    amnt  INTEGER    NOT NULL COMMENT '예산금액', -- 예산금액
    wthdr INTEGER    NOT NULL COMMENT '지출금액' -- 지출금액
)
COMMENT '예산';

-- 예산
ALTER TABLE bc_budget
    ADD CONSTRAINT PK_bc_budget -- 예산 기본키
        PRIMARY KEY (
            bdgno -- 예산번호
        );

-- 예산 유니크 인덱스
CREATE UNIQUE INDEX UIX_bc_budget
    ON bc_budget ( -- 예산
        mno ASC,   -- 회원번호
        month ASC  -- 해당 월
    );

ALTER TABLE bc_budget
    MODIFY COLUMN bdgno INTEGER NOT NULL AUTO_INCREMENT COMMENT '예산번호';

-- 출금내용
CREATE TABLE bc_contents (
    amntno INTEGER        NOT NULL COMMENT '입출금번호', -- 입출금번호
    plcnm  VARCHAR(50)    NULL     COMMENT '사용처명', -- 사용처명
    plc    VARCHAR(255)   NULL     COMMENT '사용처주소', -- 사용처주소
    mapx   DECIMAL(13,10) NULL     COMMENT 'x좌표', -- x좌표
    mapy   DECIMAL(13,10) NULL     COMMENT 'y좌표', -- y좌표
    rcpt   VARCHAR(255)   NULL     COMMENT '영수증' -- 영수증
)
COMMENT '출금내용';

-- 출금내용
ALTER TABLE bc_contents
    ADD CONSTRAINT PK_bc_contents -- 출금내용 기본키
        PRIMARY KEY (
            amntno -- 입출금번호
        );

-- 입출금
ALTER TABLE bc_amount
    ADD CONSTRAINT FK_bc_memb_TO_bc_amount -- 회원 -> 입출금
        FOREIGN KEY (
            mno -- 회원번호
        )
        REFERENCES bc_memb ( -- 회원
            mno -- 회원번호
        );

-- 예산
ALTER TABLE bc_budget
    ADD CONSTRAINT FK_bc_memb_TO_bc_budget -- 회원 -> 예산
        FOREIGN KEY (
            mno -- 회원번호
        )
        REFERENCES bc_memb ( -- 회원
            mno -- 회원번호
        );

-- 출금내용
ALTER TABLE bc_contents
    ADD CONSTRAINT FK_bc_amount_TO_bc_contents -- 입출금 -> 출금내용
        FOREIGN KEY (
            amntno -- 입출금번호
        )
        REFERENCES bc_amount ( -- 입출금
            amntno -- 입출금번호
        );