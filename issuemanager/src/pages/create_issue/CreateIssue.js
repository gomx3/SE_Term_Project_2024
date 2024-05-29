import React, { useState, useEffect } from 'react';
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './CreateIssue.module.css';

function CreateIssue() {
  const [issue, setIssue] = useState({
    title: '',
    description: '',
    state: '',
    category: '',
    reporter: '',
    reportedDate: '',
    priority: '',
    assignee: '',
    fixer:'',
    comments: [],
    newComment: '',
  });
  const [userId, setUserId] = useState('tester'); // 현재 사용자 id (A 또는 B, ...)
  const categories = [
    'Memory Leak',
    'Crash Occurrence',
    'User Feedback',
    'Security Vulnerability',
    'Others',
  ]

  useEffect(() => {
    const currentDate = new Date().toISOString().split('T')[0]; // 컴포넌트가 처음 렌더링될 때 현재 날짜를 설정
    setIssue((prevIssue) => ({ 
      ...prevIssue, 
      reportedDate: currentDate,
     }));
  }, []);

  function handleSubmit() {
    console.log('Issue edited: ', issue);
  }

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        <div>
          <h1 className={styles.h1}>CREATE ISSUE</h1>
        </div>
        <IssueInfo
          issue={issue}
          setIssue={setIssue}
          userId={userId}
          categories={categories}
        />
        <div className={styles.btns}>
                <button className={styles.btn} onClick={() => console.log('Issue closed')}>Close Issue</button>
                <button className={styles.btn} onClick={() => console.log('Issue resolved')}>Resolve Issue</button>
                <button className={styles.btn} type="submit" onClick={handleSubmit}>Create Issue</button>
        </div>
      </div>
      <div className={styles.commentContainer}>
        <IssueComment
          issue={issue}
          setIssue={setIssue}
          userId={userId}
        />
      </div>
    </div>
  );
}

export default CreateIssue;