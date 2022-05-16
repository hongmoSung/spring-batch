## 스프링 배치 시작 - DB 스키마 생성

1. 스프링 배치 메타 데이터
    - 스프링 배치의 실행 및 관리를 위한 목적으로 여러 도메인들(Job, Step, JobParameters..) 의 정보들을 저장, 업데이트, 조회할 수 있는 스키마 제공
    - 과거, 현재의 실행에 대한 세세한 정보, 실행에 대한 성공과 실패 여부 등을 일목요연하게 관리함으로서 배치운용에 있어 리스크 발생시 빠른 대처 가능
    - DB 와 연동할 경우 필수적으로 메타 테이블이 생성 되어야 함
2. DB 스키마 제공
    - 파일 위치 : /org/springframework/batch/core/schema-*.sql
    - DB 유형별로 제공

3. 스키마 생성 설정
    - 수동 생성 – 쿼리 복사 후 직접 실행
    - 자동 생성 - spring.batch.jdbc.initialize-schema 설정
        - ALWAYS
            - 스크립트 항상 실행
            - RDBMS 설정이 되어 있을 경우 내장 DB 보다 우선적으로 실행 EMBEDDED : 내장 DB일 때만 실행되며 스키마가 자동 생성됨, 기본값
        - NEVER
            - 스크립트 항상 실행 안함
            - 내장 DB 일경우 스크립트가 생성이 안되기 때문에 오류 발생
            - 운영에서 수동으로 스크립트 생성 후 설정하는 것을 권장

<img src="../../images/db-schema.png" alt="db-schema">  

- Job 관련 테이블
    - BATCH_JOB_INSTANCE
        - Job 이 실행될 때 JobInstance 정보가 저장되며 job_name과 job_key를 키로 하여 하나의 데이터가 저장
        - 동일한 job_name 과 job_key 로 중복 저장될 수 없다
    - BATCH_JOB_EXECUTION
        - job 의 실행정보가 저장되며 Job 생성, 시작, 종료 시간, 실행상태, 메시지 등을 관리
    - BATCH_JOB_EXECUTION_PARAMS
        - Job 과 함께 실행되는 JobParameter 정보를 저장
    - BATCH_JOB_EXECUTION_CONTEXT
        - Job 의 실행동안 여러가지 상태정보, 공유 데이터를 직렬화 (Json 형식) 해서 저장
        - Step 간 서로 공유 가능함

- Step 관련 테이블
    - BATCH_STEP_EXECUTION
        - Step 의 실행정보가 저장되며 생성, 시작, 종료 시간, 실행상태, 메시지 등을 관리
    - BATCH_STEP_EXECUTION_CONTEXT
        - Step 의 실행동안 여러가지 상태정보, 공유 데이터를 직렬화 (Json 형식) 해서 저장
        - Step 별로 저장되며 Step 간 서로 공유할 수 없음

