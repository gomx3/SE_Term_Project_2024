import React, { useState, useEffect } from 'react';
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './CreateIssue.module.css';

function CreateIssue() {
  /* 프로젝트 정보 */
  const projectId = `2`;
  /* 사용자 정보 */
  const [user, setUser] = useState({
    id: 80,
    memberId: 'tt', // 로그인한 사용자로 설정하게
    role: 'TESTER',
  });
  /* 이슈 정보  */
  const [issue, setIssue] = useState({
    id: 7,
    title: '',
    description: '',
    state: 'NEW',
    category: 'OTHERS',
    reporter: user.memberId, // 로그인한 사용자
    reportedDate: '',
    priority: 'MAJOR',
    assignee: '',
    fixer:'',
  });
  /* 코멘트 정보 */
  const [comment, setComment] = useState({
    comments: [],
    newComment: '',
  })
  
  const categories = [
    'MEMORY_LEAK',
    'CRASH',
    'USER_FEEDBACK',
    'SECURITY',
    'OTHERS',
  ]

  useEffect(() => {
    const currentDate = new Date().toISOString().split('T')[0]; // 컴포넌트가 처음 렌더링될 때 현재 날짜를 설정
    setIssue((prevIssue) => ({ 
      ...prevIssue, 
      reportedDate: currentDate,
     }));
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        <div>
          <h1 className={styles.h1}>CREATE ISSUE</h1>
        </div>
        <IssueInfo
          projectId={projectId}
          user={user}
          issue={issue}
          setIssue={setIssue}
          categories={categories}
        />
      </div>
      <div className={styles.commentContainer}>
        <IssueComment
          user={user}
          issue={issue}
          comment={comment}
          setComment={setComment}
        />
      </div>
    </div>
  );
}

export default CreateIssue;