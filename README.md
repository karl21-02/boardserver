![image](https://github.com/user-attachments/assets/03236191-be44-4ddb-84a8-a47cfd3f4b84)
EXPLAIN키워드로 조회 결과
EXPLAIN SELECT 
    `id`,
    `name`,
    `isAdmin`,
    `contents`,
    `createTime`,
    `views`,
    `categoryId`,
    `userId`,
    `fileId`,
    `updateTime`
FROM post
WHERE 1=1
  AND name LIKE '테스트%'
  AND categoryId = 1
ORDER BY createTime DESC;
----
INDEX가 1.11로 인덱스로 검색하긴 하지만, 약 300000만 번 중에서 1%정도만 조회한다는 결론 도출
따라서, POST테이블에 인덱스를 추가
![image](https://github.com/user-attachments/assets/fcdd0077-ee65-4d49-b05b-96c72f3aff4d)
위 사진처럼 name을 기준으로 인덱스 추가하면 위 사진처럼 5%로 조회 성능이 향상된 점을 파악

-------------------------

또한, 게시글 검색 부분에서 비동기 통신을 추가

이후 RPS가 기존 스트레스 테스트 500명이 초당 50명씩 조회한 RPS 648에서 같은 조건하에 876으로 향상된 점을 파악