```sql
create table BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID bigint       not null
        primary key,
    VERSION         bigint null,
    JOB_NAME        varchar(100) not null,
    JOB_KEY         varchar(32)  not null,
    constraint JOB_INST_UN
        unique (JOB_NAME, JOB_KEY)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>JOB_INSTANCE_ID</td>
            <td>고유하게 식별할 수 있는 기본 키</td>
        </tr>
        <tr>
            <td>VERSION</td>
            <td>업데이트 될 때 마다 1씩 증가</td>
        </tr>
        <tr>
            <td>JOB_NAME</td>
            <td>Job 을 구성할 때 부여하는 Job 의 이름</td>
        </tr>
        <tr>
            <td>JOB_KEY</td>
            <td>job_name 과 jobParameter 를 합쳐 해싱한 값을 저장</td>
        </tr>
    </tbody>
</table>

```sql
create table BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID           bigint not null
        primary key,
    VERSION                    bigint null,
    JOB_INSTANCE_ID            bigint not null,
    CREATE_TIME                datetime(6) not null,
    START_TIME                 datetime(6) null,
    END_TIME                   datetime(6) null,
    STATUS                     varchar(10) null,
    EXIT_CODE                  varchar(2500) null,
    EXIT_MESSAGE               varchar(2500) null,
    LAST_UPDATED               datetime(6) null,
    JOB_CONFIGURATION_LOCATION varchar(2500) null,
    constraint JOB_INST_EXEC_FK
        foreign key (JOB_INSTANCE_ID) references BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>JOB_EXECUTION_ID</td>
            <td>JobExecution 을 고유하게 식별할 수 있는 기본 키, JOB_INSTANCE 와 일대 다 관계</td>
        </tr>
        <tr>
            <td>VERSION</td>
            <td>업데이트 될 때 마다 1씩 증가</td>
        </tr>
        <tr>
            <td>JOB_INSTANCE_ID</td>
            <td>JOB_INSTANCE 의 키 저장</td>
        </tr>
        <tr>
            <td>CREATE_TIME</td>
            <td>실행(Execution)이 생성된 시점을 TimeStamp 형식으로 기록</td>
        </tr>
        <tr>
            <td>CREATE_TIME</td>
            <td>실행(Execution)이 생성된 시점을 TimeStamp 형식으로 기록</td>
        </tr>
        <tr>
            <td>START_TIME</td>
            <td>실행(Execution)이 시작된 시점을 TimeStamp 형식으로 기록</td>
        </tr>
        <tr>
            <td>END_TIME</td>
            <td>실행이 종료된 시점을 TimeStamp 으로 기록하며 Job 실행 도중 오류가 발생해서 Job 이 중단된 경우 값이 저장되지 않을 수 있음</td>
        </tr>
        <tr>
            <td>STATUS</td>
            <td>실행 상태 (BatchStatus)를 저장 (COMPLETED, FAILED, STOPPED…)</td>
        </tr>
        <tr>
            <td>EXIT_CODE</td>
            <td>실행 종료코드(ExitStatus) 를 저장 (COMPLETED, FAILED…)</td>
        </tr>
        <tr>
            <td>EXIT_MESSAGE</td>
            <td>Status 가 실패일 경우 실패 원인 등의 내용을 저장</td>
        </tr>
        <tr>
            <td>LAST_UPDATED</td>
            <td>마지막 실행(Execution) 시점을 TimeStamp 형식으로 기록</td>
        </tr>
    </tbody>
</table>

```sql
create table BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID bigint       not null,
    TYPE_CD          varchar(6)   not null,
    KEY_NAME         varchar(100) not null,
    STRING_VAL       varchar(250) null,
    DATE_VAL         datetime(6) null,
    LONG_VAL         bigint null,
    DOUBLE_VAL       double null,
    IDENTIFYING      char         not null,
    constraint JOB_EXEC_PARAMS_FK
        foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>JOB_EXECUTION_ID</td>
            <td>JobExecution 식별 키, JOB_EXECUTION 과는 일대다 관계</td>
        </tr>
        <tr>
            <td>TYPE_CD</td>
            <td>STRING, LONG, DATE, DUBLE 타입정보</td>
        </tr>
        <tr>
            <td>KEY_NAME</td>
            <td>파라미터 키 값</td>
        </tr>
        <tr>
            <td>STRING_VAL</td>
            <td>파라미터 문자 값</td>
        </tr>
        <tr>
            <td>DATE_VAL</td>
            <td>날짜 값</td>
        </tr>
        <tr>
            <td>LONG_VAL</td>
            <td>파라미터 LONG 값</td>
        </tr>
        <tr>
            <td>DOUBLE_VAL</td>
            <td>파라미터 DOUBLE 값</td>
        </tr>
        <tr>
            <td>IDENTIFYING</td>
            <td>식별여부 (TRUE, FALSE)</td>
        </tr>
    </tbody>
</table>

```sql
create table BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text null,
    constraint JOB_EXEC_CTX_FK
        foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>JOB_EXECUTION_ID</td>
            <td>JobExecution 식별 키, JOB_EXECUTION 마다 각 생성</td>
        </tr>
        <tr>
            <td>SHORT_CONTEXT</td>
            <td>JOB 의 실행 상태정보, 공유데이터 등의 정보를 문자열로 저장</td>
        </tr>
        <tr>
            <td>SERIALIZED_CONTEXT</td>
            <td>직렬화(serialized)된 전체 컨텍스트</td>
        </tr>
    </tbody>
</table>

