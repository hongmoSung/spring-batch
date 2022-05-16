## 스프링 배치 - Spring Boot 기반으로 개발하는 Spring Batch

- [x] 섹션 0. 강좌 소개
    - [x] 서론
    - [x] 목차 / 개발 환경 및 선수 지식
- [x] 섹션 1. 스프링 배치 소개
    - [x] <a href="https://github.com/hongmoSung/spring-batch/blob/main/docs/section01/archi.md">개요 및 아키텍처</a>
- [ ] 섹션 2. 스프링 배치 시작
    - [x] 목차 소개
    - [x] <a href="https://github.com/hongmoSung/spring-batch/blob/main/docs/section02/spring-batch-start.md">프로젝트 구성 및
      의존성 설정<a>
    - [x] <a href="https://github.com/hongmoSung/spring-batch/blob/main/docs/section02/hello-batch.md">Hello
      Spring Batch 시작하기</a>
    - [x] <a href="https://github.com/hongmoSung/spring-batch/blob/main/docs/section02/db-shema.md">DB 스키마 생성 및 이해 (
      1)</a>
    - [ ] DB 스키마 생성 및 이해 (2)
- [ ] 섹션 3. 스프링 배치 도메인 이해
    - [ ] 목차 소개
    - [ ] Job
    - [ ] JobInstance
    - [ ] JobParameter
    - [ ] JobExecution
    - [ ] Step
    - [ ] StepExecution
    - [ ] StepContribution
    - [ ] ExecutionContext
    - [ ] JobRepository
    - [ ] JobLauncher
- [ ] 섹션 4. 스프링 배치 실행 - Job
    - [ ] 목차 소개
    - [ ] 배치 초기화 설정
    - [ ] Job and Step 소개
    - [ ] JobBuilderFactory
    - [ ] SimpleJob - 개념 및 API 소개
    - [ ] SimpleJob - start() / next()
    - [ ] SimpleJob - validator()
    - [ ] SimpleJob - preventRestart()
    - [ ] SimpleJob - incrementer()
    - [ ] SimpleJob 아키텍처
- [ ] 섹션 5. 스프링 배치 실행 - Step
    - [ ] StepBuilderFactory
    - [ ] TaskletStep - 개념 및 API 소개
    - [ ] TaskletStep - tasklet()
    - [ ] TaskletStep - startLimit() / allowStartIfComplete()
    - [ ] TaskletStep 아키텍처
    - [ ] JobStep
- [ ] 섹션 6. 스프링 배치 실행 - Flow
    - [ ] FlowJob - 개념 및 API 소개
    - [ ] FlowJob - start() / next()
    - [ ] Transition - 배치상태 유형 (BatchStatus / ExitStatus / FlowExecutionStatus)
    - [ ] Transition - on() / to() / stop(), fail(), end(), stopAndRestart()
    - [ ] 사용자 정의 ExitStatus
    - [ ] JobExecutionDecider
    - [ ] FlowJob 아키텍처
    - [ ] SimpleFlow - 개념 및 API 소개
    - [ ] SimpleFlow 예제
    - [ ] SimpleFlow 아키텍처
    - [ ] FlowStep
    - [ ] @JobScope / @StepScope - 기본개념 및 설정
    - [ ] @JobScope / @StepScope 아키텍처
- [ ] 섹션 7. 스프링 배치 청크 프로세스 이해
    - [ ] 목차 소개
    - [ ] Chunk
    - [ ] ChunkOrientedTasklet - 개념 및 API 소개
    - [ ] ChunkOrientedTasklet - ChunkProvider / ChunkProcessor
    - [ ] ItemReader / ItemWriter / ItemProcessor 이해
    - [ ] ItemStream
    - [ ] Chunk Process 아키텍처
- [ ] 섹션 8. 스프링 배치 청크 프로세스 활용 - ItemReader
    - [ ] 목차 소개
    - [ ] FlatFileItemReader - 개념 및 API 소개
    - [ ] FlatFileItemReader - delimetedlinetokenizer
    - [ ] FlatFileItemReader - fixedlengthtokenizer
    - [ ] FlatFileItemReader - Exception Handling
    - [ ] XML StaxEventItemReader - 개념 및 API 소개
    - [ ] XML StaxEventItemReader -예제
    - [ ] JsonItemReader
    - [ ] DB - Cursor & Paging 이해
    - [ ] DB - JdbcCursorItemReader
    - [ ] DB - JpaCursorItemReader
    - [ ] DB - JdbcPagingItemReader
    - [ ] DB - JpaPagingItemReader
    - [ ] ItemReaderAdapter
- [ ] 섹션 9. 스프링 배치 청크 프로세스 활용 - ItemWriter
    - [ ] FlatFileItemWriter - 개념 및 API 소개
    - [ ] FlatFileItemWriter - delimeted
    - [ ] FlatFileItemWriter - format
    - [ ] XML StaxEventItemWriter
    - [ ] JsonFileItemWriter
    - [ ] DB - JdbcBatchItemWriter
    - [ ] DB - JpaItemWriter
    - [ ] ItemWriterAdapter
- [ ] 섹션 10. 스프링 배치 청크 프로세스 활용 - ItemProcessor
    - [ ] CompositeItemProcessor
    - [ ] ClassifierCompositeItemProcessor
- [ ] 섹션 11. 스프링 배치 반복 및 오류 제어
    - [ ] 목차 소개
    - [ ] Repeat
    - [ ] FaultTolerant
    - [ ] Skip
    - [ ] Retry (1)
    - [ ] Retry (2)
    - [ ] Retry (3)
    - [ ] Skip & Retry 아키텍처
- [ ] 섹션 12. 스프링 배치 멀티 스레드 프로세싱
    - [ ] 기본 개념
    - [ ] AsyncItemProcessor / AsyncItemWriter
    - [ ] Multi-threaded Step
    - [ ] Partitioning (1)
    - [ ] Partitioning (2)
    - [ ] SynchronizedItemStreamReader
- [ ] 섹션 13. 스프링 배치 이벤트 리스너
    - [ ] 기본개념
    - [ ] JobExecutionListener / StepExecutionListener
    - [ ] ChunkListener / ItemReadListener /ItemProcessListener /ItemWriteListener
    - [ ] SkipListener & RetryListener
- [ ] 섹션 14. 스프링 배치 테스트 및 운영
    - [ ] Spring Batch Test
    - [ ] JobExplorer / JobRegistry / JobOperator
- [ ] 섹션 15. 실전! 스프링 배치 어플리케이션 개발
    - [ ] 어플리케이션 예제 (1)
    - [ ] 어플리케이션 예제 (2)
    - [ ] 어플리케이션 예제 (3)
    - [ ] 어플리케이션 예제 (4)
    - [ ] 어플리케이션 예제 (5)
    - [ ] 어플리케이션 예제 (6)
    - [ ] 어플리케이션 예제 (7)
- [ ] 섹션 16. 강좌 마무리
    - [ ] 정리

### Reference

- https://www.inflearn.com/course/스프링-배치
- https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html
- https://jojoldu.tistory.com/category/Spring%20Batch