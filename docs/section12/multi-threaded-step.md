## 스프링 배치 멀티 스레드 프로세싱 - Multi-threaded Step

- 기본개념
  - Step 내에서 멀티 스레드로 Chunk 기반 처리가 이루어지는 구조
  - TaskExecutorRepeatTemplate 이 반복자로 사용되며 설정한 개수 (throttleLimit) 만큼의 스레드를 생성하여 수행한다
    
<img src="../../images/section12/multi-thread.png" alt="multi-thread">

<img src="../../images/section12/multi-thread-example.png" alt="multi-thread-example">

<img src="../../images/section12/multi-thread-process.png" alt="multi-thread-process">