```sql
create table BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  bigint       not null
        primary key,
    VERSION            bigint       not null,
    STEP_NAME          varchar(100) not null,
    JOB_EXECUTION_ID   bigint       not null,
    START_TIME         datetime(6) not null,
    END_TIME           datetime(6) null,
    STATUS             varchar(10) null,
    COMMIT_COUNT       bigint null,
    READ_COUNT         bigint null,
    FILTER_COUNT       bigint null,
    WRITE_COUNT        bigint null,
    READ_SKIP_COUNT    bigint null,
    WRITE_SKIP_COUNT   bigint null,
    PROCESS_SKIP_COUNT bigint null,
    ROLLBACK_COUNT     bigint null,
    EXIT_CODE          varchar(2500) null,
    EXIT_MESSAGE       varchar(2500) null,
    LAST_UPDATED       datetime(6) null,
    constraint JOB_EXEC_STEP_FK
        foreign key (JOB_EXECUTION_ID) references BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>STEP_EXECUTION_ID</td>
            <td>Step 의 실행정보를 고유하게 식별할 수 있는 기본 키</td>
        </tr>
        <tr>
            <td>VERSION</td>
            <td>업데이트 될 때마다 1씩 증가</td>
        </tr>
        <tr>
            <td>STEP_NAME</td>
            <td>Step 을 구성할 때 부여하는 Step 이름</td>
        </tr>
        <tr>
            <td>JOB_EXECUTION_ID</td>
            <td>JobExecution 기본키, JobExecution 과는 일대 다 관계</td>
        </tr>
        <tr>
            <td>START_TIME</td>
            <td>실행(Execution)이 시작된 시점을 TimeStamp 형식으로 기록</td>
        </tr>
        <tr>
            <td>END_TIME</td>
            <td>실행이 종료된 시점을 TimeStamp 으로 기록하며 Job 실행 도중 오류가 발생해서 Job 이 중단된 경우 값이 저장되지 않을 수 있음</td>
        </tr>
        <tr>
            <td>STATUS</td>
            <td>실행 상태 (BatchStatus)를 저장 (COMPLETED, FAILED, STOPPED…)</td>
        </tr>
        <tr>
            <td>COMMIT_COUNT</td>
            <td>트랜잭션 당 커밋되는 수를 기록</td>
        </tr>
        <tr>
            <td>READ_COUNT</td>
            <td>실행시점에 Read한 Item 수를 기록</td>
        </tr>
        <tr>
            <td>FILTER_COUNT</td>
            <td>실행도중 필터링된 Item 수를 기록</td>
        </tr>
        <tr>
            <td>WRITE_COUNT</td>
            <td>실행도중 저장되고 커밋된 Item 수를 기록</td>
        </tr>
        <tr>
            <td>READ_SKIP_COUNT</td>
            <td>실행도중 Read가 Skip 된 Item 수를 기록</td>
        </tr>
        <tr>
            <td>WRITE_SKIP_COUNT</td>
            <td>실행도중 write가 Skip된 Item 수를 기록</td>
        </tr>
        <tr>
            <td>PROCESS_SKIP_COUNT</td>
            <td>실행도중 Process가 Skip 된 Item 수를 기록</td>
        </tr>
        <tr>
            <td>ROLLBACK_COUNT</td>
            <td>실행도중 rollback이 일어난 수를 기록</td>
        </tr>
        <tr>
            <td>EXIT_CODE</td>
            <td>실행 종료코드(ExitStatus) 를 저장 (COMPLETED, FAILED…)</td>
        </tr>
        <tr>
            <td>EXIT_MESSAGE</td>
            <td>Status가 실패일 경우 실패 원인 등의 내용을 저장</td>
        </tr>
        <tr>
            <td>LAST_UPDATED</td>
            <td>마지막 실행(Execution) 시점을 TimeStamp 형식으로 기록</td>
        </tr>
    </tbody>
</table>

```sql
create table BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  bigint        not null
        primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text null,
    constraint STEP_EXEC_CTX_FK
        foreign key (STEP_EXECUTION_ID) references BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
);
```

<table>
    <thead>
        <tr>
            <td>컬럼</td>
            <td>설명</td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>STEP_EXECUTION_ID</td>
            <td>StepExecution 식별 키, STEP_EXECUTION 마다 각 생성</td>
        </tr>
        <tr>
            <td>SHORT_CONTEXT</td>
            <td>STEP 의 실행 상태정보, 공유데이터 등의 정보를 문자열로 저장</td>
        </tr>
        <tr>
            <td>SERIALIZED_CONTEXT</td>
            <td>직렬화(serialized)된 전체 컨텍스트</td>
        </tr>
    </tbody>
</table>